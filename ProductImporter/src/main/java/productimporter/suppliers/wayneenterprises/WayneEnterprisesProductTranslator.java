package productimporter.suppliers.wayneenterprises;

import java.math.BigDecimal;

import productimporter.Pricing;
import productimporter.Product;

final class WayneEnterprisesProductTranslator {

    public Product translateProduct(WayneEnterprisesProduct source) {
        return  new Product("WAYNE",
                source.getId(),
                source.getTitle(),
                pricing(source));
    }

    private Pricing pricing(WayneEnterprisesProduct source) {
        int listPrice = source.getListPrice();
        int sellingPrice = source.getSellingPrice();
        BigDecimal discount = new BigDecimal(listPrice - sellingPrice);
        return new Pricing(new BigDecimal(listPrice), discount);
    }
}
