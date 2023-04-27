package com.example.ecommerce.Controller;


import com.example.ecommerce.DAO.Commande;
import com.example.ecommerce.DAO.Produit;
import com.example.ecommerce.Service.CommandeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Slf4j
@CrossOrigin(origins = "http://localhost:4200/")
public class CommandeController {
    @Autowired
    CommandeServiceImpl commandeService;


    @GetMapping("/GetAllCommandes")

    public List<Commande> getAllCommande () {
        return commandeService.getAllCommande();
    }


    @PostMapping("/addCommande")
    public Commande addCommande(@RequestBody Commande commande){
        return commandeService.addCommande(commande);
    }

    @DeleteMapping("/deleteCommande/{id}")
    public void deleteCommande(@PathVariable("id") Integer id){
        commandeService.deleteCommande(id);
    }

    @GetMapping("/Commandes/{id}")
    public Commande getCommandeById(@PathVariable("id") Integer id){
        return commandeService.getCommandeById(id);
    }

    @PostMapping("/updateCommande")
    public Commande updateCommande(Commande commande){
        return commandeService.updateCommande(commande);
    }
}
