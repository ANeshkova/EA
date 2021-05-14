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
}
