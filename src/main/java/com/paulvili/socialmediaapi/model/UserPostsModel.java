package com.paulvili.socialmediaapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "user_posts", schema = "public", catalog = "social_media")
public class UserPostsModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "post_header")
    private String postHeader;

    @Column(name = "post_text")
    private String postText;

    @Column(name = "post_image")
    private String postImage;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private UsersModel usersByUserId;
}
