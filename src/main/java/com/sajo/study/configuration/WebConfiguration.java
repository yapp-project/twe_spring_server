package com.sajo.study.configuration;

import com.sajo.study.handler.ArticleHandler;
import com.sajo.study.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
@EnableWebFlux
public class WebConfiguration {
    @Bean
    public RouterFunction<ServerResponse> userRoute(UserHandler handler) {
        return RouterFunctions.route(GET("/user/{userIdx}").and(accept(APPLICATION_JSON)), handler::getUser)
                .andRoute(POST("/user").and(accept(APPLICATION_JSON)),handler::makeUser);
    }

    @Bean
    public RouterFunction<ServerResponse> articleRoute(ArticleHandler handler){
        return RouterFunctions.route(GET("/article/{userIdx}").and(accept(APPLICATION_JSON)),handler::getArticleByUser)
                .andRoute(GET("/article/{userIdx}/{articleIdx}").and(accept(APPLICATION_JSON)),handler::getArticle)
                .andRoute(POST("/article").and(accept(APPLICATION_JSON)),handler::makeArticle);
    }



}
