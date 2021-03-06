package projectdefence.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectdefence.models.binding.AddBlogBindingModel;
import projectdefence.models.serviceModels.AddBlogServiceModel;
import projectdefence.security.rolesAuth.IsKinesitherapist;
import projectdefence.service.BlogService;

import javax.validation.Valid;


@Controller
public class BlogController implements BlogNamespace {
    private final ModelMapper modelMapper;
    private final BlogService blogService;

    public BlogController(ModelMapper modelMapper, BlogService blogService) {
        this.modelMapper = modelMapper;
        this.blogService = blogService;
    }

    @GetMapping("/add-blog")
    @IsKinesitherapist
    public String addBlog(Model model) {
        if (!model.containsAttribute("addBlogBindingModel")) {
            model.addAttribute("addBlogBindingModel", new AddBlogBindingModel());
        }
        return "add-blog";
    }


    @GetMapping("/view-blog-delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteBlog() {
        return "blog_delete";
    }


    @PostMapping("/add-blog")
    @IsKinesitherapist
    public String addBlogPost(@Valid @ModelAttribute AddBlogBindingModel addBlogBindingModel,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal UserDetails principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addBlogBindingModel", addBlogBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addBlogBindingModel",
                    bindingResult);
            return "redirect:add-blog";
        }

        this.blogService.addBlog(this.modelMapper.map(addBlogBindingModel, AddBlogServiceModel.class), principal);

        return "redirect:/info/blog";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable Long id) {

        blogService.deleteById(id);
        return "redirect:/home";
    }
}
