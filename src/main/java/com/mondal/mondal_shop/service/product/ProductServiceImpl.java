package com.mondal.mondal_shop.service.product;

import com.mondal.mondal_shop.dto.ImageDto;
import com.mondal.mondal_shop.dto.ProductDto;
import com.mondal.mondal_shop.exception.ProductNotFoundException;
import com.mondal.mondal_shop.model.Category;
import com.mondal.mondal_shop.model.Image;
import com.mondal.mondal_shop.model.Product;
import com.mondal.mondal_shop.repository.CategoryRepository;
import com.mondal.mondal_shop.repository.ImageRepository;
import com.mondal.mondal_shop.repository.ProductRepository;
import com.mondal.mondal_shop.request.AddProductRequest;
import com.mondal.mondal_shop.request.ProductUpdateRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;
    @Override
    public Product addProduct(AddProductRequest request) {
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() ->{
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request,category));
    }

    public Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrise(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->new ProductNotFoundException("Product Not Found !!"));
    }

    @Override
    public void deleteProductById(Long id) {
           productRepository.findById(id)
                   .ifPresentOrElse(productRepository::delete,
                           () -> {throw new ProductNotFoundException("Product Not Found !!");});
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(exitingProduct -> updateProduct(exitingProduct,request))
                .map(productRepository :: save)
                .orElseThrow( () -> new ProductNotFoundException("Product Not Found"));
    }
    private Product updateProduct(Product exitingProduct, ProductUpdateRequest request){
        exitingProduct.setName(request.getName());
        exitingProduct.setBrand(request.getBrand());
        exitingProduct.setPrise(request.getPrise());
        exitingProduct.setInventory(request.getInventory());
        exitingProduct.setDescription(request.getDescription());
        Category category = categoryRepository.findByName(request.getCategory().getName());
        exitingProduct.setName(category.getName());
        exitingProduct.setCategory(category);
        return exitingProduct;
    }
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }
    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }


    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
    @Override
    public List<ProductDto> getConvertedProduct(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }
    @Override
    public ProductDto convertToDto(Product product){
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}
