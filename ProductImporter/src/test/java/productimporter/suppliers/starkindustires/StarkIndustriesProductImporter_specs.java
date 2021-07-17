package productimporter.suppliers.starkindustires;

import org.junit.jupiter.params.ParameterizedTest;
import productimporter.DomainArgumentsSource;
import productimporter.Product;
import productimporter.suppliers.starkindustries.StarkIndustriesProduct;
import productimporter.suppliers.starkindustries.StarkIndustriesProductImporter;
import productimporter.suppliers.starkindustries.StarkIndustriesProductSource;
import productimporter.suppliers.starkindustries.StarkIndustriesProductTranslator;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StarkIndustriesProductImporter_specs {

    @ParameterizedTest
    @DomainArgumentsSource
    void stu_projects_all_products(StarkIndustriesProduct[] sourceProducts) {

        StarkIndustriesProductSource dataSource = mock(StarkIndustriesProductSource.class);
        when(dataSource.getAllProducts()).thenReturn(Arrays.asList(sourceProducts));

        StarkIndustriesProductTranslator translator = mock(StarkIndustriesProductTranslator.class);

        StarkIndustriesProductImporter sut = new StarkIndustriesProductImporter(dataSource, translator);

        Iterable<Product> actual = sut.fetchProducts();
        assertThat(actual).hasSize(sourceProducts.length);
    }

//    @ParameterizedTest
//    @DomainArgumentsSource
//    void sut_correctly_translaters_source_products(StarkIndustriesProduct[] sourceProducts, Product[] products) {
//
//        StarkIndustriesProductSource productSource =  mock(StarkIndustriesProductSource.class);
//        when(productSource.getAllProducts()).thenReturn(Arrays.asList(sourceProducts));
//        StarkIndustriesProductTranslator translator = mock(StarkIndustriesProductTranslator.class);
//
//        new StarkIndustriesProductImporter(sourceProducts, translator);
//        sut.fetchProducts();
//    }
}
