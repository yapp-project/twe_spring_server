package com.sajo.study.repository;

import com.sajo.study.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByUser_Idx(long userIdx);
}
