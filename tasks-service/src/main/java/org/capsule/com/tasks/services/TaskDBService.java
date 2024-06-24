package org.capsule.com.tasks.services;


import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.capsule.com.tasks.models.Task;
import org.capsule.com.tasks.models.TaskManage;
import org.capsule.com.tasks.repositories.TasksManageRepository;
import org.capsule.com.tasks.repositories.TasksRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskDBService {

    private final TasksRepository tasksRepository;
    private final TasksManageRepository tasksManageRepository;

    public Task findById(long id) {
        return tasksRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("task with id " + id + " not found"));
    }

    @Transactional(readOnly = false)
    public void save(TaskManage manager) {
        tasksManageRepository.save(manager);
    }

    public List<TaskManage> findAllSessionsByUsername(String username) {
        return tasksManageRepository.findAllByUsername(username);
    }

    public List<Task> getThreeRandomTasks() {
        List<Task> tasks = tasksRepository.findAll();
        Collections.shuffle(tasks);
        return tasks.stream().limit(3).toList();
    }
}