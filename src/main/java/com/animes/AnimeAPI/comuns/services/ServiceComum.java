package com.animes.AnimeAPI.comuns.services;

import org.springframework.stereotype.Service;

@Service
public class ServiceComum {
    public String montarEndpoint(String name, String page) {
        StringBuilder endpoint = new StringBuilder("?");
        if (name != null) endpoint.append("q=").append(name).append("&");
        if (page != null) endpoint.append("page=").append(page).append("&");
        if (endpoint.charAt(endpoint.length() - 1) == '&') {
            endpoint.setLength(endpoint.length() - 1);
        }
        return endpoint.toString();
    }
}
