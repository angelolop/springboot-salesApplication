package angelo.com.sales.rest.controller;

import angelo.com.sales.domain.entity.Client;
import angelo.com.sales.service.impl.ClientServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clients")
@Api("Clients API")
public class ClientController {

   @Autowired
   ClientServiceImpl service;

   @GetMapping
   public List<Client> findAllClients() {
      return service.findAllClients();
   }

   @GetMapping("/{id}")
   @ApiOperation("Get details from an client")
   @ApiResponses({
           @ApiResponse(code = 200, message = "Client found"),
           @ApiResponse(code = 404, message = "Client not found from that ID")
   })
   public Client getClientById(@PathVariable Long id) {
      return service.findClientById(id);
   }

   @GetMapping("/filter")
   public List<Client> findClientsByExample(Client clientToFilter) {
      return service.findClientsByExample(clientToFilter);
   }

   @PostMapping()
   @ApiResponses({
           @ApiResponse(code = 201, message = "Client saved with success"),
           @ApiResponse(code = 404, message = "Validation error")
   })
   public ResponseEntity<Client> saveClient(@RequestBody @Valid Client client){
      Client clientInserted = service.insertClient(client);
      URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clientInserted.getId()).toUri();
      return ResponseEntity.created(uri).body(client);
   }

   @ResponseStatus(NO_CONTENT)
   @DeleteMapping("/{id}")
   public void deleteClient(@PathVariable Long id) {
      service.findClientById(id);
      service.deleteClientById(id);
   }

   @ResponseStatus(NO_CONTENT)
   @PutMapping("/{id}")
   public void updateClient(@PathVariable Long id, @RequestBody @Valid Client client) {
      service.findClientById(id);
      service.updateClientById(id, client);
   }
}
