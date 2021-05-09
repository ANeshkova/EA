package bg.an.englishacademy.service;

import bg.an.englishacademy.model.service.CategoryServiceModel;

public interface CategoryService {

    boolean categoryNameExists(String name);

    CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel);

}
