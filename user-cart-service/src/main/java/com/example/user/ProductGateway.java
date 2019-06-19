package com.example.user;

import com.example.model.UserCartException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class ProductGateway {

    private static final Logger log = LoggerFactory.getLogger(ProductGateway.class);

    @Value("${product.url:http://localhost:9091}")
    private String productUrl;

    @Autowired
    private RestTemplate restTemplate;

    public List<Product> getProducts() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String url = productUrl + "/api/product/";

        try {
            /*ResponseEntity<List<Product>> response = restTemplate.exchange(url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Product>>(){});*/

            ResponseEntity<Product[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Product[].class);

            List<Product> productList = Arrays.asList(response.getBody());
            return productList;
        } catch (Exception ex) {
            log.error("Exception occurs whild getting all products");
            log.error("Exception: {}", ex);
            throw new UserCartException(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        }
    }

}
