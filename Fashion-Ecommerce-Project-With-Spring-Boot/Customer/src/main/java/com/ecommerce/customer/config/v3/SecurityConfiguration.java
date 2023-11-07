//package com.ecommerce.customer.config.v3;
//
//import com.ecommerce.library.repository.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//@EnableAutoConfiguration
//@EnableWebSecurity
//@ComponentScan(basePackageClasses = {CustomerDetailsServiceImpl.class, CustomerRepository.class})
//public class SecurityConfiguration {
//
//    private final CustomerDetailsServiceImpl customerDetailsService;
//
//    public SecurityConfiguration(CustomerDetailsServiceImpl customerDetailsService) {
//        this.customerDetailsService = customerDetailsService;
//    }
//
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customerDetailsService).passwordEncoder(bCryptPasswordEncoder());
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/static/**");
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable();
//
//        //Pages not require login
//        http.authorizeHttpRequests().antMatchers(
//                "/static/**", "/css/**", "/js/**", "/img/**",
//                "/login", "/logout", "/shop/**", "/home", "/"
//        ).permitAll();
//
//        // Pages require login with roles: ROLE_USER, ROLE_ADMIN.
//        // If not login yet, redirect to /login
//        http.authorizeHttpRequests()
//                .antMatchers("/cart", "/add-to-cart/**")
//                .hasAnyRole("CUSTOMER");
//
//        // Pages require login with role: ROLE_ADMIN.
//        // If not login at admin role yet, redirect to /login
//        http.authorizeHttpRequests()
//                .antMatchers("/role/**")
//                .hasRole("ADMIN");
//
//        // When user login with ROLE_USER, but try to
//        // access pages require ROLE_ADMIN, redirect to /error-403
//        http.authorizeHttpRequests().and().exceptionHandling()
//                .accessDeniedPage("/access-denied");
//
//        // Configure login page (check login by spring security)
//        http.authorizeHttpRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/j_spring_security_check")
//                .loginPage("/login")
//                .defaultSuccessUrl("/home")
//                .failureUrl("/login?error=true")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout=true");
//
//        // Configure remember me (save token in database)
////        http.authorizeHttpRequests()
////                .and().rememberMe()
////                .tokenRepository(this.persistentTokenRepository())
////                .tokenValiditySeconds(24 * 60 * 60);//24 hours
//
//        return http.build();
//    }
//
//    // Token stored in memory (of web server)
////    public PersistentTokenRepository persistentTokenRepository() {
////        InMemoryTokenRepositoryImpl inMemoryTokenRepository = new InMemoryTokenRepositoryImpl();
////        return inMemoryTokenRepository;
////    }
//}
