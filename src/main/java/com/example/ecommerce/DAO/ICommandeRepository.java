package com.example.ecommerce.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICommandeRepository extends JpaRepository<Commande,Integer> {
}
