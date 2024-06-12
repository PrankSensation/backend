package app.repositories;

import app.exeptions.AlreadyExistsException;
import jakarta.persistence.Entity;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EntityRepository<E> {

    List<E> findAll();

    E findByUuid(String uuid) throws ChangeSetPersister.NotFoundException;

    ResponseEntity<E> save(E entity) throws AlreadyExistsException;

    ResponseEntity<E> deleteByUuid(String uuid) throws ChangeSetPersister.NotFoundException;

}
