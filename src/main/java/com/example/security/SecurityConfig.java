package com.example.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    private static final String[] SECURED_URLs = {"/productos/**"};

    private static final String[] UN_SECURED_URLs = {"/users/**"};
    
   @Bean
   PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder(); 
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeHttpRequests()
                .requestMatchers(UN_SECURED_URLs).permitAll().and()
                .authorizeHttpRequests().requestMatchers(SECURED_URLs)
                .hasAuthority("ADMIN").anyRequest()
                .authenticated().and().httpBasic(withDefaults());

        return http.build();
    }

    public static void main(String[] args) {
        
        System.out.println(new SecurityConfig().passwordEncoder().encode("Temp2023$$"));

    //En este caso, había que poner en frente de PasswordEncoder static
    // }

    //Existe otro caso, static está en ambos planos (instancias y clases)

    //Sin el static, hay que llamar a passwordEncoder a partir de SecurityCOnfig
    //hay que instanciarlo, por eso vive en el plano de las instancias. 
}
}
