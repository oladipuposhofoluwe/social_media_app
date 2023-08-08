package com.socialmediaapp.service.comment;


import com.socialmediaapp.dto.comment.CommentDto;
import com.socialmediaapp.dto.comment.CommentRequestDto;
import com.socialmediaapp.dto.comment.CommentResponseDto;
import com.socialmediaapp.dto.post.PostResponseDto;
import com.socialmediaapp.exceptions.ResourceNotFoundException;
import com.socialmediaapp.exceptions.UnAuthorizedException;
import com.socialmediaapp.model.Comment;
import com.socialmediaapp.model.Post;
import com.socialmediaapp.model.User;
import com.socialmediaapp.repository.comment.CommentRepository;
import com.socialmediaapp.repository.post.PostRepository;
import com.socialmediaapp.repository.user.UserRepository;
import com.socialmediaapp.service.notification.NotificationService;
import com.socialmediaapp.utils.DateUtil;
import com.socialmediaapp.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Override
    public void createPostComment(CommentRequestDto commentRequestDto, Long userId, long postId) {
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        if (optionalPost.isEmpty()){
            throw new ResourceNotFoundException("Post with " + postId + "not found");
        }

        Optional<User> optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isEmpty()){
            throw new ResourceNotFoundException("User with " + userId + "not found");
        }

        Comment comment = new Comment();
        comment.setContent(commentRequestDto.getCommentContent());
        comment.setUser(optionalUser.get());

        Post post = optionalPost.get();
        post.addComment(comment);
        notificationService.sendPostNotification(optionalUser.get(), "comments on your post: ", optionalPost.get().getContent());
        this.postRepository.save(post);
    }

    @Override
    public CommentDto readPostComment(long postId, long commentId) {

        CommentDto commentDto = new CommentDto();

        Optional<Post> optionalPost = this.postRepository.findById(postId);
        if (optionalPost.isEmpty()){
            throw new ResourceNotFoundException("Post with " + postId + "not found");
        }

        Collection<Comment> comments = optionalPost.get().getComments();
        Optional<Comment> optionalComment = comments.stream().filter(comment -> comment.getId().equals(commentId)).findFirst();

        if (optionalComment.isEmpty()){
            throw new ResourceNotFoundException("Comment with " + commentId + "not found");
        }else {
            Comment comment = optionalComment.get();
            this.mapPostCommentToPost(comment, commentDto);
        }
        return commentDto;
    }

    private void mapPostCommentToPost(Comment comment, CommentDto commentDto) {
        commentDto.setComment(comment.getContent());
        commentDto.setCommentWriterName(comment.getUser().getUserName());
        commentDto.setLikeCount(comment.getLikes().size());
        commentDto.setCommentDate(comment.getAuditSection().getDateCreated());
    }

    @Override
    public void updatePostComment(CommentRequestDto commentRequestDto, User authUser, long commentId) {
        Optional<Comment> optionalComment = this.commentRepository.findById(commentId);
        if (optionalComment.isEmpty()){
            throw new ResourceNotFoundException("Comment with " + commentId + "not found");
        }

        Comment comment = optionalComment.get();

        if(!isUserOwnComment(optionalComment, authUser)){
            throw new UnAuthorizedException("Un Authorized Comment poster");
        }else {
            comment.setContent(commentRequestDto.getCommentContent());
            comment.getAuditSection().setDateModified(DateUtil.now());
            comment.getAuditSection().setModifiedBy(authUser.getUserName());
        }
        this.commentRepository.save(comment);
    }

    @Override
    public void deleteComment(long commentId, long postId, User authUser) {
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        if (optionalPost.isEmpty()){
            throw new ResourceNotFoundException("Post with " + postId + "not found");
        }

        Post post = optionalPost.get();
        Collection<Comment> comments = post.getComments();
        Optional<Comment> optionalComment = comments.stream().filter(comment -> comment.getId().equals(commentId)).findFirst();

        if (optionalComment.isEmpty()){
            throw new ResourceNotFoundException("Comment with " + commentId + "not found");
        }

        if(!isUserOwnComment(optionalComment, authUser)){
            throw new UnAuthorizedException("Unauthorized Comment deletion");
        }

        optionalPost.get().removeComment(optionalComment.get());
        this.postRepository.save(post);

    }

    @Override
    public List<CommentResponseDto> ListAllPostComment(User authUser, Long postId, Pageable pageable) {
        return null;
    }

    private Collection<CommentDto> getAllPostComment(Collection<Comment> postComments) {
       return postComments.stream().map(postComment -> {
            CommentDto comment = new CommentDto();
            comment.setComment(postComment.getContent());
            comment.setCommentWriterName(postComment.getUser().getUserName());
            comment.setCommentDate(postComment.getAuditSection().getDateCreated());
            return comment;
        }).collect(Collectors.toList());
    }

    private CommentDto mapCommentToDto(Comment comment) {
        CommentDto commentResponseDto = new CommentDto();
        commentResponseDto.setComment(comment.getContent());
        commentResponseDto.setLikeCount(comment.getLikes().size());
        commentResponseDto.setCommentWriterName(comment.getUser().getUserName());
        commentResponseDto.setCommentDate(comment.getAuditSection().getDateCreated());
        return commentResponseDto;
    }

    private boolean isUserOwnComment(Optional<Comment> optionalComment, User authUser) {
        return optionalComment.isPresent() && optionalComment.get().getUser().getId().equals(authUser.getId());
    }
}
