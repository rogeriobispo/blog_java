package blog.model.repository;

import blog.model.entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query(value = "SELECT * FROM comment c where c.post_id = ?1 ", nativeQuery = true)
    Iterable<Comment> findByPost(Long postId);
}
