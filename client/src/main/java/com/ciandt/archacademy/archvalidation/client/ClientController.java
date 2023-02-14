package com.ciandt.archacademy.archvalidation.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
public class ClientController {

    @Autowired
    private ClientRepository repository;

    @Value("${ERROR_PERCENT:20}")
    private Integer errorPercent;

    private static final Logger LOGGER= LoggerFactory.getLogger(ClientController.class);

    @GetMapping(value = "/client/{id}")
    public ResponseEntity<Client> findById(@PathVariable("id") String id) {
        LOGGER.info("GET by ID" + id);
        return Optional
                .ofNullable(repository.findById(id))
                .map( client -> respostaFindById(client))
                .orElseGet( () -> ResponseEntity.notFound().build());
    }

    private ResponseEntity<Client> respostaFindById(Optional<Client> client) {
        randomizedError();
        return ResponseEntity.ok().body(client.get());
    }

    private void randomizedError() {
        Random r = new Random();
        int randomInt = r.nextInt(100) + 1;
        if(randomInt <= this.errorPercent.intValue()){
            LOGGER.error("GERANDO PROBLEMA "+ String.valueOf(randomInt) + " " + String.valueOf(errorPercent));
            throw new RuntimeException("Problema");
        }
    }

    @GetMapping(value = "/client")
    public ResponseEntity<List<Client>> listAll() {
        LOGGER.info("GET ALL");
        randomizedError();
        List<Client> all = repository.findAll();
        if(all.isEmpty())
            ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(all);
    }


    @PostMapping(path = "/client",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> post(@RequestBody ClientRequest clientRequest) {
        LOGGER.info("POST");
        randomizedError();
        LOGGER.info(clientRequest.toString());
        Client client = repository.save(new Client(clientRequest.getFirstName(), clientRequest.getLastName()));
        if (client == null) {
            return ResponseEntity.internalServerError().build();
        }else {
            return new ResponseEntity<>(client, HttpStatus.CREATED);
        }
    }


}
