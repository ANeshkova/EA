package bg.an.englishacademy.service.impl;

import bg.an.englishacademy.model.entity.RoleEntity;
import bg.an.englishacademy.repository.RoleRepository;
import bg.an.englishacademy.service.RoleService;

public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void seedRolesInDB() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.saveAndFlush(new RoleEntity("USER"));
            this.roleRepository.saveAndFlush(new RoleEntity("ADMIN"));
            this.roleRepository.saveAndFlush(new RoleEntity("ROOT"));
        }
    }
}
