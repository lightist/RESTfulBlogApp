package com.froggy.blog.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="posts")
public class BlogPost implements Serializable {

    private static final long serialVersionUID = -3009157732242241606L;
    private static final BlogPost defaultBlogPost
            = new BlogPost("Empty post", User.getDefaultUser());

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "contents")
    private String contents;

    @OneToMany(targetEntity = Comment.class, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_email", referencedColumnName = "email")
    private User author;

    @Column(name = "date")
    private Date date;

    public BlogPost() {} //an empty constructor is necessary, will be used by Spring JPA

    public BlogPost(String contents, User user) {
        //this();
        this.contents = contents;
        this.author = user;
        this.date = new Date();
        this.comments = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return author;
    }

    public void setUser(User user) {
        this.author = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static BlogPost getDefaultBlogPost() {
        return defaultBlogPost;
    }

    @Override
    public String toString() {
        return "{\'id\':" + id + ","
                + "\'contents\':\'" + contents + "\',"
                + "\'email\':\'"+ author.getEmail() + "\',"
                + "\'date\':\'" + date + "\'}";
    }
}
