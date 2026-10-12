package tn.esprit.autoloc.autolocapi.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.Repositories.ClientRepository;
import tn.esprit.autoloc.autolocapi.domain.Client;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientService implements IClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client addClient(Client client) {
        if (client.getDateInscription() == null) {
            client.setDateInscription(LocalDate.now());
        }
        return clientRepository.save(client);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Long idClient) {
        return clientRepository.findById(idClient)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id " + idClient));
    }

    @Override
    public Client updateClient(Long idClient, Client client) {
        Client existing = getClientById(idClient);
        existing.setNom(client.getNom());
        existing.setPrenom(client.getPrenom());
        existing.setEmail(client.getEmail());
        existing.setTelephone(client.getTelephone());
        existing.setNumPermis(client.getNumPermis());
        return clientRepository.save(existing);
    }

    @Override
    public void deleteClient(Long idClient) {
        Client existing = getClientById(idClient);
        clientRepository.delete(existing);
    }
}