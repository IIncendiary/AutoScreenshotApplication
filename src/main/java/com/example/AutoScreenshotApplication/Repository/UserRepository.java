package com.example.AutoScreenshotApplication.Repository;

import com.example.AutoScreenshotApplication.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
}
