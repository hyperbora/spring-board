package io.github.hyperbora.spring.board.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.userDetailsService(userDetailsService);

        security.authorizeRequests().antMatchers("/", "/system/**").permitAll();
        security.authorizeRequests()
                .antMatchers("android-chrome-192x192.png", "apple-touch-icon.png", "favicon-32x32.png",
                        "android-chrome-512x512.png", "favicon-16x16.png", "favicon.ico", "site.webmanifest")
                .permitAll();
        security.authorizeRequests().antMatchers("/js/**", "/css/**").permitAll();
        security.authorizeRequests().antMatchers("/board/**").authenticated();
        security.authorizeRequests().antMatchers("/admin/**", "/h2-console/**").hasRole("ADMIN");

        security.csrf().disable();
        security.formLogin().loginPage("/system/login").defaultSuccessUrl("/board/getBoardList", true);
        security.exceptionHandling().accessDeniedPage("/system/accessDenied");
        security.logout().logoutUrl("/system/logout").invalidateHttpSession(true).logoutSuccessUrl("/");
        security.headers().frameOptions().sameOrigin();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
