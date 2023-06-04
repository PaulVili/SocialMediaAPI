package com.paulvili.socialmediaapi.repository;

import com.paulvili.socialmediaapi.model.UserPostsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersPostRepository extends JpaRepository<UserPostsModel, Integer> {

}
