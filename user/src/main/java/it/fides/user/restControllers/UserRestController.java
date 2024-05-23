package it.fides.user.restControllers;

import it.fides.user.config.webClient.GatewayClient;
import it.fides.user.models.dtos.TestDto;
import it.fides.user.models.dtos.UserDto;
import it.fides.user.models.entities.UserEntity;
import it.fides.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private GatewayClient gatewayClient;

    @GetMapping("/healthcheck")
    public Mono<String> healthcheck() {
        return userService.healthcheck();
    }

    @GetMapping("/all")
    public Flux<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Mono<UserDto> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/user-by-email")
    public Mono<UserDto> getUserByEmail(@RequestBody UserEntity user) {
        return userService.getUserByEmail(user.getEmailUser());
    }

    @PostMapping("/signin")
    public Mono<String> signin(@RequestBody UserDto userDto) {
        return userService.signin(userDto);
    }

    @PostMapping("/test")
    public Mono<TestDto> test(@RequestBody TestDto testDto) {
        testDto.setUserResponse("user response ok");
        return gatewayClient.test(testDto);
    }

    @GetMapping("/testRole")
    public Mono<String> testRole(ServerWebExchange exchange) {
        return Mono.just(userService.getRoleFromAuthHeader(exchange)).switchIfEmpty(Mono.error(new RuntimeException("Role not retrieved from auth header")));
    }
}
