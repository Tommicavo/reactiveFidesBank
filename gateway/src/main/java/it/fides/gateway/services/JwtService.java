package it.fides.gateway.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.fides.gateway.models.dtos.AccessTokenDto;
import it.fides.gateway.models.dtos.UserDto;
import it.fides.gateway.util.GatewayLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class JwtService {

    @Autowired
    private GatewayLogger gatewayLogger;

    @Autowired
    private BlackTokenService blackTokenService;

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    @Value("${jwt.expiration.millis}")
    private int EXPIRATION_MILLIS;

    public String generateToken(UserDto user) {
        return Jwts.builder().setSubject(String.valueOf(user.getIdUser()))
                .claim("role", user.getRole().getLabelRole())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).compact();
    }

    public Mono<AccessTokenDto> verifyToken(String token) {
        return isTokenBlackListed(token)
                .flatMap(isBlackListed -> {
                    if (isBlackListed) {
                        return Mono.just(new AccessTokenDto(false, "BLACKLISTED TOKEN"));
                    } else if(!isValidToken(token)) {
                        return Mono.just(new AccessTokenDto(false, "EXPIRED TOKEN"));
                    }
                    return Mono.just(constructAccessToken(token));
                });
    }

    private Mono<Boolean> isTokenBlackListed(String token) {
        return blackTokenService.isTokenBlackListed(token);
    }

    private boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parse(token);
            return true;
        } catch(Exception e) {
            gatewayLogger.log.info("TOKEN VERIFICATION FAILED: " + e);
            return false;
        }
    }

    private AccessTokenDto constructAccessToken(String token) {
        String idUser = getIdFromToken(token);
        Claims claims = getClaimsFromToken(token);
        Date expirationDate = claims.getExpiration();
        String role = (String) claims.get("role");

        AccessTokenDto accessToken = new AccessTokenDto();
        accessToken.setToken(token);
        accessToken.setIdUser(Long.parseLong(idUser));
        accessToken.setExpiration(expirationDate);
        accessToken.setLabelRole(role);
        accessToken.setVerified(true);
        return accessToken;
    }

    public String getJwtFromRequest(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    public String getIdFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build().parseClaimsJws(token).getBody().getSubject();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parseClaimsJws(token).getBody();
    }

    public ServerWebExchange injectAuthorizationToken(ServerWebExchange exchange, String token) {
        ServerHttpRequest tokenExchange = exchange
                .getRequest()
                .mutate()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();
        return exchange.mutate().request(tokenExchange).build();
    }

    public ServerWebExchange injectUnsecuredHeader(ServerWebExchange exchange) {
        ServerHttpRequest unsecuredExchange = exchange
                .getRequest()
                .mutate()
                .header("UnsecuredRoute", "true")
                .build();
        return exchange.mutate().request(unsecuredExchange).build();
    }
}
