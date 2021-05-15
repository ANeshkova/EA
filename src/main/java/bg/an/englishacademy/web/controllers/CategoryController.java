package bg.an.englishacademy.web.controllers;

import bg.an.englishacademy.model.binding.CategoryAddBindingModel;
import bg.an.englishacademy.model.service.CategoryServiceModel;
import bg.an.englishacademy.service.CategoryService;
import bg.an.englishacademy.service.CloudinaryService;
import bg.an.englishacademy.validation.CategoryValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    private final CategoryService categoryService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final CategoryValidationService categoryValidationService;

    public CategoryController(CategoryService categoryService, CloudinaryService cloudinaryService,
                              ModelMapper modelMapper, CategoryValidationService categoryValidationService) {
        this.categoryService = categoryService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
        this.categoryValidationService = categoryValidationService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String add(Model model) {

        if (!model.containsAttribute("categoryAddBindingModel")) {
            model.addAttribute("categoryAddBindingModel", new CategoryAddBindingModel());
        }
        return "categories/category-add";
    }
}
