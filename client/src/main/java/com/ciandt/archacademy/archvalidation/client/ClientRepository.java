package com.ciandt.archacademy.archvalidation.client;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClientRepository extends MongoRepository<Client, String> {

    public Client findByFirstName(String firstName);
    public List<Client> findByLastName(String lastName);

}
