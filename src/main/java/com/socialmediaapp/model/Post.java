package com.socialmediaapp.model;

import com.socialmediaapp.constant.SchemaConstant;
import com.socialmediaapp.model.common.audit.AuditListener;
import com.socialmediaapp.model.common.audit.AuditSection;
import com.socialmediaapp.model.common.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static com.socialmediaapp.constant.SchemaConstant.TABLE_POST;

@Getter
@Setter
@EntityListeners(AuditListener.class)
@NoArgsConstructor
@Entity
@Table(name = TABLE_POST)
public class Post implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(length = SchemaConstant.COMMENT_COL_SIZE)
    private String content;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Like> likes = new HashSet<>();
    @ManyToOne
    private User user;
    @Embedded
    private AuditSection auditSection = new AuditSection();

    public void addComment(Comment comment) {
        comment.setPost(this);
        this.comments.add(comment);
    }
    public void removeComment(Comment comment) {
        comment.setPost(null);
        this.comments.remove(comment);
    }

    public void addLike(Like like) {
        like.setPost(this);
        this.likes.add(like);
    }
    public void removeLike(Like like) {
        like.setPost(null);
        this.likes.remove(like);
    }
}
