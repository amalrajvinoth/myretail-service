package in.amal.retail.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class Product implements Serializable {
    private Long id;
    private String name;
    @Valid
    @JsonAlias("current_price")
    private Price currentPrice;


    public Product() {
    }
    public Product(String name, Price currentPrice) {
        this.name = name;
        this.currentPrice = currentPrice;
    }
}
