package com.nagarro.nagp.controller;

import com.nagarro.nagp.model.Task;
import com.nagarro.nagp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/task")
@CrossOrigin
public class TaskController {
    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<Task>> getAllTask(){
        return ResponseEntity.ok(userService.getAllTask());
    }
    @PostMapping("/add")
    private ResponseEntity<Task> saveTask(@RequestBody Task task){
        userService.saveTask(task);
        return ResponseEntity.ok(task);
    }
    @PutMapping("/update")
    public ResponseEntity<Task> updateTask(@RequestParam String title, @RequestBody Task task){
        return ResponseEntity.ok(userService.updateTask(title,task));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTask(@RequestParam String title){
        return ResponseEntity.ok(userService.deleteByTitle(title));
    }

}
