package it.fides.user.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.fides.user.models.dtos.SigninDto;
import it.fides.user.models.dtos.UserDto;
import it.fides.user.models.entities.UserEntity;
import it.fides.user.models.repositories.RoleRepository;
import it.fides.user.models.repositories.UserRepository;
import it.fides.user.util.UserConverter;
import it.fides.user.util.UserLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserLogger userLogger;

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    public Mono<String> healthcheck() {
        return Mono.just("User Microservice ok");
    }

    public String getRoleFromAuthHeader(ServerWebExchange exchange) {
        String token = Objects.requireNonNull(exchange.getRequest().getHeaders().getFirst("Authorization")).substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parseClaimsJws(token).getBody();
        return (String) claims.get("role");
    }

    public Flux<UserEntity> getAllUsers() {
        return userRepo.findAll().switchIfEmpty(Mono.error(new RuntimeException("Users not found")));
    }

    public Mono<UserDto> getUser(Long id) {
        return userRepo.findById(id)
                .flatMap(userEntity -> roleRepo.findById(userEntity.getIdRole())
                        .map(roleEntity -> userConverter.toDto(userEntity, roleEntity)))
                .doOnError(error -> userLogger.log.error("Error fetching user: " + error.getMessage()))
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }

    public Mono<UserDto> getUserByEmail(String email) {
        return userRepo.findByEmailUser(email)
                .flatMap(userEntity -> roleRepo.findById(userEntity.getIdRole())
                        .map(roleEntity -> userConverter.toDto(userEntity, roleEntity)))
                .doOnError(error -> userLogger.log.error("Error fetching user by email: " + error.getMessage()))
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }

    public Mono<String> signin(UserDto userDto) {
        UserEntity user = userConverter.toEntity(userDto);
        return userRepo.save(user)
                .doOnError(error -> userLogger.log.error("Error saving user: " + error.getMessage()))
                .then(Mono.just("New User signed in"));
    }
}
