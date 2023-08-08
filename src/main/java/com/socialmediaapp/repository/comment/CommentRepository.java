package com.socialmediaapp.repository.comment;

import com.socialmediaapp.model.Comment;
import com.socialmediaapp.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select p from Post p where p.user.id= ?1")
    List<Post> ListAllPostComment(Long userId, Pageable pageable);
}
