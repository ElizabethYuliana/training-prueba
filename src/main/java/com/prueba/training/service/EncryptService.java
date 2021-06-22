package com.prueba.training.service;

public interface EncryptService {
	
	String encryptPassword(String password);
	
	boolean verifyPassword(String originalPassword, String hashPassword);

}
