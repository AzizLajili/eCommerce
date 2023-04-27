package com.example.ecommerce.Controller;


import com.example.ecommerce.DAO.Commande;
import com.example.ecommerce.DAO.CommandeDetails;
import com.example.ecommerce.Service.CommandeDetailsServiceImpl;
import com.example.ecommerce.Service.CommandeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Slf4j
@CrossOrigin(origins = "http://localhost:4200/")
public class CommandeDetailsController {

    @Autowired
    CommandeDetailsServiceImpl commandeDetailsService;

    @GetMapping("/GetAllCommandeDetails")

    public List<CommandeDetails> getAllCommandeDetails () {
        return commandeDetailsService.getAllCommandeDetails();
    }


    @PostMapping("/addCommandeDetails")
    public CommandeDetails addCommandeDetails(@RequestBody CommandeDetails commandeDetails){
        return commandeDetailsService.addCommandeDetails(commandeDetails);
    }

    @DeleteMapping("/deleteCommandeDetails/{id}")
    public void deleteCommandeDetails(@PathVariable("id") Integer id){
        commandeDetailsService.deletCommandeDetails(id);
    }

    @GetMapping("/CommandeDetails/{id}")
    public CommandeDetails getCommandeDetailsById(@PathVariable("id") Integer id){
        return commandeDetailsService.getCommandeDetailsById(id);
    }

    @PostMapping("/updateCommandeDetails")
    public CommandeDetails updateCommandeDetails(CommandeDetails commandeDetails){
        return commandeDetailsService.updateCommandeDetails(commandeDetails);
    }
}
