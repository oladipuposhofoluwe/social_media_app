package com.socialmediaapp.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;

import static com.socialmediaapp.constant.SchemaConstant.TABLE_TOKEN_BLACKLIST;


@Entity
@Getter
@Setter
@Table(name = TABLE_TOKEN_BLACKLIST)
public class TokenBlacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "mediumtext")
    @Nullable
    private String token;
}