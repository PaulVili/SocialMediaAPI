package com.paulvili.socialmediaapi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Date;
import java.util.Objects;

@Data
@Entity
@DynamicUpdate
@Table(name = "user_friend", schema = "public", catalog = "social_media")
public class UserFriendModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "source_id")
    private int sourceId;
    @Basic
    @Column(name = "target_id")
    private int targetId;
    @Basic
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private Type type;
    @Basic
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private Status status;
    @Basic
    @Column(name = "created_at")
    private Date createdAt;
    @Basic
    @Column(name = "updated_at")
    private Date updatedAt;
    @Basic
    @Column(name = "notes")
    private String notes;
    @ManyToOne
    @JoinColumn(name = "source_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private UsersModel usersBySourceId;
    @ManyToOne
    @JoinColumn(name = "target_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private UsersModel usersByTargetId;
}
