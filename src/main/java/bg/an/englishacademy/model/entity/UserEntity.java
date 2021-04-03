package bg.an.englishacademy.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    private String username;
    private String password;
    private String email;
    private Set<RoleEntity> roles;
    private Set<WordEntity> words;

    public UserEntity() {
    }

    @Column(name = "username", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToMany(targetEntity = RoleEntity.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    @ManyToMany(targetEntity = WordEntity.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_words",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "word_id", referencedColumnName = "id")
    )
    public Set<WordEntity> getWords() {
        return words;
    }

    public void setWords(Set<WordEntity> words) {
        this.words = words;
    }
}
