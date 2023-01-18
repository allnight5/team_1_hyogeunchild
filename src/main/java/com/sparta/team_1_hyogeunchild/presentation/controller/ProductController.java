package com.sparta.team_1_hyogeunchild.presentation.controller;

import com.sparta.team_1_hyogeunchild.business.dto.ProductRequestDto;
import com.sparta.team_1_hyogeunchild.business.dto.ProductResponseDto;
import com.sparta.team_1_hyogeunchild.business.service.ProductService;
import com.sparta.team_1_hyogeunchild.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    @GetMapping()
    public List<ProductResponseDto> getProducts(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return productService.getProducts(userDetails.getUsername());
    }

    @GetMapping("/myproducts")
    public List<ProductResponseDto> getAllProducts(@PageableDefault Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return productService.getAllProducts(pageable.getPageNumber(), userDetails.getUsername());
    }

//    @GetMapping("/all")
//    public List<ProductResponseDto> getAllProducts(){
//        return productService.getAllProducts();
//    }

    @PostMapping("")
    public String uploadProduct(@RequestBody ProductRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.uploadProduct(requestDto, userDetails.getUsername());
    }

    @PutMapping("/{id}")
    public ProductResponseDto updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.updateProduct(requestDto, userDetails.getUsername(), id);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.deleteProduct(id, userDetails.getUsername());
    }

}
