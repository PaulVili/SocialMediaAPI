package com.paulvili.socialmediaapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_messages", schema = "public", catalog = "social_media")
public class UserMessageModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "source_id")
    private int sourceId;

    @Column(name = "target_id")
    private int targetId;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "source_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private UsersModel usersBySourceId;

    @ManyToOne
    @JoinColumn(name = "target_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private UsersModel usersByTargetId;
}
