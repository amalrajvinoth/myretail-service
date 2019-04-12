package in.amal.retail.client;

import in.amal.retail.model.Price;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClient {
    private RestTemplate restTemplate = new RestTemplate();

    public String findProductNameById(long id) {
        String url = "http://localhost:8080/retail/product/product-name-by-id";
        ResponseEntity<String> productName
                = restTemplate.getForEntity(url + "/" + id, String.class);
        return productName.getBody();
    }

    public Price findPriceByProduct(long id) {
        String url = "http://localhost:8080/retail/product/";
        ResponseEntity<Price> priceResponseEntity
                = restTemplate.getForEntity(url + "/" + id+"/price", Price.class);
        return priceResponseEntity.getBody();
    }
}
