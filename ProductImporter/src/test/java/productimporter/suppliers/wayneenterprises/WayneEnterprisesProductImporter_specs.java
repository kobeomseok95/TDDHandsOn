package productimporter.suppliers.wayneenterprises;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import productimporter.DomainArgumentsSource;
import productimporter.Product;

import java.util.ArrayList;
import java.util.List;

public class WayneEnterprisesProductImporter_specs {

    @ParameterizedTest
    @DomainArgumentsSource
    @DisplayName("상품을 공급한 수 만큼 Iterable을 반환")
    void sut_projects_all_products(WayneEnterprisesProduct[] source) {

        var stub = new WayneEnterprisesProductSourceStub(source);
        var sut = new WayneEnterprisesProductImporter(stub);

        Iterable<Product> actual = sut.fetchProducts();

        assertThat(actual).hasSize(source.length);
    }

     @ParameterizedTest
     @DomainArgumentsSource
     @DisplayName("모든 프로덕트 이름의 공급자가 WAYNE")
     void sut_correctly_sets_supplier_name(WayneEnterprisesProduct[] source) {
         var stub = new WayneEnterprisesProductSourceStub(source);
         var sut = new WayneEnterprisesProductImporter(stub);

         Iterable<Product> actual = sut.fetchProducts();

         assertThat(actual).allMatch(x -> x.getSupplierName().equals("WAYNE"));
     }

     @ParameterizedTest
     @DomainArgumentsSource
     @DisplayName("projection test")
     void sut_correctly_projects_source_properties(WayneEnterprisesProduct source) {
         var stub = new WayneEnterprisesProductSourceStub(source);
         var sut = new WayneEnterprisesProductImporter(stub);

         List<Product> products = new ArrayList<Product>();
         sut.fetchProducts().forEach(products::add);
         Product actual = products.get(0);

         assertThat(actual.getProductCode()).isEqualTo(source.getId());
         assertThat(actual.getProductName()).isEqualTo(source.getTitle());
         assertThat(actual.getPricing().getListPrice()).isEqualByComparingTo(Integer.toString(source.getListPrice()));
         assertThat(actual.getPricing().getDiscount())
                 .isEqualByComparingTo(Integer.toString(source.getListPrice() - source.getSellingPrice()));
     }
}
