package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {

    private ProductService productService;
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> getAllCategories() {
        return categoryService.findAllByActivated();
    }

    @GetMapping("/products")
    public String products(Model model){
        List<ProductDto> products= productService.findAll();
        model.addAttribute("products", products);
        return "shop";
    }

    @GetMapping("/find-product/{id}")
    public String findProductById(@PathVariable("id") Long id, Model model){
        Product product = productService.getProductById(id);
        List<Category> categories = categoryService.findAllByActivated();
        model.addAttribute("categories", categories);
        Long categoryId = product.getCategory().getId();
        List<Product> products = productService.getRelatedProductByCategoryId(categoryId);
        model.addAttribute("product", product);
        model.addAttribute("products", products);
        return "detail";
    }
    @GetMapping("/product-in-category/{id}")
    public String getProductInCategory(@PathVariable("id") Long categoryId, Model model){
        Category category = categoryService.findById(categoryId);
        List<Product> products = productService.getProductsInCategory(categoryId);
        List<Category> categories = categoryService.findAllByActivated();
        model.addAttribute("categories", categories);
        model.addAttribute("category", category);
        model.addAttribute("products", products);
        return "products-in-category";
    }

    @GetMapping("/high-price")
    public String filterHighPrice(Model model){

        List<Product> products = productService.filterHighPrice();
        model.addAttribute("products", products);
        return "high-filter";
    }

    @GetMapping("/low-price")
    public String filterLowPrice(Model model){
        List<Product> products = productService.filterLowPrice();
        model.addAttribute("products", products);
        return "low-filter";
    }

//    @GetMapping("/search-product")
//    public String searchProduct(@RequestParam("keyword") String keyword, Model model) {
//        List<ProductDto> productDtos = productService.searchProducts(keyword);
//        model.addAttribute("title", "Search Products");
//        model.addAttribute("page", "Result Search");
//        model.addAttribute("products", productDtos);
//        return "search-result";
//    }

    @GetMapping("/products/{pageNo}")
    public String productsPage(@PathVariable("pageNo") int pageNo,
                               Model model, Principal principal ){
        if (principal == null){
            return "redirect:/login";
        }
        Page<ProductDto> productDtoList = productService.pageProducts(pageNo);
        model.addAttribute("title", "Shop");
        model.addAttribute("size", productDtoList.getSize());
        model.addAttribute("totalPage",productDtoList.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("productList", productDtoList);
        return "shop";

    }

    @GetMapping("/search-result/{pageNo}")
    public String searchProducts(@PathVariable("pageNo") int pageNo,
                                 Model model, Principal principal,
                                 @RequestParam("keyword") String keyword){
        if (principal == null){
            return "redirect:/login";
        }

        Page<ProductDto> productDtoList;
        if (pageNo == 0){
            productDtoList = productService.searchProducts(0,keyword);
            model.addAttribute("keyword", keyword);
        }else {
            productDtoList = productService.searchProducts(pageNo,keyword);
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("title", "Search Result");
        model.addAttribute("size", productDtoList.getSize());
        model.addAttribute("totalPage",productDtoList.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("productList", productDtoList);
        return "search-result";
    }
}
