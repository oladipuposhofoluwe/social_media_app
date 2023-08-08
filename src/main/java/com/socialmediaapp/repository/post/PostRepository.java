package com.socialmediaapp.repository.post;

import com.socialmediaapp.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where p.user.id= ?1")
    List<Post> findAllPostsByUser(Long userId, Pageable pageable);

    @Query("select p from Post p where p.user.id= ?1 and p.id= ?2 ")
    List<Post> findAllPostsByUser(Long userId, Long postId, Pageable pageable);
}
