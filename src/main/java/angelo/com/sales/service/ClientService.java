package angelo.com.sales.service;

import angelo.com.sales.domain.entity.Client;

import java.util.List;

public interface ClientService {

   List<Client> findAllClients();

   Client findClientById(Long id);

   List<Client> findClientsByExample(Client clientToFilter);

   Client insertClient(Client client);

   void deleteClientById(Long id);

   void updateClientById(Long id, Client client);
}
