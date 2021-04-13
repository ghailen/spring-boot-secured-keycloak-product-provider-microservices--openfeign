package com.ghailene.providers.controllers;

import com.ghailene.providers.models.Provider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/providers")
public class ProviderController {

    private List<Provider> providers = Stream.of(
            new Provider("1","provider1"),
            new Provider("1","provider12")
    ).collect(Collectors.toList());

    @GetMapping("/{id}")
    public ResponseEntity<Provider> getDetails(@PathVariable("id") String id){
        Optional<Provider> provider = providers.stream()
                .filter(p-> id.equals(p.getId())).findFirst();
        if (!provider.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Provider>(provider.get(),HttpStatus.ACCEPTED);
    }


}
