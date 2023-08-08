package com.socialmediaapp.model;

import com.socialmediaapp.constant.SchemaConstant;
import com.socialmediaapp.model.common.audit.AuditListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static com.socialmediaapp.constant.SchemaConstant.TABLE_USER;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditListener.class)
@Table(name = TABLE_USER)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(length = SchemaConstant.USER_NAME_COL_SIZE)
    private String userName;
    @Column(unique = true, length = SchemaConstant.EMAIL_COL_SIZE)
    private String email;
    private String password;
    @Column(length = SchemaConstant.PROFILE_PICTURE_COL_SIZE)
    private String profilePicture;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_follows",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private Set<User> following = new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();

    public void addPost(Post post) {
        post.setUser(this);
        this.posts.add(post);
    }
    public void removePost(Post post) {
        post.setUser(null);
        this.posts.remove(post);
    }


    public void addFollowUser(User user) {
        boolean alreadyExists = following.stream()
                .anyMatch(f -> f.getId().equals(user.getId()));
        if (alreadyExists) {
            following.remove(user);
        } else {
            following.add(user);
        }
    }

}
