package com.example.ecommerce.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String description;
    private Boolean disponibilite;
    private String type;
    private String Image;
    private int gros;
    private int Stock;

    @JsonIgnore
    @OneToMany(mappedBy = "produit")
    private List<CommandeDetails> commandeDetails;
}
