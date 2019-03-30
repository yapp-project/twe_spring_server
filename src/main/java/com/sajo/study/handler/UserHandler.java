package com.sajo.study.handler;

import com.sajo.study.model.User;
import com.sajo.study.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@Slf4j
public class UserHandler {
    private UserService userService;

    @Autowired
    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {
        try {
            Integer idx = Integer.valueOf(request.pathVariable("userIdx"));
            Mono<User> helloWorldMono = Mono.just(userService.getUserByIdx(idx));
            return ServerResponse.ok().body(helloWorldMono, User.class);
        } catch (NullPointerException e) {
            return ServerResponse.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    public Mono<ServerResponse> makeUser(ServerRequest request) {
        return request.body(BodyExtractors.toMono(User.class))
                .flatMap(u -> Mono.just(userService.makeUser(u)))
                .flatMap(u -> created(URI.create("/user/" + u.getId()))
                        .body(Mono.just(u), User.class));
    }


    public Mono<ServerResponse> updateUser(ServerRequest request){
        return request.body(BodyExtractors.toMono(User.class))
                .flatMap(u -> Mono.just(userService.updateUser(u)))
                .flatMap(u-> ok().body(Mono.just(u),User.class));
    }
}
