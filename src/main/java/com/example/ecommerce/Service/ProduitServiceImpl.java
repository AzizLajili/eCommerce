package com.example.ecommerce.Service;

import com.example.ecommerce.DAO.IProduitRepository;
import com.example.ecommerce.DAO.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitServiceImpl  implements IProduitService{

    @Autowired
    IProduitRepository iProduitRepository;
    @Override
    public Produit addProduit(Produit produit) {
        return iProduitRepository.save(produit);
    }

    @Override
    public List<Produit> getAllProduit() {
        return iProduitRepository.findAll();
    }

    @Override
    public Produit updateProduit(Produit produit) {
        return iProduitRepository.save(produit);
    }

    @Override
    public Produit getProduitById(int id) {
        return iProduitRepository.findById(id).get();
    }

    @Override
    public void deleteProduit(int id) {
        iProduitRepository.deleteById(id);

    }
}
