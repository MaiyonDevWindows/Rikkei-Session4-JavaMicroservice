package com.maiyon.repository;
import com.maiyon.model.dto.response.UserResponseToUser;
import com.maiyon.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByUserId(Long id);
    Optional<User> findByUsername(String userName);
    List<User> searchUsersByFullNameContainingIgnoreCase(String keyword);
}
