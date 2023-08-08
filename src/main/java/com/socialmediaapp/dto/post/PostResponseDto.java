package com.socialmediaapp.dto.post;

import com.socialmediaapp.dto.comment.CommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostResponseDto {

    private String postContent;
    Collection<CommentResponseDto> postComments = new ArrayList<>();
    private Date dateCreated;
    private int likeCount;
}
