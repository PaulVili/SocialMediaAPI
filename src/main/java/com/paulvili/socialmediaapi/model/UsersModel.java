package com.paulvili.socialmediaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "users", schema = "public", catalog = "social_media")
public class UsersModel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @NonNull
    @Column(name = "id")
    private int id;

    @Basic
    @NonNull
    @Column(name = "user_name")
    private String userName;

    @Basic
    @NonNull
    @Column(name = "email")
    private String email;

    @Basic
    @NonNull
    @Column(name = "password_hash")
    private String passwordHash;

    @Basic
    @Enumerated(EnumType.STRING)
    @NonNull
    @Column(name = "role")
    private Role role;

    @Basic
    @NonNull
    @Column(name = "registered_at")
    private Date registeredAt;

    @Basic
    @Column(name = "last_login")
    private Date lastLogin;

    @OneToMany(mappedBy = "usersBySourceId")
    @JsonIgnore
    private Collection<UserFollowerModel> userFollowersById;

    @OneToMany(mappedBy = "usersByTargetId")
    @JsonIgnore
    private Collection<UserFollowerModel> userFollowersById_0;

    @OneToMany(mappedBy = "usersBySourceId")
    @JsonIgnore
    private Collection<UserFriendModel> userFriendsById;

    @OneToMany(mappedBy = "usersByTargetId")
    @JsonIgnore
    private Collection<UserFriendModel> userFriendsById_0;

    @OneToMany(mappedBy = "usersByUserId")
    @JsonIgnore
    private Collection<UserPostsModel> userPostsById;

}
