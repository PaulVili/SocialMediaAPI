package com.paulvili.socialmediaapi.repository;

import com.paulvili.socialmediaapi.model.UserMessageModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMessageRepository extends JpaRepository<UserMessageModel, Integer> {
}
