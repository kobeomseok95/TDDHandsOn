package productimporter;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class ProductSynchronizer {

    private final ProductImporter importer;
    private final ProductValidator validator;
    private final ProductInventory inventory;

    public ProductSynchronizer(ProductImporter importer, ProductValidator validator, ProductInventory inventory) {
        this.importer = importer;
        this.validator = validator;
        this.inventory = inventory;
    }

    public void run() {
        StreamSupport.stream(importer.fetchProducts().spliterator(), false)
                .filter(validator::isValid)
                .forEach(inventory::upsertProduct);
    }
}
