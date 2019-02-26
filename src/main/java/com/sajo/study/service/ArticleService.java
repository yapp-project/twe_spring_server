package com.sajo.study.service;

import com.sajo.study.model.Article;
import com.sajo.study.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ArticleService {
    private ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article getArticleByIdx(long articleIdx){
        return articleRepository.findById(articleIdx).orElse(new Article());
    }

    public Article makeArticle(Article article){
        return articleRepository.save(article);
    }


    public List<Article> getArticleByUserIdx(long userIdx){
        return articleRepository.findByUser_Idx(userIdx);
    }

}
