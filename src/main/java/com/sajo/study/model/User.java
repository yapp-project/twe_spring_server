package com.sajo.study.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sajo.study.json.LocalDateDeSerializer;
import com.sajo.study.json.LocalDateSerializer;
import com.sajo.study.security.JWTUtil;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class User implements UserDetails {

    public enum JOB{
        STUDENT,
        JOBING,
        JOB,
        ETC
    }

    public enum SEX{
        MALE,
        FEMALE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;


    @Column(unique = true, nullable = false)
    private String id;

    @Column(nullable = false)
    private String password;

    @Column
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeSerializer.class)
    private LocalDate age;

    @Enumerated(value = EnumType.STRING)
    private JOB job;

    @Enumerated(value = EnumType.STRING)
    private SEX sex;

    @JsonIgnore
    @Transient
    private List<JWTUtil.Role> roles;

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

    public User(String id, String password, LocalDate age, JOB job, SEX sex) {
        this.id = id;
        this.password = password;
        this.age = age;
        this.job = job;
        this.sex = sex;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Article> articles;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return id;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }


    public void update(User u ){
        this.job = u.getJob();
        this.age = u.getAge();
        this.password = u.getPassword();
        this.sex = u.getSex();
        this.articles = u.getArticles();
    }
}
