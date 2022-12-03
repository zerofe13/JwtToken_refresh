package com.example.haru.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "USER_TABLE")
@Getter @Setter
@Builder
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    // email
    private String username;
    // name
    private String nickname;
    private String password;
    private String phone;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;


}
