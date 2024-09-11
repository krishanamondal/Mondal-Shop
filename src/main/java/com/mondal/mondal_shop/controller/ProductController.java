package com.mondal.mondal_shop.controller;

import com.mondal.mondal_shop.dto.ProductDto;
import com.mondal.mondal_shop.exception.ResourceNotFoundException;
import com.mondal.mondal_shop.model.Product;
import com.mondal.mondal_shop.request.AddProductRequest;
import com.mondal.mondal_shop.request.ProductUpdateRequest;
import com.mondal.mondal_shop.response.ApiResponse;
import com.mondal.mondal_shop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.getConvertedProduct(products);
        return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
    }


    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
        try {
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("success", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
@PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try {
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("add product success",theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request,@PathVariable Long productId){
        try {
            Product theProduct = productService.updateProduct(request,productId);
            return ResponseEntity.ok(new ApiResponse("Update product success ! ",theProduct));
        } catch (ResourceNotFoundException e) {
          return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("product deleted successfully",productId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductBrandAndName(@RequestParam String brand, @RequestParam String name){
        try {
           List<Product> products=  productService.getProductsByBrandAndName(brand, name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("product not found ",null));
            }
            List<ProductDto> productDtos = productService.getConvertedProduct(products);
            return ResponseEntity.ok(new ApiResponse("success",productDtos));

        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestParam String category,@RequestParam String brand){
        try {
            List<Product> products=  productService.getProductsByCategoryAndBrand(category, brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("product not found ",null));
            }
            List<ProductDto> productDtos = productService.getConvertedProduct(products);

            return ResponseEntity.ok(new ApiResponse("success",productDtos));

        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @GetMapping("/{name}")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name){
        try {
            List<Product> products=  productService.getProductsByName(name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("product not found ",null));
            }
            List<ProductDto> productDtos = productService.getConvertedProduct(products);
            return ResponseEntity.ok(new ApiResponse("success",productDtos));

        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error",e.getMessage()));
        }
    }

    @GetMapping("/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand){
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("product not found ",null));
            }
            List<ProductDto> productDtos = productService.getConvertedProduct(products);
            return ResponseEntity.ok(new ApiResponse("success ",productDtos));
        }catch (Exception exception){
            return ResponseEntity.ok(new ApiResponse(exception.getMessage(),null));
        }
    }

    @GetMapping("/{category}/all")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("No products found ", null));
            }
            List<ProductDto> productDtos = productService.getConvertedProduct(products);
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

        @GetMapping("/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand,@RequestParam String name){
        try {
            var productCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product Count!",productCount));
        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponse(e.getMessage(),null));
        }
    }
}
