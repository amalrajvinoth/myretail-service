package in.amal.retail.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.amal.retail.Application;
import in.amal.retail.model.CommonResponse;
import in.amal.retail.model.Price;
import in.amal.retail.model.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class ProductServiceIntegrationTest {

    private static final String BASE_URL = "/retail";
    private static final String HOST = "http://localhost:";

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() throws Exception {
        for (int i = 1; i <= 5; i++) {
            Product product = createProduct("product" + i);
            product.setId((long)i);
            mvc.perform(post(prepareUrl(120 + i))
                    .param("name", product.getName())
                    .param("price", String.valueOf(product.getCurrentPrice().getValue()))
                    .param("currency", product.getCurrentPrice().getCurrencyCode())
                    .content(objectMapper.writeValueAsString(product))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetProductByIdSuccess() throws Exception {
        String url = prepareUrl(121);
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", equalToIgnoringCase("product1")))
                .andExpect(jsonPath("$.data.currentPrice.value", equalTo(12.39)));
    }

    private Product createProduct(String name) {
        Product product = new Product();
        product.setName(name);
        Price price = new Price();
        price.setCurrencyCode("USD");
        price.setValue(12.39);
        product.setCurrentPrice(price);
        return product;
    }

    private String prepareUrl(long id) {
        // http://localhost:8080//api/v1.0/products/{id}
        StringBuilder url = new StringBuilder().append("/products/").append(id);
        return prepareURLWithPort(url.toString());
    }

    private String prepareURLWithPort(String uri) {
        return HOST+port+BASE_URL+uri;
    }
}