package org.dentech.Services;

import org.dentech.Entities.Client;
import org.dentech.Repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepo clientRepo;

    public ClientService() {
    }

    public void delete(Client client) {
        clientRepo.delete(client);
    }

    public void deleteById(Long id) {
        clientRepo.deleteById(id);
    }

    public List<Client> getAll() {
        return clientRepo.findAll();
    }

    public List<Client> getAllSort(){
        List<Client> list = clientRepo.findAll();
        Collections.sort(list);
        return list;
    }

    public Optional<Client> findClient(long passport) {
        Client client = new Client ();
        client.setPassport(passport);
        return clientRepo.findOne(Example.of(client));
    }

    public void save(Client client){
        clientRepo.save(client);
        System.out.println("Save a new client " + client.toString());
    }
}
