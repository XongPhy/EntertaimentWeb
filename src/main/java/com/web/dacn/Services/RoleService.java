package com.web.dacn.Services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.dacn.Models.Role;
import com.web.dacn.Repositories.RoleRepository;

@Service
@Transactional
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;

	public List<Role> listAll() {
		return roleRepository.findAll();
	}

	public void save(Role user) {
		roleRepository.save(user);
	}

	public Role get(long id) {
		return roleRepository.findById(id).orElse(null);
	}

	public Role getbyName(String name) {
		return roleRepository.getRoleByName(name);
	}

	public void delete(long id) {
		roleRepository.deleteById(id);
	}
}
