package com.example.ecommerce.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface IUserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);
}
