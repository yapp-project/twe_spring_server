package com.sajo.study.handler;

import com.sajo.study.model.Article;
import com.sajo.study.model.User;
import com.sajo.study.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@Slf4j
public class ArticleHandler {
    private ArticleService articleService;


    @Autowired
    public ArticleHandler(ArticleService articleService) {
        this.articleService = articleService;
    }

    public Mono<ServerResponse> getArticleByUser(ServerRequest request) {
        return Mono.just(request.pathVariable("userIdx")).map(Long::valueOf)
                .flatMap(i -> ok().body(fromPublisher(Flux.fromIterable(articleService.getArticleByUserIdx(i)), Article.class)));
    }

    public Mono<ServerResponse> getArticle(ServerRequest request) {
        return Mono.just(request.pathVariable("articleIdx")).map(Long::valueOf)
                .flatMap(i -> ok().body(fromPublisher(Mono.just(articleService.getArticleByIdx(i)), Article.class)));
    }

    public Mono<ServerResponse> makeArticle(ServerRequest request) {
        // makeArticle이 왜 null을 리턴할까?
        int userIdx = Integer.parseInt(request.pathVariable("userIdx"));
        return request.formData()
                .flatMap(a -> {
                    Article article = new Article(new User(userIdx), a.get("content").get(0));
                    articleService.makeArticle(article);
                    return Mono.just(article);
                })
                .flatMap(a -> created(URI.create("/article/" + a.getUser().getIdx() + "/" + a.getIdx()))
                        .body(Mono.just(a), Article.class));
    }
}
