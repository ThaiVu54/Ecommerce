package com.example.thuongmai.Service.role;

import com.example.thuongmai.enums.EnumRoles;
import com.example.thuongmai.model.role.Role;
import com.example.thuongmai.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RoleService implements IRoleService{
    @Autowired
    private IRoleRepository roleRepository;
    @Override
    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role findByName(EnumRoles name) {
        return roleRepository.findByName(name);
    }
}
