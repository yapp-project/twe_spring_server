package com.sajo.study.handler;

import com.sajo.study.model.User;
import com.sajo.study.security.AuthRequest;
import com.sajo.study.security.AuthResponse;
import com.sajo.study.security.JWTUtil;
import com.sajo.study.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoginHandler {


    private UserService userService;
    private JWTUtil jwtUtil;

    @Autowired
    public LoginHandler(UserService userService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public Mono<ServerResponse> login(ServerRequest request) {

        return request.bodyToMono(AuthRequest.class).flatMap(
                ar -> {
                    User u = userService.getUserById(ar.getUserName());
                    if (u == null)
                        return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
                    if (ar.getPassword().equals(u.getPassword())) {
                        String token = jwtUtil.generateToken(u);
                        return ServerResponse.ok().body(Mono.just(new AuthResponse(token)), AuthResponse.class);
                    }
                    return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
                }
        );


    }
}
