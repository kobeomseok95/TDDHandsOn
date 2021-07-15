package productimporter.suppliers.wayneenterprises;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import productimporter.Pricing;
import productimporter.Product;
import productimporter.ProductImporter;

import static java.util.stream.Collectors.*;

public final class WayneEnterprisesProductImporter implements ProductImporter {

    private final WayneEnterprisesProductSource dataSource;
    private final WayneEnterprisesProductTranslator translator;

    public WayneEnterprisesProductImporter(WayneEnterprisesProductSource dataSource) {
        this.dataSource = dataSource;
        translator = new WayneEnterprisesProductTranslator();
    }

    @Override
    public Iterable<Product>  fetchProducts() {
        return StreamSupport.stream(dataSource.fetchProducts().spliterator(), false)
                .map(translator::translateProduct)
                .collect(toList());
    }
}
