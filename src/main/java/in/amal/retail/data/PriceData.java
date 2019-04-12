package in.amal.retail.data;

import in.amal.retail.model.Price;
import org.springframework.data.repository.CrudRepository;

public interface PriceData extends CrudRepository<Price, Long> {
    // Will be using default query methods
}