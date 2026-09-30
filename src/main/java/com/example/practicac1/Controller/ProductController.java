package com.example.practicac1.Controller;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<PagedResponseDto<ProductDto>>
    getAllProducts(
            @PageableDefault(
                    page = 0,
                    size = 10
            )
            Pageable pageable) {

        Page<ProductDto> productsPage =
                productService.getAllProducts(pageable);

        PagedResponseDto<ProductDto> responseDto =
                new PagedResponseDto<>(productsPage);

        return ResponseEntity.ok(responseDto);
    }
}
