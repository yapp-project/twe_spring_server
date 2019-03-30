package com.sajo.study.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sajo.study.json.ArticleDeSerializer;
import com.sajo.study.json.ArticleSerializer;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JsonSerialize(using = ArticleSerializer.class)
@JsonDeserialize(using = ArticleDeSerializer.class)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;


    @ManyToOne
    @JoinColumn(name = "userIdx", nullable = false)
    private User user;

    @Column
    private String imagePath;

    @Column(length = 1000)
    private String content;

    public Article(User user, String content) {
        this.user = user;
        this.content = content;
    }

    @JsonCreator
    public Article(User user, String imagePath, String content) {
        this.user = user;
        this.imagePath = imagePath;
        this.content = content;
    }
}
