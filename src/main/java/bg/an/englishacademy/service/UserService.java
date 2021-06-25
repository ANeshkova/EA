package bg.an.englishacademy.service;

import bg.an.englishacademy.model.service.UserServiceModel;
import bg.an.englishacademy.model.service.WordServiceModel;

import java.util.List;

public interface UserService {

    void registerUser(UserServiceModel userServiceModel);

    boolean usernameExists(String username);

    boolean emailExists(String email);

    UserServiceModel findUserByUsername(String username);

    void editUserProfile(UserServiceModel userServiceModel, String oldPassword, Long id);

    List<UserServiceModel> findAllUsers();

    void setUserRole(Long id, String role);

    UserServiceModel addWordToUser(WordServiceModel wordServiceModel, UserServiceModel userServiceModel);
}
