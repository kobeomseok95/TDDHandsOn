package productimporter.suppliers.starkindustries;

import productimporter.Product;
import productimporter.ProductImporter;

import java.util.ArrayList;

public class StarkIndustriesProductImporter implements ProductImporter {

    private final StarkIndustriesProductSource productSource;
    private final StarkIndustriesProductTranslator translator;

    public  StarkIndustriesProductImporter(StarkIndustriesProductSource productSource,
                                           StarkIndustriesProductTranslator translator) {
        this.productSource = productSource;
        this.translator = translator;
    }

    @Override
    public Iterable<Product> fetchProducts() {
        Iterable<StarkIndustriesProduct> source = productSource.getAllProducts();
        ArrayList<Product> products = new ArrayList<>();
        for (StarkIndustriesProduct s : source) {
            products.add(null);
        }
        return products;
    }
}
