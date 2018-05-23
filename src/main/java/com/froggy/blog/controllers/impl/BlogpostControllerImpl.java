package com.froggy.blog.controllers.impl;

import com.froggy.blog.controllers.BlogpostController;
import com.froggy.blog.dto.CreateBlogPostDTO;
import com.froggy.blog.dto.EditBlogPostDTO;
import com.froggy.blog.models.BlogPost;
import com.froggy.blog.models.User;
import com.froggy.blog.repos.BlogPostRepo;
import com.froggy.blog.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/posts/")
public class BlogpostControllerImpl implements BlogpostController {

    @Autowired
    BlogPostRepo blogPostRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    @RequestMapping(value = "", method = GET)
    public String getAllBlogPosts() {
        return blogPostRepo.findAll()
                .stream()
                .sorted(Comparator.comparing(BlogPost::getDate))
                .map(post -> post.toString() + "\n")
                .reduce("", (a, b) -> a + b);
    }

    @Override
    @RequestMapping(value = "post/{id}", method = GET)
    public String getOneBlogPost(@PathVariable("id") Long postId) {
        return blogPostRepo.findOneById(postId)
                .orElse(BlogPost.getDefaultBlogPost())
                .toString();
    }

    @Override
    @RequestMapping(value = "{username}", method = GET)
    public String getAllBlogPostsByUser(@PathVariable("username") String username) {
        return blogPostRepo.findAllByAuthorUsername(username)
                .stream()
                .sorted(Comparator.comparing(BlogPost::getDate))
                .map(post -> post.toString() + "\n")
                .reduce("", (a, b) -> a + b);
    }


    @Override
    @RequestMapping(value = "", method = POST)
    public HttpStatus saveBlogPost(@RequestBody CreateBlogPostDTO blogPost) {
        try {
            blogPostRepo.save(new BlogPost(blogPost.getContents(),
                    userRepo.findUserByEmail(blogPost.getAuthorEmail()).orElse(User.getDefaultUser())))
                    .getUser().getEmail();
            return HttpStatus.CREATED;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }


    @Override
    @RequestMapping(value = "post/{postId}", method = PUT)
    public HttpStatus editBlogPost(@RequestBody EditBlogPostDTO edit,
                                   @PathVariable("postId") Long postId) {
        try {
            blogPostRepo.update(postId, edit.getContents());
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
