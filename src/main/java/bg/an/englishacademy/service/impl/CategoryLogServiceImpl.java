package bg.an.englishacademy.service.impl;

import bg.an.englishacademy.model.entity.CategoryEntity;
import bg.an.englishacademy.model.entity.CategoryLogEntity;
import bg.an.englishacademy.model.entity.UserEntity;
import bg.an.englishacademy.model.service.CategoryLogServiceModel;
import bg.an.englishacademy.repository.CategoryLogRepository;
import bg.an.englishacademy.service.CategoryService;
import bg.an.englishacademy.service.CategoryLogService;
import bg.an.englishacademy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryLogServiceImpl implements CategoryLogService {

    private final CategoryLogRepository categoryLogRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public CategoryLogServiceImpl(CategoryLogRepository categoryLogRepository, CategoryService categoryService, UserService userService, ModelMapper modelMapper) {
        this.categoryLogRepository = categoryLogRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createLog(String action, String categoryName) {

        CategoryEntity categoryEntity = this.modelMapper
                .map(this.categoryService.findCategoryByName(categoryName), CategoryEntity.class);

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String username = authentication.getName();

        UserEntity userEntity = this.modelMapper
                .map(this.userService.findUserByUsername(username), UserEntity.class);

        CategoryLogEntity categoryLogEntity = new CategoryLogEntity()
                .setUserEntity(userEntity)
                .setCategoryEntity(categoryEntity)
                .setAction(action)
                .setDateTime(LocalDateTime.now());

        categoryLogRepository.save(categoryLogEntity);
    }
}
