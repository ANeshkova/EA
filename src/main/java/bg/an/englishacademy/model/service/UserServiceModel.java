package bg.an.englishacademy.model.service;

import java.util.Set;

public class UserServiceModel extends BaseServiceModel {

    private String username;
    private String password;
    private String email;
    private Set<RoleServiceModel> roles;
    private Set<WordServiceModel> words;

    public UserServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleServiceModel> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleServiceModel> roles) {
        this.roles = roles;
    }

    public Set<WordServiceModel> getWords() {
        return words;
    }

    public void setWords(Set<WordServiceModel> words) {
        this.words = words;
    }
}
