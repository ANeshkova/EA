package bg.an.englishacademy.web.controllers;

import bg.an.englishacademy.model.binding.CategoryAddBindingModel;
import bg.an.englishacademy.model.binding.CategoryEditBindingModel;
import bg.an.englishacademy.model.service.CategoryServiceModel;
import bg.an.englishacademy.model.view.CategoryViewModel;
import bg.an.englishacademy.service.CategoryService;
import bg.an.englishacademy.service.CloudinaryService;
import bg.an.englishacademy.validation.CategoryValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addConfirm(@Valid CategoryAddBindingModel categoryAddBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

        if (this.categoryValidationService.categoryAddHasErrors(categoryAddBindingModel, bindingResult, redirectAttributes)) {
            return redirect("/categories/add");
        }

        CategoryServiceModel categoryServiceModel = this.modelMapper.map(categoryAddBindingModel, CategoryServiceModel.class);
        categoryServiceModel.setImageUrl(this.cloudinaryService.uploadImage(categoryAddBindingModel.getImageUrl()));

        this.categoryService.addCategory(categoryServiceModel);
        redirectAttributes.addFlashAttribute("categoryAddedSuccessfully", true);

        return redirect("/categories/all/admin-table");
    }

    @GetMapping("/all/admin-table")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allCategories(Model model) {

        List<CategoryViewModel> categories = this.categoryService
                .findAllCategories()
                .stream()
                .map(p -> this.modelMapper.map(p, CategoryViewModel.class))
                .collect(Collectors.toList());

        model.addAttribute("categories", categories);

        return "categories/categories-all-admin-table";
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public String all(Model model) {

        List<CategoryViewModel> categories =
                this.categoryService.findAllCategories()
                        .stream()
                        .map(c -> this.modelMapper.map(c, CategoryViewModel.class))
                        .collect(Collectors.toList());

        model.addAttribute("categories", categories);
        return "categories/categories-all";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editCategory(@PathVariable Long id, Model model) {

        if (!model.containsAttribute("categoryEditBindingModel")) {
            CategoryEditBindingModel categoryEditBindingModel = new CategoryEditBindingModel();
            CategoryServiceModel categoryServiceModel = this.categoryService.findCategoryById(id);

            categoryEditBindingModel.setName(categoryServiceModel.getName());
            model.addAttribute("categoryEditBindingModel", categoryEditBindingModel);
        }

        model.addAttribute("id", id);
        return "categories/category-edit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editCategoryConfirm(@Valid CategoryEditBindingModel categoryEditBindingModel, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes, @PathVariable Long id) throws IOException {

        if (this.categoryValidationService.categoryEditHasErrors(categoryEditBindingModel, bindingResult, redirectAttributes, id)) {
            return super.redirect("/categories/edit/" + id);
        }

        CategoryServiceModel categoryServiceModel = this.modelMapper.map(categoryEditBindingModel, CategoryServiceModel.class);

        if (categoryEditBindingModel.getImageUrl().isEmpty()) {
            categoryServiceModel.setImageUrl("");
        } else {
            categoryServiceModel.setImageUrl(this.cloudinaryService.uploadImage(categoryEditBindingModel.getImageUrl()));
        }

        this.categoryService.editCategory(id, categoryServiceModel);
        redirectAttributes.addFlashAttribute("categoryEditedSuccessfully", true);

        return super.redirect("/categories/all/admin-table");
    }
}
