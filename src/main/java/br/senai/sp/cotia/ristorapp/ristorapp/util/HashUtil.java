package br.senai.sp.cotia.ristorapp.ristorapp.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class HashUtil {
	public static String hash256(String original) {
		String salt = "ristorapp";
	
		original = salt+original;
		String sha256hex = Hashing.sha256().hashString(original, StandardCharsets.UTF_8).toString();
		return sha256hex;
	}
}
