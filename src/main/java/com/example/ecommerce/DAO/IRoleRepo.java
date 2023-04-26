package com.example.ecommerce.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface IRoleRepo extends JpaRepository<Role, TypeRole> {

    Role findByRole(String name);
}
