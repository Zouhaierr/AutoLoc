package tn.esprit.autoloc.autolocapi.Services;

import tn.esprit.autoloc.autolocapi.domain.Client;

import java.util.List;

public interface IClientService {

    Client addClient(Client client);

    List<Client> getAllClients();

    Client getClientById(Long idClient);

    Client updateClient(Long idClient, Client client);

    void deleteClient(Long idClient);
}