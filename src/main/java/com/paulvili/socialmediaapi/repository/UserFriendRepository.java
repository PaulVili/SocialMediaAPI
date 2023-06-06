package com.paulvili.socialmediaapi.repository;

import com.paulvili.socialmediaapi.model.Status;
import com.paulvili.socialmediaapi.model.UserFriendModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface UserFriendRepository extends JpaRepository<UserFriendModel, Integer> {
    @Query("select u from UserFriendModel u where u.sourceId = :sourceId and u.targetId = :targetId")
    UserFriendModel findBySourceIdAndTargetId (@Param("sourceId") int sourceId, @Param("targetId") int targetId);

    @Modifying
    @Transactional
    @Query("update UserFriendModel u set u.status = :status, u.updatedAt = :updatedAt where u.sourceId = :sourceId and u.targetId = :targetId")
    void updateStatus(@Param("status") Status status, @Param("updatedAt") Date updatedAt, @Param("sourceId") long sourceId, @Param("targetId") long targetId);

    @Modifying
    @Transactional
    @Query("delete UserFriendModel u where u.sourceId = :sourceId and u.targetId = :targetId")
    void deleteBySourceIdAndTargetId(@Param("sourceId") int sourceId, @Param("targetId") int targetId);


}
