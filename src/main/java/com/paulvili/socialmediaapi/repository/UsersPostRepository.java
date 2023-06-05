package com.paulvili.socialmediaapi.repository;

import com.paulvili.socialmediaapi.model.UserPostsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersPostRepository extends JpaRepository<UserPostsModel, Integer> {
    @Query("select u from UserPostsModel u where u.userId in :ids order by u.createdAt desc")
    List<UserPostsModel> getUserPostsByUserIdAndSorted(@Param("ids") List<Integer> ids);

}
