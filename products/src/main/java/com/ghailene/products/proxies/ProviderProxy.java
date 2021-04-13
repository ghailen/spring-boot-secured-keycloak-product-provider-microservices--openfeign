package com.ghailene.products.proxies;

import com.ghailene.products.config.ClientConfiguration;
import com.ghailene.products.models.Provider;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "providers-service", url = "${microservice.providers.url}",configuration = {ClientConfiguration.class})
public interface ProviderProxy {


    @GetMapping(value = "/providers/{id}")
    public Provider getDetails(@PathVariable("id") String id);

}
