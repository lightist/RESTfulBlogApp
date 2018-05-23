package com.froggy.blog.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = -3009157732242241606L;
    private static final User defaultUser
            = new User("Anonymous", "anykey", "absent@email.com");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "passwd")
    private String password;

    @OneToMany(targetEntity = BlogPost.class, mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Long> postIds;

    @OneToMany(targetEntity = Comment.class, mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Long> commentIds;

    public User() {} //an empty constructor is necessary, will be used by Spring JPA

    public User(String username, String password, String email) {
        //this();
        this.username = username;
        this.password = password;
        this.email = email;
        this.postIds = new HashSet<>();
        this.commentIds = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Long> getPostIds() {
        return postIds;
    }

    public void setPostIds(Set<Long> postIds) {
        this.postIds = postIds;
    }

    public Set<Long> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(Set<Long> commentIds) {
        this.commentIds = commentIds;
    }

    public static User getDefaultUser() {
        return defaultUser;
    }

    @Override
    public String toString() {
        return "{\'id\':" + id + ","
                + "\'username\':\'" + username + "\',"
                + "\'email\':\'"+ email + "\'}";
    }

}
