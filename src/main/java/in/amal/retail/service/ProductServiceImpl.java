package in.amal.retail.service;

import in.amal.retail.client.RestClient;
import in.amal.retail.data.ProductData;
import in.amal.retail.model.Price;
import in.amal.retail.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private static final Logger LOG = LogManager.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductData data;

    @Autowired
    RestClient restClient;

    public Product findProductById(long id) {
        Optional<Product> product = data.findById(id);
        if(!product.isPresent()) {
            throw new RuntimeException("Product not found with id: "+id);
        }
        Product p = product.get();
        //p.setName(findProductNameById(id));
        //p.setCurrentPrice(findPriceByProduct(id));
        return p;
    }

    public String findProductNameById(long id) {
        return restClient.findProductNameById(id);
    }

    public Product updateProduct(Product product) {
        if(product == null) {
            throw new RuntimeException("Product cannot be null");
        }
        return data.update(product);
    }

    public Product createProduct(Product product) {
        if(product == null) {
            throw new RuntimeException("Product cannot be null");
        }
        return data.save(product);
    }

    public Product deleteProduct(long id) {
        return data.delete(id);
    }

    public Price findPriceByProduct(long productId) {
        return restClient.findPriceByProduct(productId);
    }
    public Collection<Product> getAll() {
        return data.getAll();
    }


}
