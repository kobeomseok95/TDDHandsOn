package productimporter.suppliers.starkindustires;

import productimporter.DomainArgumentResolver;
import productimporter.suppliers.starkindustries.StarkIndustriesProduct;

import java.util.Optional;
import java.util.UUID;

public class StarkIndustiresProductArgumentResolver implements DomainArgumentResolver {
    @Override
    public Optional<Object> tryResolve(Class<?> parameterType) {
        if (parameterType.equals(StarkIndustriesProduct.class)) {
            return Optional.of(generate());
        } else if (parameterType.equals(StarkIndustriesProduct[].class)) {
            return Optional.of(new StarkIndustriesProduct[] { generate(), generate(), generate() });
        }

        return Optional.empty();
    }

    private static StarkIndustriesProduct generate() {
        String code = "code" + UUID.randomUUID().toString();
        String name = "name" + UUID.randomUUID().toString();
        int listPrice = random.nextInt(100000) + 100000;
        int discountAmount = random.nextInt(10000) + 10000;
        return new StarkIndustriesProduct(code, name, listPrice, discountAmount);
    }
}
