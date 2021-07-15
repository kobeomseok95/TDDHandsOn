package productimporter.suppliers.wayneenterprises;

import java.util.ArrayList;
import java.util.Arrays;

public class WayneEnterprisesProductSourceStub implements WayneEnterprisesProductSource {

    private WayneEnterprisesProduct[] products;

    public WayneEnterprisesProductSourceStub(WayneEnterprisesProduct... products) {
        this.products = products;
    }

    @Override
    public Iterable<WayneEnterprisesProduct> fetchProducts() {
        return Arrays.asList(products);
    }
}
