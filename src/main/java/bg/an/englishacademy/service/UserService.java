package bg.an.englishacademy.service;

import bg.an.englishacademy.model.service.UserServiceModel;

public interface UserService {

    void registerUser(UserServiceModel userServiceModel);

    boolean usernameExists(String username);

    boolean emailExists(String email);
}
