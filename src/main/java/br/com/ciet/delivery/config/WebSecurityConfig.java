package br.com.ciet.delivery.config;

import br.com.ciet.delivery.util.RestApiURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  public void configurGlobal(AuthenticationManagerBuilder auth) throws Exception {

    auth.inMemoryAuthentication().withUser(Credencias.USUARIO).password(Credencias.SENHA).roles("USER");
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .anyRequest().permitAll();

    http.headers().frameOptions().sameOrigin();

  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
            .antMatchers(HttpMethod.GET, RestApiURI.ENTREGA_PACOTE)
            .antMatchers(HttpMethod.POST, RestApiURI.ENTREGA_PACOTE)
            .antMatchers(
                    "/swagger-resources/**",
                    "/configuration/ui",
                    "/swagger-resources",
                    "/configuration/security",
                    "/swagger-ui.html");
  }

}
