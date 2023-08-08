package com.socialmediaapp.model;

import com.socialmediaapp.model.common.audit.AuditListener;
import com.socialmediaapp.model.common.audit.AuditSection;
import com.socialmediaapp.model.common.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static com.socialmediaapp.constant.SchemaConstant.TABLE_COMMENT;

@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = TABLE_COMMENT)
public class Comment implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Like> likes = new HashSet<>();
    @ManyToOne
    private User user;
    @Embedded
    private AuditSection auditSection = new AuditSection();

    public void addLike(Like like) {
        like.setComment(this);
        this.likes.add(like);
    }
    public void removeLike(Like like) {
        like.setComment(null);
        this.likes.remove(like);
    }
}
