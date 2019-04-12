package in.amal.retail.data;

import in.amal.retail.model.Price;
import in.amal.retail.model.Product;
import in.amal.retail.web.ProductController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProductData {
    private static final Logger LOG = LogManager.getLogger(ProductController.class);
    private ConcurrentHashMap<Long, Product> products = new ConcurrentHashMap<>();
    private Random random = new Random();

    @PostConstruct
    private void init() {
        random.setSeed(1);
        // Test Data
        for (int i = 1; i < 5; i++) {
            Price price = new Price();
            price.setValue(random.nextInt(10000000));
            price.setCurrencyCode("USD");
            Product product = new Product("Product" + i, price);
            product.setId(Long.valueOf(i));
            save(product);
        }
    }

    public Optional<Product> findById(long id) {
        Product product = products.get(id);
        return Optional.ofNullable(product);
    }

    public Product update(Product product) {
        Product existing = products.get(product.getId());
        if (existing == null) {
            throw new RuntimeException("Product not found with id: " + product.getId());
        }
        if (product.getCurrentPrice() != null) {
            existing.setCurrentPrice(product.getCurrentPrice());
            existing.setName(product.getName());
        }

        LOG.info("Updated product: {}", product);
        return product;
    }

    public Product save(Product product) {
        if (products != null && products.containsKey(product.getId())) {
            throw new RuntimeException("Product already exist id: " + product.getId());
        }
        products.put(product.getId(), product);
        LOG.info("Created product: {}", product);
        return product;
    }

    public Price findPriceByProduct(long productId) {
        return Optional.ofNullable(products.get(productId).getCurrentPrice()).orElse(null);
    }

    public Product delete(long id) {
        return products.remove(id);
    }

    public Collection<Product> getAll() {
        return products.values();
    }
}
