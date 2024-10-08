package com.dogadopt.dog_adopt.config.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.COOKIES;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthUserService authUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                                   .loginPage("/dog-adopt/registration/custom-login")
                                   .defaultSuccessUrl("/dog-adopt/users/me", true)
                                   .failureUrl("/error-general?message=Login failed! Wrong username or password or account has not yet been activated!")
                                   .permitAll()
                          )

                .logout(logout -> {
                    logout.addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(COOKIES)));
                    logout.logoutUrl("/dog-adopt/users/logout");
                    logout.deleteCookies("JSESSIONID");
                    logout.logoutSuccessUrl("/login");
                })
                .authorizeHttpRequests(requests -> requests
                                               .requestMatchers("/dog-adopt/**").permitAll()

                                               .anyRequest().authenticated()
                                      )
                .httpBasic(withDefaults())
                .authenticationProvider(daoAuthenticationProvider())
                .sessionManagement(sessionManagement -> {
                                       sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                                       sessionManagement
                                               .maximumSessions(1)
                                               .maxSessionsPreventsLogin(false);
                                   }
                                  );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(authUserService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
