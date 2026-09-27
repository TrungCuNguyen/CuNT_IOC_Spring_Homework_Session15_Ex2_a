package api.ex_ss15_ioc.controller;

import api.ex_ss15_ioc.dto.request.ProductRequest;
import api.ex_ss15_ioc.dto.response.ApiResponse;
import api.ex_ss15_ioc.entity.Product;
import api.ex_ss15_ioc.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Xem danh sách sản phẩm (Mọi user có thể truy cập)[cite: 2]
    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() { //[cite: 2]
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    // Xem chi tiết sản phẩm
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success(product));
    }

    // Thêm mới sản phẩm (Chỉ ADMIN/STAFF)[cite: 2]
    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@Valid @RequestBody ProductRequest request) { //[cite: 2]
        Product createdProduct = productService.createProduct(request);
        return ResponseEntity.ok(ApiResponse.success(createdProduct));
    }

    // Cập nhật sản phẩm (Chỉ ADMIN/STAFF)[cite: 2]
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct( //[cite: 2]
                                                               @PathVariable Long id,
                                                               @Valid @RequestBody ProductRequest request) {
        Product updatedProduct = productService.updateProduct(id, request);
        return ResponseEntity.ok(ApiResponse.success(updatedProduct));
    }

    // Xóa sản phẩm (Chỉ ADMIN/STAFF)[cite: 2]
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable Long id) { //[cite: 2]
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa sản phẩm thành công!"));
    }
}