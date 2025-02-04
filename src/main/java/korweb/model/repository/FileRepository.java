package korweb.model.repository;

import korweb.model.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
}
