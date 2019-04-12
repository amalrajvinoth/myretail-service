package in.amal.retail.web;

import in.amal.retail.model.CommonResponse;
import in.amal.retail.model.Price;
import in.amal.retail.model.Product;
import in.amal.retail.service.ProductService;
import in.amal.retail.util.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/retail")
@Api(value = "Product APIs")
public class ProductController {

    private static final Logger LOG = LogManager.getLogger(ProductController.class);

    @Autowired
    private ProductService service;

    @ApiOperation(value = "Get product by id.")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Not Found")})
    @GetMapping("/products/{id}")
    public ResponseEntity<CommonResponse> getProduct(@PathVariable long id) {
        return Utils.buildSuccessResponse(service.findProductById(id));
    }

    @ApiOperation(value = "Update product details")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Not Found")})
    @PutMapping("/products/{id}")
    public ResponseEntity<CommonResponse> updateProduct(@PathVariable long id, @RequestParam("name") String name,
                                                        @RequestParam("price") double priceValue,
                                                        @RequestParam("currency") String currency) {
        Price price = new Price();
        price.setCurrencyCode(currency);
        price.setValue(priceValue);
        Product product = new Product(name, price);
        product.setId(id);
        return Utils.buildSuccessResponse(service.updateProduct(product));
    }

    @ApiOperation(value = "Create a new product")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Not Found")})
    @PostMapping("/products/{id}")
    public ResponseEntity<CommonResponse> createProduct(@PathVariable long id,
                                                        @RequestParam("name") String name,
                                                        @RequestParam("price") double priceValue,
                                                        @RequestParam("currency") String currency) {
        Price price = new Price();
        price.setCurrencyCode(currency);
        price.setValue(priceValue);
        Product product = new Product(name, price);
        product.setId(id);
        return Utils.buildSuccessResponse(service.createProduct(product));
    }

    @ApiOperation(value = "Delete a existing product byid")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Not Found")})
    @DeleteMapping("/products/{id}")
    public ResponseEntity<CommonResponse> deleteProduct(@PathVariable long id) {
        return Utils.buildSuccessResponse(service.deleteProduct(id));
    }

    @ApiOperation(value = "Delete a existing product byid")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Not Found")})
    @GetMapping("/products/all")
    public ResponseEntity<CommonResponse> getAllProduct() {
        return Utils.buildSuccessResponse(service.getAll());
    }
}
