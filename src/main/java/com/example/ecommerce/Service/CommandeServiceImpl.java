package com.example.ecommerce.Service;

import com.example.ecommerce.DAO.Commande;
import com.example.ecommerce.DAO.ICommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeServiceImpl implements  ICommandeService{

    @Autowired
    ICommandeRepository iCommandeRepository;
    @Override
    public Commande addCommande(Commande commande) {
        return iCommandeRepository.save(commande);
    }

    @Override
    public List<Commande> getAllCommande() {
        return iCommandeRepository.findAll();
    }

    @Override
    public Commande updateCommande(Commande commande) {
        return iCommandeRepository.save(commande);
    }

    @Override
    public Commande getCommandeById(int id) {
        return iCommandeRepository.findById(id).get();
    }

    @Override
    public void deleteCommande(int id) {
        iCommandeRepository.deleteById(id);

    }
}
