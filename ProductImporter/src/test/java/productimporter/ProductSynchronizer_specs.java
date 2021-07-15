package productimporter;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import productimporter.suppliers.wayneenterprises.WayneEnterprisesProduct;
import productimporter.suppliers.wayneenterprises.WayneEnterprisesProductImporter;
import productimporter.suppliers.wayneenterprises.WayneEnterprisesProductSourceStub;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductSynchronizer_specs {

    @ParameterizedTest
    @DomainArgumentsSource
    @DisplayName("upsert method 확인하기, 입력한 상품들이 잘 입력되는지!(spy)")
    void sut_correctly_saves_products(WayneEnterprisesProduct[] products) {
        var stub = new WayneEnterprisesProductSourceStub(products);
        // 간접 입력
        var importer = new WayneEnterprisesProductImporter(stub);
        var validator = new ListPriceFilter(BigDecimal.ZERO);
        // 간접 출력
        var spy = new ProductInventorySpy();
        var sut = new ProductSynchronizer(importer, validator, spy);

        sut.run();

        Iterable<Product> expected = importer.fetchProducts();
        assertThat(spy.getLog()).usingRecursiveFieldByFieldElementComparator().containsAll(expected);
    }

    @ParameterizedTest
    @DomainArgumentsSource
    @DisplayName("valid test")
    void sut_does_not_save_invalid_product(WayneEnterprisesProduct product) {
        // Arrange
        var lowerBound = new BigDecimal(product.getListPrice() + 10000);
        var validator = new ListPriceFilter(lowerBound);

        var stub = new WayneEnterprisesProductSourceStub(product);
        var importer = new WayneEnterprisesProductImporter(stub);
        var spy = new ProductInventorySpy();
        var sut = new ProductSynchronizer(importer, validator, spy);

        // Act
        sut.run();

        // Assert
        assertThat(spy.getLog()).isEmpty();
    }

    @Test
    @DisplayName("상품 필터링")
    void sut_really_does_not_save_invalid_product() {

        // given
        Pricing pricing = new Pricing(BigDecimal.TEN, BigDecimal.ONE);
        Product product = new Product("supplierNane",
                "productCode", "productName", pricing);

        ProductImporter importer = mock(ProductImporter.class);
        when(importer.fetchProducts()).thenReturn(Arrays.asList(product));
        ProductValidator validator = mock(ProductValidator.class);
        when(validator.isValid(product)).thenReturn(false);
        ProductInventory inventory = mock(ProductInventory.class);

        var sut = new ProductSynchronizer(importer, validator, inventory);

        // when
        sut.run();

        // then
        verify(inventory, never()).upsertProduct(product);
    }
}
