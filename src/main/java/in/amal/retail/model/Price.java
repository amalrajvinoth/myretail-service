package in.amal.retail.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Price implements Serializable {
    private double value;
    @JsonAlias("currency_code")
    private String currencyCode;
}
