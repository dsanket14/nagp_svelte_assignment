package com.nagarro.nagp.service;

import com.nagarro.nagp.dto.UserProfile;
import com.nagarro.nagp.exception.GenericException;
import com.nagarro.nagp.model.Task;
import com.nagarro.nagp.model.User;
import com.nagarro.nagp.repo.TaskRepo;
import com.nagarro.nagp.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    TaskRepo taskRepo;


    @PostConstruct
    public void init() {
        User user = new User(1L, "sanket", "sanket123", "7798892075", "dsanket188@gmail.com", LocalDateTime.now());
        userRepo.save(user);
        log.info("User Initialized for JWT Token Creation");
        Task task =new Task(1L,"Read","Personal","Read Book","03/06/2023","Pending");
        taskRepo.save(task);
        log.info("Task Initialized for JWT Token Creation");
    }

    public User registerUser(User user) {
        user.setCreationTime(LocalDateTime.now());
        Optional<User> existingUser = userRepo.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new GenericException("400", "Username already exists");
        }
        log.info("{} : user Registered Successfully ", user.getUsername());
        return userRepo.save(user);
    }

    public User getUser(String userName) {
        Optional<User> existingUser = userRepo.findByUsername(userName);
        if (existingUser.isEmpty()) {
            throw new GenericException("400", "Username Not Found");
        }
        return existingUser.get();
    }


    public Task saveTask(Task task) {
        Optional<Task> existingTask = taskRepo.findByTitle(task.getTitle());
        if (existingTask.isPresent()) {
            throw new GenericException("400", "Task already exists");
        }
        log.info("{} : task saved Successfully ", task.getTitle());
        return taskRepo.save(task);
    }

    public List<Task> getAllTask() {
        return taskRepo.findAll();
    }

    @Transactional
    public String deleteByTitle(String title) {
        Optional<Task> existingTask = taskRepo.findByTitle(title);
        if (existingTask.isEmpty()) {
            throw new GenericException("400", "Task Not Found");
        }
        taskRepo.deleteAllByTitle(title);
        return "Task deleted";
    }

    public Task updateTask(String title, Task task) {
        Optional<Task> existingTask = taskRepo.findByTitle(title);
        if (existingTask.isEmpty()) {
            throw new GenericException("400", "Task Not Found");
        }
        existingTask.get().setTitle(null==task.getTitle() ? existingTask.get().getTitle():task.getTitle());
        existingTask.get().setCategory(null==task.getCategory()?existingTask.get().getCategory():task.getCategory());
        existingTask.get().setDate(null==task.getDate()?existingTask.get().getDate():task.getDate());
        existingTask.get().setDescription(null==task.getDescription()?existingTask.get().getDescription():task.getDescription());
        existingTask.get().setStatus(null==task.getStatus()?existingTask.get().getStatus():task.getStatus());
        return existingTask.get();
    }

    public UserProfile getUserProfile() {
        Optional<User> user = userRepo.findFirstByOrderByCreationTimeDesc();
        if (user.isEmpty()) {
            throw new GenericException("400", "User Not Found");
        }
        UserProfile userProfile=new UserProfile();
        userProfile.setUserName(user.get().getUsername());
        userProfile.setPhone(user.get().getPhone());
        userProfile.setMailId(user.get().getMailId());
        return userProfile;
    }
}
