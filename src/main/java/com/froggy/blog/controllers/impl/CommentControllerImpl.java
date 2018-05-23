package com.froggy.blog.controllers.impl;

import com.froggy.blog.controllers.CommentController;
import com.froggy.blog.dto.CreateCommentDTO;
import com.froggy.blog.dto.EditCommentDTO;
import com.froggy.blog.models.BlogPost;
import com.froggy.blog.models.Comment;
import com.froggy.blog.models.User;
import com.froggy.blog.repos.BlogPostRepo;
import com.froggy.blog.repos.CommentRepo;
import com.froggy.blog.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/comments/")
public class CommentControllerImpl implements CommentController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    BlogPostRepo blogPostRepo;

    @Override
    @RequestMapping(value = "{postId}", method = POST)
    public HttpStatus saveComment(@RequestBody CreateCommentDTO comment,
                                  @PathVariable Long postId) {
        try {
            commentRepo.save(new Comment(comment.getContents(),
                    userRepo.findUserByEmail(comment.getAuthorEmail()).orElse(User.getDefaultUser()),
                    blogPostRepo.findOneById(postId).orElse(BlogPost.getDefaultBlogPost())));
            return HttpStatus.CREATED;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    @RequestMapping(value = "{commentId}", method = PUT)
    public HttpStatus editComment(@RequestBody EditCommentDTO edit,
                                  @PathVariable("commentId") Long commentId) {
        try {
            commentRepo.update(commentId, edit.getContents());
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    @RequestMapping(value = "{id}", method = DELETE)
    public HttpStatus deleteComment(@PathVariable(name = "id") Long id) {
        try {
            commentRepo.deleteCommentById(id);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    @RequestMapping(value = "{postId}", method = GET)
    public String allCommentsOfOneBlogPost(@PathVariable("postId") Long postId) {
        return commentRepo.findAllByPost_Id(postId)
                .stream()
                .sorted(Comparator.comparing(Comment::getDate))
                .map(comment -> comment.toString() + "\n")
                .reduce("", (a, b) -> a + b);
    }
}
