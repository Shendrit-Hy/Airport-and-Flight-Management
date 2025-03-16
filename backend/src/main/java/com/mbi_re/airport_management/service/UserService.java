package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.model.User;
import com.mbi_re.airport_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        String tenantId = TenantContext.getTenantId();
        return userRepository.findByTenantId(tenantId);
    }

    public User createUser(User user) {
        user.setTenantId(TenantContext.getTenantId());
        return userRepository.save(user);
    }
}


