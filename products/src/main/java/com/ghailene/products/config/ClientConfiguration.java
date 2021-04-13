package com.ghailene.products.config;

import feign.RequestInterceptor;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ClientConfiguration {
    // intercepter le traffic lorsqu'on a un api à un autre micor service fein on doit passer le token dans le header lorsqu'on a un appel api
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            //get authentification object
            KeycloakSecurityContext context = getKeycloakSecurityContext();
            // set token in header authorization
            requestTemplate.header("Authorization", "Bearer "+context.getTokenString());
        };
    }

// this used to get the authentification object from spring security context holder
    protected KeycloakSecurityContext getKeycloakSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        KeycloakAuthenticationToken token;
        KeycloakSecurityContext context;

        if (authentication == null) {
            throw new IllegalStateException("Cannot set authorization header because there is no authenticated principal");
        }

        if (!KeycloakAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            throw new IllegalStateException(
                    String.format(
                            "Cannot set authorization header because Authentication is of type %s but %s is required",
                            authentication.getClass(), KeycloakAuthenticationToken.class)
            );
        }

        token = (KeycloakAuthenticationToken) authentication;
        context = token.getAccount().getKeycloakSecurityContext();

        return context;
    }

}
