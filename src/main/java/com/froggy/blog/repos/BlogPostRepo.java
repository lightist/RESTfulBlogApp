package com.froggy.blog.repos;


import com.froggy.blog.models.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogPostRepo extends JpaRepository<BlogPost, Long>{

    Optional<BlogPost> findOneById(Long id);
    BlogPost save(BlogPost blogPost);
    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("UPDATE BlogPost p SET p.contents = :contents WHERE p.id = :id") //Here we should not be using Table name, but Model/Entity name instead
    void update(@Param("id") Long postId, @Param("contents") String contents);
    List<BlogPost> findAll();
    List<BlogPost> findAllByAuthorUsername(String username);





}
