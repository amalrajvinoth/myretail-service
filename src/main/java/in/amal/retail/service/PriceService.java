package in.amal.retail.service;

import in.amal.retail.data.ProductData;
import in.amal.retail.model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

    @Autowired
    ProductData data;

    @Autowired
    ProductService service;

    public Price findPriceByProduct(long productId) {
        return data.findPriceByProduct(productId);
    }

    public String findProductNameById(long id) {
        return service.findProductById(id).getName();
    }
}
