package com.epam.clothshop.repository;

import com.epam.clothshop.entity.Category;
import com.epam.clothshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
}
