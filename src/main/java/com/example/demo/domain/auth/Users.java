package com.example.demo.domain.auth;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userIndex;

    @Column(
            name = "USER_ID",
            columnDefinition = "VARCHAR(70)",
            nullable = false,
            unique = true
    )
    private String userId;

    @Column(
            name = "PASSWORD",
            columnDefinition = "VARCHAR(255)",
            nullable = false
    )
    private String password;

    @Column(
            name = "EMAIL",
            columnDefinition = "VARCHAR(255)"
    )
    private String email;

    @Column(
            name = "NAME",
            columnDefinition = "VARCHAR(255)",
            nullable = false
    )
    private String name;

    @Column(
            name = "PHONE_NUMBER",
            columnDefinition = "VARCHAR(255)"
    )
    private String phoneNumber;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roleSet = new HashSet<>();

    @Column(
            name = "REG_DTM",
            columnDefinition = "DATETIME",
            nullable = false
    )
    private LocalDateTime regDtm;

    @Column(
            name = "REG_ID",
            columnDefinition = "VARCHAR(255)"
    )
    private String regId;

    @Column(
            name = "MOD_DTM",
            columnDefinition = "DATETIME",
            nullable = false
    )
    private LocalDateTime modDtm;

    @Column(
            name = "MOD_ID",
            columnDefinition = "VARCHAR(255)"
    )
    private String modId;
}
