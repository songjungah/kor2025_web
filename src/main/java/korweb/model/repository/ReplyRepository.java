package korweb.model.repository;

import korweb.model.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Integer> {
}
