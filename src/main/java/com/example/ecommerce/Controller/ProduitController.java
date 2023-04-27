package com.example.ecommerce.Controller;

import com.example.ecommerce.DAO.Produit;
import com.example.ecommerce.Service.ProduitServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Slf4j
@CrossOrigin(origins = "http://localhost:4200/")
public class ProduitController {
    @Autowired
    ProduitServiceImpl produitService;

    @GetMapping("/GetAllProduits")

    public List<Produit> getAllProduit () {
        return produitService.getAllProduit();
    }


    @PostMapping("/addProduit")
    public Produit addProduit(@RequestBody Produit produit){
        return produitService.addProduit(produit);
    }

    @DeleteMapping("/deleteProduit/{id}")
    public void deleteProduit(@PathVariable("id") Integer id){
        produitService.deleteProduit(id);
    }

    @GetMapping("/Produits/{id}")
    public Produit getProduitById(@PathVariable("id") Integer id){
        return produitService.getProduitById(id);
    }

    @PostMapping("/updatePublication")
    public Produit updatePublication(Produit produit){
        return produitService.updateProduit(produit);
    }
}
