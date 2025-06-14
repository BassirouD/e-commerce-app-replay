package com.koula.ecommerce.product;


import com.koula.ecommerce.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.POST;

@Service
public class ProductClient {
    @Value("${application.config.product-url}")
    private String productUrl;
    private final RestTemplate restTemplate;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PurchaseResponse> purchaseProduct(List<PurchaseRequest> requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(requestBody, headers);
        ParameterizedTypeReference<List<PurchaseResponse>> responseType = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<PurchaseResponse>> responseEntity = restTemplate.exchange(
                productUrl + "/purchase",
                POST,
                requestEntity,
                responseType
        );
        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException("An error occurred while processing the product purchase: " + responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }

}
