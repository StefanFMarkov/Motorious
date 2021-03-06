package projectdefence.service;

import org.springframework.security.core.userdetails.UserDetails;
import projectdefence.models.serviceModels.AddBlogServiceModel;
import projectdefence.models.viewModels.BlogViewModel;

import java.util.List;

public interface BlogService {

    void addBlog(AddBlogServiceModel blogServiceModel, UserDetails principal);

    List<BlogViewModel> findFirst120Blogs();

    void deleteById(Long id);

    List<BlogViewModel> findAll();
}
