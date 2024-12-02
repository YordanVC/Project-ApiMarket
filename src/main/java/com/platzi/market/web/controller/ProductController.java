package com.platzi.market.web.controller;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    @ApiOperation("Get all supermarket products")
    @ApiResponse(code=200,message = "OK")
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Search a prodcut by an id")
    @ApiResponses({
            @ApiResponse(code=200,message = "Product found"),
            @ApiResponse(code=404,message = "Product not found")
    })
    public ResponseEntity<Product> getProduct(@ApiParam(value = "The ID of de product",required = true,example = "10")
                                                  @PathVariable("id") int productId) {
        return productService.getProduct(productId)
                .map(product ->new ResponseEntity<>(product,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category/{categoryId}")
    @ApiOperation("Obtain a list of supermarket products by searching for a category ID")
    @ApiResponses({
            @ApiResponse(code=200,message = "Products found"),
            @ApiResponse(code=404,message = "Products not found")
    })
    public ResponseEntity<List<Product>> getByCategory(@ApiParam(value = "The category ID of de product",required = true,example = "3")
                                                           @PathVariable("categoryId") int categoryId) {
        return productService.getByCategory(categoryId)
                .map(products -> new ResponseEntity<>(products,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    @ApiOperation("Save a new supermarket product")
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("Delete a supermarket product by searching for a product ID")
    @ApiResponses({
            @ApiResponse(code=200,message = "Product deleted"),
            @ApiResponse(code=404,message = "Product not found")
    })
    public ResponseEntity delete(@ApiParam(value = "The ID of de product",required = true,example = "50")
                                     @PathVariable("id") int productId) {
        if(productService.delete(productId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
