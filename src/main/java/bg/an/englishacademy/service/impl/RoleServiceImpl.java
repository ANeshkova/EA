package bg.an.englishacademy.service.impl;

import bg.an.englishacademy.model.entity.RoleEntity;
import bg.an.englishacademy.model.service.RoleServiceModel;
import bg.an.englishacademy.repository.RoleRepository;
import bg.an.englishacademy.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedRolesInDB() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.saveAndFlush(new RoleEntity("USER"));
            this.roleRepository.saveAndFlush(new RoleEntity("ADMIN"));
            this.roleRepository.saveAndFlush(new RoleEntity("ROOT"));
        }
    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return this.roleRepository
                .findAll()
                .stream()
                .map(role -> this.modelMapper.map(role, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }
}
