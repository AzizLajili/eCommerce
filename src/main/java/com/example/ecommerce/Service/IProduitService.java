package com.example.ecommerce.Service;

import com.example.ecommerce.DAO.Produit;

import java.util.List;

public interface IProduitService {

    Produit addProduit(Produit produit);
    List<Produit> getAllProduit();
    Produit updateProduit (Produit produit);
    Produit getProduitById (int id);
    void deleteProduit(int id);
}
