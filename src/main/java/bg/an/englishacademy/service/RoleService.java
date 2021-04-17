package bg.an.englishacademy.service;

import bg.an.englishacademy.model.service.RoleServiceModel;

import java.util.Set;

public interface RoleService {

    void seedRolesInDB();

    Set<RoleServiceModel> findAllRoles();

}
