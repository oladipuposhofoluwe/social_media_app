package com.socialmediaapp.service.post;

import com.socialmediaapp.dto.comment.CommentDto;
import com.socialmediaapp.dto.comment.CommentResponseDto;
import com.socialmediaapp.dto.post.PostRequestDto;
import com.socialmediaapp.dto.post.PostResponseDto;
import com.socialmediaapp.exceptions.ResourceNotFoundException;
import com.socialmediaapp.exceptions.UnAuthorizedException;
import com.socialmediaapp.model.Comment;
import com.socialmediaapp.model.Like;
import com.socialmediaapp.model.Post;
import com.socialmediaapp.model.User;
import com.socialmediaapp.repository.post.PostRepository;
import com.socialmediaapp.service.notification.NotificationService;
import com.socialmediaapp.utils.DateUtil;
import com.socialmediaapp.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final NotificationService notificationService;


    @Override
    public void createPost(PostRequestDto postRequestDto, User authUser) {
        Post post = new Post();
        this.mapPostToEntity(post, postRequestDto, authUser);
    }

    private void mapPostToEntity(Post post, PostRequestDto postRequestDto, User authUser) {
        post.setUser(authUser);
        post.setContent(postRequestDto.getPostContent());
        post.getAuditSection().setDateCreated(DateUtil.now());
        this.postRepository.save(post);
    }

    @Override
    public PostResponseDto readPost(long postId) {
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        if (optionalPost.isEmpty()){
            throw new ResourceNotFoundException("Post with " + postId + "not found");
        }
       return this.mapPostToDto(optionalPost.get());
    }

    @Override
    @Transactional
    public void updatePost(long postId, PostRequestDto postRequestDto, User authUser) {
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        if (optionalPost.isEmpty()){
            throw new ResourceNotFoundException("Post with " + postId + "not found");
        }

        Post post = optionalPost.get();
        if(!isUserOwerOdPost(authUser, post)){
            throw new UnAuthorizedException("Unauthorised");
        }

        this.updatePostEntity(optionalPost.get(), postRequestDto, authUser);
    }

    @Override
    public void deletePost(User user, long postId) {
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        if (optionalPost.isEmpty()){
            throw new ResourceNotFoundException("Post with " + postId + "not found");
        }
        Post post = optionalPost.get();
        if(!isUserOwerOdPost(user, post)){
            throw new UnAuthorizedException("Unauthorised");
        }
        postRepository.delete(optionalPost.get());
    }

    private boolean isUserOwerOdPost(User user, Post post) {
        return post.getUser().getId().equals(user.getId());
    }

    @Override
    public void likePost(long postId, User authUser) {
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new ResourceNotFoundException("Post with " + postId + "not found");
        }

        if (isUserAlreadyLikedPost(optionalPost.get(), authUser)) {
            Optional<Like> existingPostLike = optionalPost.get().getLikes().stream().filter(l -> l.getUser().equals(authUser)).findFirst();
            existingPostLike.ifPresent(like -> optionalPost.get().removeLike(like));
//            notificationService.sendPostNotification(authUser, "likes your post: ", optionalPost.get().getContent());

        } else {
            Like like = new Like();
            like.setUser(authUser);
            optionalPost.get().addLike(like);
//            notificationService.sendPostNotification(authUser, "Unlikes your post: ", optionalPost.get().getContent());
        }
        postRepository.save(optionalPost.get());
    }


    @Override
    public List<PostResponseDto> ListAllPostComments(User authUser, Long postId, Pageable pageable) {
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        List<Post> userPosts = this.postRepository.findAllPostsByUser(authUser.getId(), postId, PageUtils.normalisePageRequest(pageable));

        for (Post post : userPosts) {
            PostResponseDto postResponseDto = mapPostCommentToDto(post);
            postResponseDtos.add(postResponseDto);
        }
        return postResponseDtos;
    }


    private PostResponseDto mapPostCommentToDto(Post post) {
        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setPostContent(post.getContent());
        postResponseDto.setDateCreated(post.getAuditSection().getDateCreated());
        postResponseDto.setLikeCount(post.getLikes().size());

        Collection<Comment> postComments = post.getComments();
        if (CollectionUtils.isNotEmpty(postComments)){
            List<CommentResponseDto> comment =  this.getAllPostComment(postComments);
            postResponseDto.setPostComments(comment);
        }
        return postResponseDto;
    }

    private List<CommentResponseDto> getAllPostComment(Collection<Comment> postComments) {
        return postComments.stream().map(postComment -> {
            CommentDto commentDto = new CommentDto();
            commentDto.setComment(postComment.getContent());
            commentDto.setCommentWriterName(postComment.getUser().getUserName());
            commentDto.setLikeCount(postComment.getLikes().size());
            commentDto.setCommentDate(postComment.getAuditSection().getDateCreated());

            CommentResponseDto commentResponseDto = new CommentResponseDto();
            commentResponseDto.setComments(Collections.singletonList(commentDto));

            return commentResponseDto;
        }).collect(Collectors.toList());
    }
    @Override
    public List<PostResponseDto> ListAllPost(User authUser, Pageable pageable) {

        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        List<Post> userPosts = this.postRepository.findAllPostsByUser(authUser.getId(), PageUtils.normalisePageRequest(pageable));

        for (Post post : userPosts) {
            PostResponseDto postResponseDto = mapPostToDto(post);
            postResponseDtos.add(postResponseDto);
        }
        return postResponseDtos;
    }

    private boolean isUserAlreadyLikedPost(Post post, User authUser) {
        return post.getLikes().stream().anyMatch(like -> like.getUser().equals(authUser));
    }

    private PostResponseDto mapPostToDto(Post post) {
        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setPostContent(post.getContent());
        postResponseDto.setDateCreated(post.getAuditSection().getDateCreated());
        postResponseDto.setLikeCount(post.getLikes().size());
        return postResponseDto;
    }

    private void updatePostEntity(Post post, PostRequestDto postRequestDto, User authUser) {
        post.setContent(postRequestDto.getPostContent());
        post.getAuditSection().setModifiedBy(authUser.getUserName());
        post.getAuditSection().setDateModified(DateUtil.now());
    }
}
