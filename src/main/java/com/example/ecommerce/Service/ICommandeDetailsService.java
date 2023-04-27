package com.example.ecommerce.Service;

import com.example.ecommerce.DAO.Commande;
import com.example.ecommerce.DAO.CommandeDetails;

import java.util.List;

public interface ICommandeDetailsService {

    CommandeDetails addCommandeDetails(CommandeDetails commandeDetails );
    List<CommandeDetails> getAllCommandeDetails();
    CommandeDetails updateCommandeDetails(CommandeDetails commandeDetails);
    CommandeDetails getCommandeDetailsById (int id);
    void deletCommandeDetails(int id);
}
