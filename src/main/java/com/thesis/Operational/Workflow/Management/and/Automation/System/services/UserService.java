package com.thesis.Operational.Workflow.Management.and.Automation.System.services;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.User;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.UserRepository;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User, Long> {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        super(repository, "User");
        this.repository = repository;
    }

    public User findByEMail(String name) {

        return repository.findByEmail(name).orElse(null);
    }
}
