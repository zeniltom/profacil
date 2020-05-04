package com.profacil.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JsfLoginUrlAuthenticationEntryPoint jsfLoginEntry = new JsfLoginUrlAuthenticationEntryPoint();
		jsfLoginEntry.setLoginFormUrl("/Login.xhtml");
		jsfLoginEntry.setRedirectStrategy(new JsfRedirectStrategy());

		JsfAccessDeniedHandler jsfDeniedEntry = new JsfAccessDeniedHandler();
		jsfDeniedEntry.setLoginPath("/AcessoNegado.xhtml");
		jsfDeniedEntry.setContextRelative(true);

		http.csrf().disable().headers().frameOptions().sameOrigin();

		http.authorizeRequests().antMatchers("/Login.xhtml", "/Erro.xhtml", "/javax.faces.resource/**").permitAll()
				
			.antMatchers("/Home.xhtml", "/Sobre.xhtml", "/AcessoNegado.xhtml").authenticated()
			.antMatchers("/curso/**").hasAnyRole("ADMINISTRADORES", "PROFESSORES")
			.antMatchers("/disciplina/**").hasAnyRole("ADMINISTRADORES", "PROFESSORES")
			.antMatchers("/prova/**").hasAnyRole("ADMINISTRADORES", "PROFESSORES")
			.antMatchers("/questao/**").hasAnyRole("ADMINISTRADORES", "PROFESSORES")
			.antMatchers("/relatorios/**").hasAnyRole("ADMINISTRADORES")
			.antMatchers("/professor/**").hasAnyRole("ADMINISTRADORES")
			.antMatchers("/professor/Professor.xhtml").authenticated()
			.antMatchers("/usuario/**").hasAnyRole("ADMINISTRADORES");
			
		http.formLogin().loginPage("/Login.xhtml").failureUrl("/Login.xhtml?invalid=true");
		
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

		http.exceptionHandling().accessDeniedPage("/AcessoNegado.xhtml").authenticationEntryPoint(jsfLoginEntry)
				.accessDeniedHandler(jsfDeniedEntry);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	} 
}