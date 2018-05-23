package com.froggy.blog.controllers;

import com.froggy.blog.dto.CreateCommentDTO;
import com.froggy.blog.dto.EditCommentDTO;
import org.springframework.http.HttpStatus;

public interface CommentController {

    HttpStatus saveComment(CreateCommentDTO comment, Long postId);

    HttpStatus editComment(EditCommentDTO edit, Long commentId);

    HttpStatus deleteComment(Long id);

    String allCommentsOfOneBlogPost(Long postId);
}
