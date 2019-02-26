package com.sajo.study.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;


    @Column(unique = true, nullable = false)
    private String id;

    @Column(nullable = false)
    private String password;

    public User(long idx) {
        this.idx = idx;
    }


    public User(long idx, String id, String password) {
        this.idx = idx;
        this.id = id;
        this.password = password;
    }


    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }


    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Article> articles;

}
