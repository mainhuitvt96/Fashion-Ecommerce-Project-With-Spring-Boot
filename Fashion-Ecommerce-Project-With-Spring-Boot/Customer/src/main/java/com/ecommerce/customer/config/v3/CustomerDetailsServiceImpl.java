//package com.ecommerce.customer.config.v3;
//
//import com.ecommerce.library.model.Customer;
//import com.ecommerce.library.repository.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class CustomerDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Customer customer = customerRepository.findByUsername(username);
//
//        if (customer == null) {
//            throw new UsernameNotFoundException("User " + username + "was not found in database!");
//        }
//
//        List<String> roles = customerRepository.findRolesByUsername(username);
//
////        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
////        for (Role role : customer.getRoles()){
////            authorities.add(new SimpleGrantedAuthority(role.getName()));
////
////        }
//
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        for (String role: roles) {
//            GrantedAuthority authority = new SimpleGrantedAuthority(role);
//            grantedAuthorities.add(authority);
//        }
//
//        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
//                customer.getUsername(),
//                customer.getPassword(),
//                grantedAuthorities);
//
//        return userDetails;
//    }
//}
