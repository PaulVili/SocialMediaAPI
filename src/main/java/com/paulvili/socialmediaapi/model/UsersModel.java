package com.paulvili.socialmediaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "users", schema = "public", catalog = "social_media")
public class UsersModel implements UserDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @NonNull
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "user_name")
    private String userName;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Basic
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
