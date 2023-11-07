package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.ldap.PagedResultsControl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class AuthController {

    private final CustomerService customerService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("/login");
        return modelAndView;
    }

    @GetMapping("/register")
    public  String register(Model model){
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }

    @PostMapping("/do-resgister")
    public String processRegister(@Valid @ModelAttribute("customerDto")CustomerDto customerDto,
                                  BindingResult result,
                                  Model model){
        try{
            if (result.hasErrors()){
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
            String username = customerDto.getUsername().trim();
            Customer customer = customerService.findByUsername(username);
            if (customer != null) {
                model.addAttribute("username", "Username have been registered!");
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
            if (customerDto.getPassword().equals(customerDto.getRepeatPassword())) {
                customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
                customerService.save(customerDto);
                model.addAttribute("success", "Register successfully!");
            } else {
                model.addAttribute("error", "Password is incorrect");
                model.addAttribute("customerDto", customerDto);
                return "register";
            }

        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", "Server is error, please try again!");
        }
        return "register";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Lấy thông tin đăng nhập của người dùng
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Nếu người dùng đã đăng nhập, thực hiện việc đăng xuất
        if (authentication != null) {
            // Xóa thông tin đăng nhập khỏi SecurityContextHolder
            SecurityContextHolder.clearContext();

            // Thực hiện việc đăng xuất khỏi session
            request.getSession().invalidate();
        }

        // Redirect đến trang đăng nhập hoặc bất kỳ trang nào bạn mong muốn
        return "redirect:/login?logout";
    }



}
