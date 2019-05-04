package blog.model.repository;

import blog.model.entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
    @Query(value = "SELECT * FROM posts p where p.autor_id = ?1")
    Iterable<Post> findByAutor(Long autorId);
}
