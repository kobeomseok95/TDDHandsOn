package productimporter;

import java.util.ArrayList;
import java.util.List;

public final class ProductInventorySpy implements ProductInventory {

    private ArrayList<Product> log;

    public ProductInventorySpy() {
        log = new ArrayList<Product>();
    }

    public ArrayList<Product> getLog() {
        return log;
    }

    @Override
    public void upsertProduct(Product product) {
        log.add(product);
    }
}
