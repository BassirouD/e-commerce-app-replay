# 🛒 E-Commerce Microservices Application
Ce projet est une application e-commerce développée en architecture **microservices** avec **Spring Boot** et **Spring Cloud**. 
Il permet la gestion des clients, produits, commandes, paiements, notifications par email, etc., 
à travers des services découplés et orchestrés via un **API Gateway** et un serveur de configuration centralisé.

## 🚀 Technologies utilisées
- **Spring Boot** — Framework principal
- **Spring Cloud** — Eureka, Config Server, Gateway, etc.
- **Spring Cloud Config Server** — Centralisation des fichiers YAML de config
- **Spring Cloud Gateway** — Entrée unique vers tous les services
- **Spring Boot Mail + Thymeleaf** — Pour les emails HTML
- **Spring Kafka** — Pour la communication asynchrone entre services
- **Feign Client** ou **RestTemplate** — Pour la communication synchrone entre services
- **Docker Compose** — Pour orchestrer les conteneurs locaux

---

## 📦 Microservices présents

| Microservice       | Description |
|--------------------|-------------|
| **customer-service** | Gère les clients (CRUD, vérification d’existence) |
| **product-service** | Gère les produits (ajout, achat, listing) |
| **order-service**   | Gère les commandes (création, consultation) |
| **payment-service** | Enregistre les paiements et envoie des events Kafka |
| **notification-service** | Consomme Kafka pour envoyer des emails (confirmation commande/paiement) |
| **config-server**   | Contient tous les fichiers `application.yml` des services |
| **eureka-server**   | Service registry pour la découverte dynamique des services |
| **gateway-service** | Fournit un point d’entrée unique pour accéder aux autres services via des routes `lb://` |

---

## 🔁 Communication entre microservices

### 🌐 Synchronous — REST via FeignClient (ou RestTemplate)

Les services utilisent **FeignClient** pour interagir les uns avec les autres via HTTP.

#### 🔍 Exemple 1 : order-service appelle customer-service via Feign

```java
@FeignClient(
        name = "customer-service",
        url = "${application.config.customer-url}"
)
public interface CustomerClient {
    @GetMapping("/{customer-id}")
    Optional<CustomerResponse> findCustomerById(@PathVariable("customer-id") String customerId);
}
```

#### 🔍 Exemple 2 : order-service appelle product-service via restTemplate
```java
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
```

## Docker Compose
L'ensemble de l’infrastructure est contenue dans un docker-compose.yml:
- postgres, pgadmin pour services SQL
- mongodb, mongo-express pour services NoSQL
- zookeeper, kafka pour les événements
- zipkin pour le tracing distribué
- mail-dev pour simuler l’envoi d’emails
- keycloak pour la sécurité OAuth2

### ➤ Lancement
Lancer tous les services nécessaires (bases de données, Kafka, Eureka, etc.).
  ```bash
  docker-compose up -d
  ```

### 🛑 Arrêt de l'infrastructure
Stopper et supprimer tous les conteneurs créés.
  ```bash
  docker-compose down
  ```
