package com.froggy.blog.controllers;

import com.froggy.blog.dto.CreateBlogPostDTO;
import com.froggy.blog.dto.EditBlogPostDTO;
import org.springframework.http.HttpStatus;

public interface BlogpostController {

    String getAllBlogPosts();

    String getOneBlogPost(Long postId);

    String getAllBlogPostsByUser(String username);

    HttpStatus saveBlogPost(CreateBlogPostDTO blogPost);

    HttpStatus editBlogPost(EditBlogPostDTO edit, Long postId);

}
