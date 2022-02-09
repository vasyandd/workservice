package work.app.product;

public interface ProductService {
    void saveProduct(ProductDto productDto);

    Product findByName(String productNumber);
}
