package blog.controller;

import blog.model.entity.Comment;
import blog.model.entity.Post;
import blog.model.entity.Users;
import blog.model.repository.CommentRepository;
import blog.model.repository.PostRepository;
import blog.model.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/posts")

public class PostController  {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    private Long getAuthenticatedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Long user =  userRepository.findByusername(username);
        return user;
    }

    @RequestMapping(value = {"", "/", "/index"}, method= RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("posts/index");

        Long id_user = getAuthenticatedUser();

        Iterable<Post> posts = postRepository.findByAutor(id_user);
        mv.addObject("titulo", "Lista de posts");
        mv.addObject("posts", posts);
        return mv;
    }

    @RequestMapping(value = {"/list"}, method= RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("posts/list");
        Iterable<Post> posts = postRepository.findAll();
        mv.addObject("titulo", "posts");
        mv.addObject("posts", posts);
        return mv;
    }

    @RequestMapping(value = "/read/{id}", method=RequestMethod.GET)
    public ModelAndView read(@PathVariable String id) {

        ModelAndView mv = new ModelAndView("posts/read");
        int postId = Integer.parseInt(id);

        Post post = postRepository.findOne((long) postId);
        Iterable<Comment> comments = commentRepository.findByPost((long) postId);

        mv.addObject("comments", comments);
        mv.addObject("titulo", "Detalhe do post");
        mv.addObject("post", post);


        return mv;
    }

    @RequestMapping(value = "/thumbsUp/{commentId}", method= RequestMethod.GET)
    public ModelAndView addthumbsUp(@PathVariable Long commentId){
        Comment comment;
        comment = commentRepository.findOne(commentId);
        comment.addThumbsUp();
        commentRepository.save(comment);
        ModelAndView mv = new ModelAndView("redirect:/app/posts/read/"+ comment.getPost().getId());
        return mv;
    }

    @RequestMapping(value = "/thumbsDown/{commentId}", method = RequestMethod.GET)
    public ModelAndView addThumbsDown(@PathVariable Long commentId){
        Comment comment;
        comment = commentRepository.findOne(commentId);
        comment.addThumbsDown();
        commentRepository.save(comment);
        ModelAndView mv = new ModelAndView("redirect:/app/posts/read/"+ comment.getPost().getId());
        return mv;
    }

    @RequestMapping(value = "/read/{id}", method=RequestMethod.POST)
    public ModelAndView read(String idPost,  Comment comment, BindingResult result, RedirectAttributes attributes) {
        if(!comment.getTexto().isEmpty()){
            int post_id = Integer.parseInt(idPost);
            Post post = postRepository.findOne((long) post_id);
            Comment c = new Comment(comment);
            c.setData(new Date(System.currentTimeMillis()));
            c.setThumbsUp(0L);
            c.setThumbsDown(0L);
            c.setPost(post);
            commentRepository.save(c);

        }
        ModelAndView mv = new ModelAndView("redirect:/app/posts/read/"+ idPost);
        return mv;
    }

    @RequestMapping(value = "/delete_comment/{id}", method = RequestMethod.DELETE)
    public ModelAndView delete_comment(@PathVariable Long id){
        Comment comment = commentRepository.findOne(id);
        commentRepository.delete(id);
        ModelAndView mv = new ModelAndView("redirect:/app/posts/read/"+ comment.getPost().getId());
        return mv;


    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(Post post) {
        ModelAndView mv = new ModelAndView("posts/create");
        mv.addObject("titulo", "Cadastrar um novo post");
        return mv;
    }

    @RequestMapping(value = "/create", method=RequestMethod.POST)
    public ModelAndView create(@Valid Post post, BindingResult result, RedirectAttributes attributes) {

        Long idUser = getAuthenticatedUser();

        post.setAutor(userRepository.findOne(idUser));

        if (result.hasErrors()) {
            return create(post);
        }

        postRepository.save(post);

        ModelAndView mv = new ModelAndView("redirect:/app/posts");
        return mv;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable String id) {
        ModelAndView mv = new ModelAndView("posts/update");
        int postId = Integer.parseInt(id);
        Post post = postRepository.findOne((long) postId);
        mv.addObject("titulo", "Alterar dados do post");
        mv.addObject("post", post);
        return mv;
    }

    @RequestMapping(value = "/update/{id}", method=RequestMethod.POST)
    public ModelAndView update(@Valid Post post, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return update(post.getId().toString());
        }

        postRepository. save(post);

        ModelAndView mv = new ModelAndView("redirect:/app/posts");
        return mv;
    }

    @RequestMapping(value = "/delete/{id}", method=RequestMethod.GET)
    public ModelAndView delete(@PathVariable String id) {
        ModelAndView mv = new ModelAndView("posts/delete");
        int postId = Integer.parseInt(id);
        Post post = postRepository.findOne((long) postId);
        mv.addObject("titulo", "Exluir post");
        mv.addObject("post", post);
        return mv;
    }

    @RequestMapping(value = "/delete_confirmed/{id}", method=RequestMethod.GET)
    public ModelAndView delete_confirmed(@PathVariable String id){
        int postId = Integer.parseInt(id);
        postRepository.delete((long) postId);
        ModelAndView mv = new ModelAndView("redirect:/app/posts");
        return mv;
    }

}
