//package com.example.drugsystem.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    /**
//     * PasswordEncoder:加密编码，这里使用 NoOpPasswordEncoder 明文密码，如果需要加密，用 BCryptPasswordEncoder
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        //明文加密
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorizeHttpRequests->
//                        authorizeHttpRequests
//                                .requestMatchers("/login").permitAll()
////                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
//                                .anyRequest().authenticated()
//        );
//        http.formLogin(formLogin->
//                formLogin
//                        .loginPage("/login.html")
//                        .loginProcessingUrl("/login")
//                        .defaultSuccessUrl("http://localhost:8081/#/",true)
//                        .permitAll()
//        );
//        // 注意 6.2 版本里这里要使用 csrf.disable() 而不是 withDefault() 方法，网上很多使用 withDefault()方法的，个人实践不成功
//        http.csrf(csrf->csrf.disable());
//        return http.build();
//    }
//
//}
