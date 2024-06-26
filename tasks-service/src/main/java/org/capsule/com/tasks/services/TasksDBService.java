package org.capsule.com.tasks.services;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.capsule.com.tasks.models.Task;
import org.capsule.com.tasks.models.Session;
import org.capsule.com.tasks.repositories.SessionsRepository;
import org.capsule.com.tasks.repositories.TasksRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TasksDBService {

    private final TasksRepository tasksRepository;
    private final SessionsRepository sessionsRepository;

    public Task findById(long id) {
        return tasksRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("task with id " + id + " not found"));
    }

    @Transactional(readOnly = false)
    public void save(Session session) {
        sessionsRepository.save(session);
    }

    public List<Session> findAllSessionsByUsername(String username) {
        return sessionsRepository.findAllByUsername(username);
    }

    public List<Task> getThreeRandomTasks() {
        List<Task> tasks = tasksRepository.findAll();
        Collections.shuffle(tasks);
        return tasks.stream().limit(3).toList();
    }

    public Session getSessionByUsernameAndTaskId(String username, long id) {
        return sessionsRepository.findByUsernameAndTaskId(username, id);
    }
}