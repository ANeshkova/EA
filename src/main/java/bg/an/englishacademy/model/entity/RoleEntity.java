package bg.an.englishacademy.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {

    private String role;

    public RoleEntity() {
    }

    public RoleEntity(String role) {
        this.role = role;
    }

    @Column(name = "role", unique = true)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
