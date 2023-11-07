package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@AllArgsConstructor
public class CartController {

    private ProductService productService;
    private ShoppingCartService shoppingCartService;
    private CustomerService customerService;
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> getAllCategories() {
        return categoryService.findAllByActivated();
    }


    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session){
        model.addAttribute("title", "Cart");
        if (principal == null){
            return "redirect:/login";
        }else {
            String username = principal.getName();
            Customer customer = customerService.findByUsername(username);
            ShoppingCart shoppingCart = customer.getShoppingCart();
            if (shoppingCart == null){
                model.addAttribute("check", "No item in your cart!");

            }
            if (shoppingCart != null) {
                model.addAttribute("grandTotal", shoppingCart.getTotalPrice());
            }
            model.addAttribute("shoppingCart", shoppingCart);

            return "cart";
        }
    }

    @PostMapping("/add-to-cart")
    public String addItemToCart(HttpServletRequest request,
                                @RequestParam("id") Long productId, // ĐỂ Ở BODY
                                @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                                Principal principal,
                                HttpSession session,
                                Model model){
        ProductDto productDto = productService.getById(productId);
        if (principal == null){
                return "redirect:/login";
        }else {
            String username = principal.getName();
            ShoppingCart shoppingCart = shoppingCartService.addItemToCart(productDto, quantity, username);
            session.setAttribute("totalItems", shoppingCart.getTotalItems());
            model.addAttribute("shoppingCart", shoppingCart);
        }
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/update-cart")
    public String updateCartOfInput(@RequestParam("quantity") int quantity,
                             @RequestParam("id") Long productId,
                             Model model,
                             Principal principal){
        if (principal == null){
            return "redirect:/login";
        }else {
            ProductDto productDto = productService.getById(productId);
            String username = principal.getName();
            ShoppingCart shoppingCart = shoppingCartService.updateCart(productDto, quantity, username);
            model.addAttribute("shoppingCart", shoppingCart);
            return "redirect:/cart";
        }

    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("quantity") int quantity,
                             @RequestParam("id") Long productId,
                             Model model,
                             Principal principal){
        if (principal == null){
            return "redirect:/login";
        }else {
            ProductDto productDto = productService.getById(productId);
            String username = principal.getName();
            ShoppingCart shoppingCart = shoppingCartService.updateCart(productDto, quantity, username);
            model.addAttribute("shoppingCart", shoppingCart);
            return "redirect:/cart";
        }
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItemInCart(@RequestParam("id") Long productId,
                             Model model,
                             Principal principal){
        if (principal == null){
            return "redirect:/login";
        }else {
            ProductDto productDto = productService.getById(productId);
            String username = principal.getName();
            ShoppingCart shoppingCart = shoppingCartService.removeItemFromCart(productDto, username);
            model.addAttribute("shoppingCart", shoppingCart);
            return "redirect:/cart";
        }


    }
}
