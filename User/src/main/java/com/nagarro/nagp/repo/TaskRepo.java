package com.nagarro.nagp.repo;

import com.nagarro.nagp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task,Long> {
    Optional<Task> findByTitle(String title);
    void deleteAllByTitle(String title);
}
