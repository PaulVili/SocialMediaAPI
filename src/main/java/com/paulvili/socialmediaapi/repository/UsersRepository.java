package com.paulvili.socialmediaapi.repository;

import com.paulvili.socialmediaapi.model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UsersRepository extends JpaRepository<UsersModel, Integer> {
    @Query(nativeQuery = true, value = "select * from users p where p.user_name = :name")
    UsersModel findByUserName (@Param("name") String name);
}
