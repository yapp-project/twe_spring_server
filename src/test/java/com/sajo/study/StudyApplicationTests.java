package com.sajo.study;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sajo.study.configuration.WebConfiguration;
import com.sajo.study.handler.ArticleHandler;
import com.sajo.study.handler.UserHandler;
import com.sajo.study.model.Article;
import com.sajo.study.model.User;
import com.sajo.study.service.ArticleService;
import com.sajo.study.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebFluxTest
//@WebMvcTest
@Import({WebConfiguration.class, UserHandler.class, ArticleHandler.class})
@Slf4j
public class StudyApplicationTests {

    @Autowired
    public WebTestClient webClient;


    @MockBean
    private UserService userService;

    @MockBean
    private ArticleService articleService;


    User tempUser = new User(1L, "test", "test");


    @Test
    public void 유저받기테스트() throws Exception {
        //exchange -> 요청

        given(userService.getUserByIdx(1L)).willReturn(tempUser);
        User value = webClient.get().uri("/user/1").exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .returnResult().getResponseBody();
        assertEquals(value, tempUser);
    }

    @Test
    public void 유저만들기테스트() {
        given(userService.makeUser(tempUser)).willReturn(tempUser);
        User value = webClient.post().uri("/user").body(Mono.just(tempUser), User.class).exchange()
                .expectStatus().isCreated()
                .expectBody(User.class)
                .returnResult().getResponseBody();
        log.info(value.toString());
        assertEquals(value, tempUser);
    }

    @Test
    public void 글받기테스트() {
        List<Article> tempList = new ArrayList<>();
        tempList.add(new Article(tempUser, "test", "test"));
        given(articleService.getArticleByUserIdx(1L)).willReturn(tempList);
        List<Article> value = webClient.get().uri("/article/1").exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .returnResult().getResponseBody();
        // web을 통해서 올경우 jackson을 통해 json으로 변환됨.
        // array의 경우 jackson에서 LinkedHashMap으로 변환함.
        log.info(value.toString());
    }


    @Test
    public void 글하나받기테스트() {
        Article a = new Article(tempUser, "test", "test");
        given(articleService.getArticleByIdx(1L)).willReturn(a);
        Article value = webClient.get().uri("/article/1/1").exchange()
                .expectStatus().isOk()
                .expectBody(Article.class)
                .returnResult().getResponseBody();
        assertEquals(value.getImagePath(), a.getImagePath());
    }


    @Test
    public void 글만들기테스트() throws Exception {
        Article a = new Article(tempUser, "test", "test");
        given(articleService.makeArticle(a)).willReturn(a);
        Article value = webClient.post().uri("/article").body(Mono.just(a), Article.class).exchange()
                .expectStatus().isCreated()
                .expectBody(Article.class)
                .returnResult().getResponseBody();
        ObjectMapper mapper = new ObjectMapper();
        assertEquals(mapper.writeValueAsString(value), mapper.writeValueAsString(a));
    }
}
