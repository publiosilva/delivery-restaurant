package br.com.ufc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.ufc.security.UserDetailsServiceImplementation;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImplementation userDetailsServiceImplementation;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()

				.antMatchers("/").permitAll()
				.antMatchers("/user/sign-up").permitAll()
				.antMatchers("/user/save").permitAll()

				.antMatchers("/dish/list").hasRole("ADMIN")
				.antMatchers("/dish/create").hasRole("ADMIN")
				.antMatchers("/dish/update").hasRole("ADMIN")
				.antMatchers("/dish/save").hasRole("ADMIN")
				.antMatchers("/dish/delete").hasRole("ADMIN")

				.antMatchers("/purchase-order/list").hasRole("CLIENT")
				.antMatchers("/purchase-order/save").hasRole("CLIENT")
				.antMatchers("/shopping-bag").hasRole("CLIENT")
				.antMatchers("/shopping-bag/add-dish").hasRole("CLIENT")
				.antMatchers("/shopping-bag/remove-dish").hasRole("CLIENT")

				.anyRequest().authenticated()

				.and().formLogin().loginPage("/user/sign-in").permitAll().defaultSuccessUrl("/")

				.and().logout().logoutSuccessUrl("/user/sign-in?logout").permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImplementation).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/webfonts/**", "/images/**");
	}

}
