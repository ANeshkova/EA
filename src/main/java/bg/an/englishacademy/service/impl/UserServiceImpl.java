package bg.an.englishacademy.service.impl;

import bg.an.englishacademy.model.entity.UserEntity;
import bg.an.englishacademy.model.service.UserServiceModel;
import bg.an.englishacademy.repository.UserRepository;
import bg.an.englishacademy.service.RoleService;
import bg.an.englishacademy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final MyUserDetailsServiceImpl userDetailsService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService,
                           MyUserDetailsServiceImpl userDetailsService, ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userDetailsService = userDetailsService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {

        this.roleService.seedRolesInDB();

        if (this.userRepository.count() == 0) {
            userServiceModel.setRoles(this.roleService.findAllRoles());
        } else {
            userServiceModel.setRoles(new LinkedHashSet<>());
            userServiceModel.getRoles().add(this.roleService.findByRole("USER"));
        }

        UserEntity userEntity = this.modelMapper.map(userServiceModel, UserEntity.class);
        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setWords(new LinkedHashSet<>());

        this.userRepository.save(userEntity);
    }

    @Override
    public boolean usernameExists(String username) {
        return this.userRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean emailExists(String email) {
        return this.userRepository.findByEmail(email).isPresent();
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        return this.userRepository.findByUsername(username).map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Override
    public void editUserProfile(UserServiceModel userServiceModel, String oldPassword, Long id) {

        UserEntity userEntity = this.userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        if (!this.passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
            throw new IllegalArgumentException("Incorrect old password!");
        }

        userEntity.setUsername(userServiceModel.getUsername());
        userEntity.setEmail(userServiceModel.getEmail());
        userEntity.setPassword(this.passwordEncoder.encode(userServiceModel.getPassword()));

        this.userRepository.saveAndFlush(userEntity);

        UserDetails principal = userDetailsService.loadUserByUsername(userEntity.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                userEntity.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
