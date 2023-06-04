package com.paulvili.socialmediaapi.repository;

import com.paulvili.socialmediaapi.model.UserFollowerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface UserFollowerRepository extends JpaRepository<UserFollowerModel, Integer> {
    @Modifying
    @Transactional
    @Query("delete UserFollowerModel u where u.sourceId = :sourceId and u.targetId = :targetId")
    void deleteBySourceIdAndTargetId(@Param("sourceId") int sourceId, @Param("targetId") int targetId);

    @Query("select u.targetId from UserFollowerModel u where u.sourceId = :sourceId")
    List<Integer> findTargetIdBySourceId(@Param("sourceId") int sourceId);
}
