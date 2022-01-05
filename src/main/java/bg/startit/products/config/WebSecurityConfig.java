package bg.startit.products.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 *  Allows customization to both WebSecurity and HttpSecurity
 */
@EnableWebSecurity
/*
 * The prePostEnabled property enables Spring Security pre-post annotations.
 * The jsr250Enabled property allows us to use the @RoleAllowed annotation.
 * The @RoleAllowed annotation is the JSR-250’s equivalent annotation of the @Secured annotation.
 * The securedEnabled property determines if the @Secured annotation should be enabled.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     *  The default configuration makes sure any request to the application is authenticated with form based login or
     *  HTTP basic authentication.
     * @param http
     * throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/console/**").permitAll()
                .antMatchers("/api/v1/**").authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable();
    }
}
