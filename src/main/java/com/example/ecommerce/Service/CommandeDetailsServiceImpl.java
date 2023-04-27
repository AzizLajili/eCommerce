package com.example.ecommerce.Service;

import com.example.ecommerce.DAO.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommandeDetailsServiceImpl implements ICommandeDetailsService{
    @Autowired
    ICommandeDetailsRepository iCommandeDetailsRepository;
    @Autowired
    ICommandeRepository iCommandeRepository;
    @Autowired
    IProduitRepository iProduitRepository;

    @Override
    public CommandeDetails addCommandeDetails(CommandeDetails commandeDetails) {
      
        return iCommandeDetailsRepository.save(commandeDetails);
    }

    @Override
    public List<CommandeDetails> getAllCommandeDetails() {
        return iCommandeDetailsRepository.findAll();
    }

    @Override
    public CommandeDetails updateCommandeDetails(CommandeDetails commandeDetails) {
        return iCommandeDetailsRepository.save(commandeDetails);
    }

    @Override
    public CommandeDetails getCommandeDetailsById(int id) {
        return iCommandeDetailsRepository.findById(id).get();
    }

    @Override
    public void deletCommandeDetails(int id) {
        iCommandeDetailsRepository.deleteById(id);

    }


}
