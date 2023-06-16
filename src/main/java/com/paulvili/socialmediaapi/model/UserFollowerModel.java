package com.paulvili.socialmediaapi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Date;

@Data
@Entity
@DynamicUpdate
@Table(name = "user_follower", schema = "public", catalog = "social_media")
public class UserFollowerModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "source_id")
    private int sourceId;

    @Column(name = "target_id")
    private int targetId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private Type type;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "source_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private UsersModel usersBySourceId;

    @ManyToOne
    @JoinColumn(name = "target_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private UsersModel usersByTargetId;
}
