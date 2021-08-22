package projectdefence.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import projectdefence.models.entities.Blog;
import projectdefence.models.entities.User;
import projectdefence.models.serviceModels.AddBlogServiceModel;
import projectdefence.models.viewModels.BlogViewModel;
import projectdefence.repositories.BlogRepository;
import projectdefence.repositories.UserRepository;
import projectdefence.security.IsRoot;
import projectdefence.service.BlogService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    public BlogServiceImpl(BlogRepository blogRepository,
                           ModelMapper modelMapper, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void addBlog(AddBlogServiceModel blogServiceModel, UserDetails principal) {

        Blog blog = this.modelMapper.map(blogServiceModel, Blog.class);
        User user = this.userRepository.findByUsername(principal.getUsername());
        blog.setAuthor(user.getFirstName() + " " + user.getLastName()).setDate(LocalDate.now());
        this.blogRepository.save(blog);
    }

    @Override
    public List<BlogViewModel> findFirst120Blogs() {
        return this.blogRepository.findFirst120_OrderByDate()
                .stream()
                .map(b -> modelMapper.map(b, BlogViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        this.blogRepository.deleteById(id);
    }

    @Override
    public List<BlogViewModel> findAll() {
        return this.blogRepository.findAll()
                .stream()
                .map(b -> this.modelMapper.map(b, BlogViewModel.class))
                .collect(Collectors.toList());
    }
}
