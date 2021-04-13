package com.ghailene.products.controllers;

import com.ghailene.products.models.Product;
import com.ghailene.products.models.Provider;
import com.ghailene.products.proxies.ProviderProxy;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProviderProxy providerProxy;

    private List<Product> products = Stream.of(
            new Product("1", "product1", "1"),
            new Product("3", "product1", "3"),
            new Product("2", "product2", "2")).collect(Collectors.toList());

    @GetMapping
    public List<Product> getAll() {
        return products;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getDetails(@PathVariable("id") String id){
        Optional<Product> product = products.stream()
                .filter(p-> id.equals(p.getId())).findFirst();
        if (!product.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Product productToReturn = product.get();
        Provider provider= providerProxy.getDetails(productToReturn.getProviderId());
        productToReturn.setProvider(provider);
        return new ResponseEntity<Product>(product.get(),HttpStatus.ACCEPTED);
    }


}
