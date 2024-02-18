package com.web.dacn.Services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.dacn.Exception.UserNotFoundException;
import com.web.dacn.Models.User;
import com.web.dacn.Repositories.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public List<User> listAll() {
		return userRepository.findAll();
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public User get(long id) {
		return userRepository.findById(id).orElse(null);
	}

	public void delete(long id) {
		userRepository.deleteById(id);
	}

	public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
		User user = userRepository.getUserByEmail(email);
		if (user != null) {
			user.settokenforgotpassword(token);
			user.setTimeexpired(null);
			userRepository.save(user);
		} else {
			throw new UserNotFoundException("khong ton tai user co email " + email);
		}
	}

	public User getUserByTokenforgotpassWord(String token) {
		return userRepository.getUserBytokenforgotpassword(token);
	}

	public void updatePassword(User user, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);
		user.setPassword(encodedPassword);

		user.settokenforgotpassword(null);
		userRepository.save(user);
	}

	public boolean verify(String verificationCode) {
		User user = userRepository.findByVerificationCode(verificationCode);

		if (user == null || user.isEnabled()) {
			return false;
		} else {
			user.setVerificationCode(null);
			user.setEnabled(true);
			userRepository.save(user);

			return true;
		}

	}
}
