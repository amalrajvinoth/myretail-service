package in.amal.retail.web;

import in.amal.retail.model.Price;
import in.amal.retail.model.Product;
import in.amal.retail.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/retail/product")
@Api(value="Price APIs")
public class PriceController {

    private static final Logger LOG = LogManager.getLogger(PriceController.class);

    @Autowired
    private ProductService service;

    @ApiOperation(value = "Get product name by id.")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Not Found")})
    @GetMapping("/product-name-by-id/{id}")
    public String getProductNameById(@PathVariable long id) {
        return service.findProductById(id).getName();
    }


    @ApiOperation(value = "Get price by product")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Not Found")})
    @GetMapping("/{productId}/price")
    public Price getPrice(@PathVariable long productId) {
        return service.findPriceByProduct(productId);
    }
}
