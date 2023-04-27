package com.example.ecommerce.Service;

import com.example.ecommerce.DAO.Commande;
import com.example.ecommerce.DAO.Produit;

import java.util.List;

public interface ICommandeService {

    Commande addCommande(Commande commande);
    List<Commande> getAllCommande();
    Commande updateCommande(Commande commande);
    Commande getCommandeById (int id);
    void deleteCommande(int id);
}
