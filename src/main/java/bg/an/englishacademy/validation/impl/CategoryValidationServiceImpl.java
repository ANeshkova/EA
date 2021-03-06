package bg.an.englishacademy.validation.impl;

import bg.an.englishacademy.model.binding.CategoryAddBindingModel;
import bg.an.englishacademy.model.binding.CategoryEditBindingModel;
import bg.an.englishacademy.model.service.CategoryServiceModel;
import bg.an.englishacademy.service.CategoryService;
import bg.an.englishacademy.validation.CategoryValidationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class CategoryValidationServiceImpl implements CategoryValidationService {

    private final CategoryService categoryService;

    public CategoryValidationServiceImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean categoryAddHasErrors(CategoryAddBindingModel categoryAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("categoryAddBindingModel", categoryAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.categoryAddBindingModel", bindingResult);
            return true;
        }

        if (categoryAddBindingModel.getImageUrl().isEmpty()) {
            redirectAttributes.addFlashAttribute("categoryAddBindingModel", categoryAddBindingModel);
            redirectAttributes.addFlashAttribute("youMustUploadImage", true);
            return true;
        }

        if (this.categoryService.categoryNameExists(categoryAddBindingModel.getName())) {
            redirectAttributes.addFlashAttribute("categoryAddBindingModel", categoryAddBindingModel);
            redirectAttributes.addFlashAttribute("categoryNameAlreadyExists", true);
            return true;
        }

        return false;
    }

    @Override
    public boolean categoryEditHasErrors(CategoryEditBindingModel categoryEditBindingModel, BindingResult bindingResult,
                                         RedirectAttributes redirectAttributes, Long id) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("categoryEditBindingModel", categoryEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.categoryEditBindingModel", bindingResult);
            return true;
        }

        CategoryServiceModel currentCategory = this.categoryService.findCategoryById(id);

        if (this.categoryService.categoryNameExists(categoryEditBindingModel.getName())
                && !categoryEditBindingModel.getName().equalsIgnoreCase(currentCategory.getName())) {
            redirectAttributes.addFlashAttribute("categoryEditBindingModel", categoryEditBindingModel);
            redirectAttributes.addFlashAttribute("categoryNameAlreadyExists", true);
            return true;
        }

        return false;
    }
}
