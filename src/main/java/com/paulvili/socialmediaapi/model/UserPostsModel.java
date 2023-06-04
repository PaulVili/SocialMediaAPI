package com.paulvili.socialmediaapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "user_posts", schema = "public", catalog = "social_media")
public class UserPostsModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "post_header")
    private String postHeader;
    @Basic
    @Column(name = "post_text")
    private String postText;
    @Basic
    @Column(name = "post_image")
    private String postImage;
    @Basic
    @Column(name = "created_at")
    private Date createdAt;
    @Basic
    @Column(name = "updated_at")
    private Integer updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private UsersModel usersByUserId;
}
