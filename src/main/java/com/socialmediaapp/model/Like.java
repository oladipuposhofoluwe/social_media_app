package com.socialmediaapp.model;

import com.socialmediaapp.model.common.audit.AuditListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static com.socialmediaapp.constant.SchemaConstant.TABLE_LIKE;
@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditListener.class)
@Table(name = TABLE_LIKE)
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
