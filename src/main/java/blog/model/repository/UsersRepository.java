package blog.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import blog.model.entity.*;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long>  {

    @Query(value = "SELECT u.id FROM dbo.Users u WHERE u.username = ?1 ", nativeQuery = true)
    Long findByusername(String username);
}
