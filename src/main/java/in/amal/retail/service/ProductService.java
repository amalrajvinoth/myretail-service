package in.amal.retail.service;

import in.amal.retail.model.Price;
import in.amal.retail.model.Product;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface ProductService {

    Product findProductById(long id);

    String findProductNameById(long id);

    Product updateProduct(Product product);

    Product createProduct(Product product);

    Product deleteProduct(long id);

    Price findPriceByProduct(long productId);

    Collection<Product> getAll();
}
