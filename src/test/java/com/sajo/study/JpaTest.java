package com.sajo.study;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sajo.study.model.Article;
import com.sajo.study.model.User;
import com.sajo.study.repository.ArticleRepository;
import com.sajo.study.repository.UserRepository;
import com.sajo.study.service.ArticleService;
import com.sajo.study.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
// DataJpaTest는 Repository만 받아옴.
// service는 불러오지 않는다.
public class JpaTest {


    private UserService userService;

    private ArticleService articleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;


    @Before
    public void setUp() {
        userService = new UserService(userRepository);
        articleService = new ArticleService(articleRepository);
    }

    @Test
    public void 유저만들기테스트() {
        User u = new User("test", "haha");
        Mono<User> mu = Mono.just(u);
        assertEquals(userService.makeUser(u), u);
    }

    @Test
    public void 글리스트받아오기(){
        User u = new User("test","test");
        Article a  = new Article(u,"test2","test1");
        userService.makeUser(u);
        articleService.makeArticle(a);
        List<Article> articleList = articleService.getArticleByUserIdx(1);
        assertEquals(articleList, Lists.list(a));
    }

    @Test
    public void 유저받아오기테스트(){
        User u = new User("test", "haha");
     userService.makeUser(u);
    assertEquals(userService.getUserById("test"),u);
    }
}
