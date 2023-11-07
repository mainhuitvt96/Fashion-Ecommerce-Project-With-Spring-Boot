package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String products(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        List<ProductDto> productDtoList = productService.findAll();
        model.addAttribute("productList", productDtoList);
        model.addAttribute("size", productDtoList.size());
        model.addAttribute("title", "Manage Product");
        return "products";
    }
    @GetMapping("/products/{pageNo}")
    public String productsPage(@PathVariable("pageNo") int pageNo,
                               Model model, Principal principal ){
        if (principal == null){
            return "redirect:/login";
        }
        Page<ProductDto> productDtoList = productService.pageProducts(pageNo);
        model.addAttribute("title", "Manage Product");
        model.addAttribute("size", productDtoList.getSize());
        model.addAttribute("totalPage",productDtoList.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("productList", productDtoList);
        return "products";

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
//    @GetMapping("/search-result/{pageNo}/")

    @GetMapping("/add-new-product")
    public String addProductForm(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAllByActivated();
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Add product");
        model.addAttribute("product", new ProductDto());

        return "add-new-product";
    }

    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("product") ProductDto productDto,
                              @RequestParam("imageProduct")MultipartFile imageProduct,
                              RedirectAttributes attributes){
        try {
            productService.save(imageProduct,productDto);
            attributes.addFlashAttribute("success", "Add new product successfully!");

        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to add!");

        }
        return "redirect:/products/0";
    }

    @GetMapping("/update-product/{id}")
    public String updateProduct(@PathVariable("id") Long id, Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        try {
           model.addAttribute("title","Update Product");
           List<Category> categories = categoryService.findAllByActivated();
           ProductDto productDto = productService.getById(id);
           model.addAttribute("categories", categories);
           model.addAttribute("productDto", productDto);

        }catch (Exception e){
            e.printStackTrace();
        }
        return "update-product";
    }

    @PostMapping("/update-product/{id}")
    public String processUpdate(@ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct") MultipartFile imageProduct,
                                RedirectAttributes attributes){
        try {
            productService.update(imageProduct, productDto);
            attributes.addFlashAttribute("success", "Update successfully!");

        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");

        }
        return "redirect:/products/0";
    }
    @RequestMapping(value = "/delete-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            productService.deleteById(id);
            attributes.addFlashAttribute("success", "Delete successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to delete!");
        }

        return "redirect:/products/0";
    }

    @RequestMapping(value = "/enable-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enableProduct(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            productService.enableById(id);
            attributes.addFlashAttribute("success", "Enable successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Enable to delete!");
        }

        return "redirect:/products/0";
    }
}
