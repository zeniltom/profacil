package com.profacil.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CriptografiaBCrypt {

	public static String criptografar(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}

}
