package az.ingress.dao.repository;

import az.ingress.dao.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<StudentEntity, Long> {
    Page<StudentEntity> findAll(Pageable pageable);
}