package com.socialmediaapp.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {
    private String comment;
    private String commentWriterName;
    private int likeCount;
    private Date commentDate;
}
