package com.example.deliveryproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        final String[] JS_WHITELIST = {
                "/js/fill_posting_details.js",
                "/js/fill_tracking_table.js",
                "/js/fill_postings_by_user.js",
                "/js/show_map.js",
                "/js/regions.js",
                "/js/fill_offices.js",
                "/js/deleteEntity.js",
                "/css/tracking.css"
        };

        http.csrf().disable()

                .authorizeHttpRequests((authorize) ->
                                authorize.requestMatchers("/register/**").permitAll()
                                        .requestMatchers("/index").permitAll()
                                        .requestMatchers("/users/**").permitAll()//.hasRole("ADMIN")
                                        .requestMatchers("/testPostings**").permitAll()//.hasRole("ADMIN")
//                                .requestMatchers("/?**").hasRole("ADMIN")
                                        .requestMatchers("/create-posting/**").hasRole("ADMIN")
                                        .requestMatchers("/offices/**").permitAll()
                                        .requestMatchers("/postings/**").permitAll()
                                        .requestMatchers("/getWithMultipleRequestParams/**").permitAll()
                                        .requestMatchers("/test/**").permitAll()
                                        .requestMatchers("/getPostingDtoList").permitAll()
                                        .requestMatchers("/getUserDtoList").permitAll()
                                        .requestMatchers("/postings2").permitAll()

                                        .requestMatchers("/getUserDto/**").permitAll()
                                        .requestMatchers("/getPostingDto/**").permitAll()

                                        .requestMatchers("/getPostingEvents/**").permitAll()
                                        .requestMatchers("/getPostingsBySenderId/**").permitAll()
                                        .requestMatchers("/getPostingsByReceiverId/**").permitAll()
                                        .requestMatchers("/getOfficesByRegion/**").permitAll()
                                        .requestMatchers("/getRegionList").permitAll()


                                        .requestMatchers(JS_WHITELIST).permitAll()

//                ).anonymous(()
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/users")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                )
        ;
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
