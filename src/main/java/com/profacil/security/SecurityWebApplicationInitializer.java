package com.profacil.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer  {
	
	public SecurityWebApplicationInitializer() {
		super(SecurityConfig.class);
	}
}
