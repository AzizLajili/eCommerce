package com.example.ecommerce.DAO;

import lombok.*;

import javax.persistence.*;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private int quantite;
    @ManyToOne
    private AppUser user;

    @ManyToOne
    private Produit produit;
}
