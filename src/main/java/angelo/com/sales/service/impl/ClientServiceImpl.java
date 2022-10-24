package angelo.com.sales.service.impl;

import angelo.com.sales.domain.entity.Client;
import angelo.com.sales.domain.repository.ClientRepository;
import angelo.com.sales.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

   @Autowired
   private ClientRepository repository;

   public List<Client> findAllClients() {
      return repository.findAll();
   }

   public Client findClientById(Long id) {
      Optional<Client> client = repository.findById(id);
      return client.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
   }

   public List<Client> findClientsByExample(Client clientToFilter) {
      ExampleMatcher matcher = ExampleMatcher
                                .matching()
                                .withIgnoreCase()
                                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
      Example<Client> example = Example.of(clientToFilter, matcher);
      return repository.findAll(example);
   }

   public Client insertClient(Client client) {
      return repository.save(client);
   }

   public void deleteClientById(Long id) {
      repository.deleteById(id);
   }

   public void updateClientById(Long id, Client client) {
      repository.findById(id)
                  .map(clientToUpdate -> {
                     client.setId(clientToUpdate.getId());
                     return repository.save(client);
                  }).orElseThrow();
   }
}
