package bg.an.englishacademy.service.impl;

import bg.an.englishacademy.model.entity.CategoryEntity;
import bg.an.englishacademy.model.service.CategoryServiceModel;
import bg.an.englishacademy.repository.CategoryRepository;
import bg.an.englishacademy.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean categoryNameExists(String name) {
        return this.categoryRepository.findByName(name).isPresent();
    }

    @Override
    public CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel) {
        this.categoryRepository.saveAndFlush(this.modelMapper
                        .map(categoryServiceModel, CategoryEntity.class));

        return categoryServiceModel;
    }

    @Override
    public List<String> findAllCategoryNames() {
        return categoryRepository.findAll()
                .stream().map(CategoryEntity::getName)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryServiceModel findCategoryByName(String categoryName) {
        return this.categoryRepository.findByName(categoryName)
                .map(c -> this.modelMapper.map(c, CategoryServiceModel.class))
                .orElseThrow(() -> new IllegalArgumentException("Invalid category!"));
    }

    @Override
    public List<CategoryServiceModel> findAllCategories() {
        return this.categoryRepository.findAll()
                .stream().map(c -> this.modelMapper.map(c, CategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryServiceModel findCategoryById(Long id) {
        CategoryEntity categoryEntity = this.categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with id " + id + " not found"));
        return this.modelMapper.map(categoryEntity, CategoryServiceModel.class);
    }

    @Override
    public CategoryServiceModel editCategory(Long id, CategoryServiceModel categoryServiceModel) {
        CategoryEntity categoryEntity = this.categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with id" + id + " not found"));

        categoryEntity.setName(categoryServiceModel.getName());

        if (!categoryServiceModel.getImageUrl().equals("")) {
            categoryEntity.setImageUrl(categoryServiceModel.getImageUrl());
        }

        return this.modelMapper.map(this.categoryRepository.saveAndFlush(categoryEntity), CategoryServiceModel.class);
    }

    @Override
    public void deleteCategory(Long id) {
        this.categoryRepository.deleteById(id);
    }
}
