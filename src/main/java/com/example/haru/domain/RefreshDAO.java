package com.example.haru.domain;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "refresh")
@Entity
@Builder
public class RefreshDAO {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refreshToken_id")
    private long id;

    private String refreshToken;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


}
