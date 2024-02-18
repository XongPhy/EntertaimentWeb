package com.web.dacn.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.web.dacn.Services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	String[] pathArrayPermitAll = new String[] { "/", "/bangtins", "/webjars/**",
			"/forgot_password", "/reset_password", "/signup", "/verify/**", "/process_register",
			"/bangtins/detail/**", "/bangtins/page/**", "/mobiles", "/pcs", "/esports" ,"/mobiles/page/**",
			"/esports/page/**","/pcs/page/**","/bangtins/edit/**","/photos/**"};
	String[] pathArrayNew = new String[] { "/bangtins/new" };
	String[] pathArrayDelete = new String[] { "/bangtins/edit/**" };
	String[] pathArrayUpdate = new String[] { "/bangtins/delete/**" };
	String[] pathArrayRole = new String[] { "/roles" };
	String[] pathArrayUser = new String[] { "/users" };
	String[] pathArrayTheLoai = new String[] { "/theloais" };

	@Bean
	protected BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	protected UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	protected DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		System.out.println("filter");
		http.authorizeHttpRequests(requests -> requests.antMatchers(
				pathArrayPermitAll).permitAll()
				.antMatchers(pathArrayNew).hasAnyAuthority("ADMIN", "CREATER")
				.antMatchers(pathArrayUpdate).hasAnyAuthority("ADMIN", "EDITOR", "CREATER")
				.antMatchers(pathArrayRole).hasAnyAuthority("ADMIN")
				.antMatchers(pathArrayUser).hasAnyAuthority("ADMIN")
				.antMatchers(pathArrayTheLoai).hasAnyAuthority("ADMIN", "EDITOR", "CREATER")
				.antMatchers(pathArrayDelete).hasAuthority("ADMIN")
				.anyRequest().authenticated())
				.formLogin(login -> login.loginPage("/login").defaultSuccessUrl("/").permitAll())
				.logout(logout -> logout.permitAll())
				.exceptionHandling(handling -> handling.accessDeniedPage("/403"))
				.csrf().disable();
		return http.build();
	}

}
