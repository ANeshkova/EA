package bg.an.englishacademy.service;

import bg.an.englishacademy.model.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {

    boolean categoryNameExists(String name);

    CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel);

    List<String> findAllCategoryNames();

    CategoryServiceModel findCategoryByName(String categoryName);

    List<CategoryServiceModel> findAllCategories();

    CategoryServiceModel findCategoryById(Long id);

    CategoryServiceModel editCategory(Long id, CategoryServiceModel categoryServiceModel);

    void deleteCategory(Long id);
}
