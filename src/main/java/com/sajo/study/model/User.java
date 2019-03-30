package com.sajo.study.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sajo.study.security.JWTUtil;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    private LocalDate age;

    @Enumerated(value = EnumType.STRING)
    private JOB job;

    @Enumerated(value = EnumType.STRING)
    private SEX sex;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
