package org.capsule.com.tasks.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.capsule.com.tasks.dtos.errors.CustomError;
import org.capsule.com.tasks.dtos.requests.TaskIdReqst;
import org.capsule.com.tasks.dtos.responses.ListOfTasksResp;
import org.capsule.com.tasks.models.Session;
import org.capsule.com.tasks.models.Task;
import org.capsule.com.tasks.utils.exceptions.FieldsNotValidException;
import org.capsule.com.tasks.utils.mappers.TasksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class TasksService implements ITasksService<TaskIdReqst, ListOfTasksResp> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksService.class);

    private final DecodeJwtService decodeJwtService;
    private final TasksDBService tasksDBService;
    private final TasksMapper tasksMapper;

    @Override
    public HttpStatus startTask(String token, TaskIdReqst request, BindingResult bindingResult) {
        return processTaskRequest(token, request, bindingResult, Session.Status.IN_PROGRESS,
            HttpStatus.CREATED);
    }

    @Override
    public HttpStatus completeTask(String token, TaskIdReqst request, BindingResult bindingResult) {
        return processTaskRequest(token, request, bindingResult, Session.Status.COMPLETED,
            HttpStatus.OK);
    }

    @Override
    public HttpStatus skipTask(String token, TaskIdReqst request, BindingResult bindingResult) {
        return processTaskRequest(token, request, bindingResult, Session.Status.SKIPPED,
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ListOfTasksResp> get(String token) {
        String username = extractUsernameFromToken(token);
        List<Session> sessionToday = getTodaySessionsByUsername(username);

        if (!sessionToday.isEmpty()) {
            return createResponse(sessionToday.stream()
                .map(Session::getTask)
                .collect(Collectors.toList()));
        }

        List<Task> tasks = tasksDBService.getThreeRandomTasks();
        tasks.forEach(task -> sessionManipulation(Session.Status.ASSIGNED, username, task));
        return createResponse(tasks);
    }

    private HttpStatus processTaskRequest(String token, TaskIdReqst request,
        BindingResult bindingResult, Session.Status status, HttpStatus httpStatus) {
        validate(bindingResult);
        String username = extractUsernameFromToken(token);
        Task task = tasksDBService.findById(request.taskId());
        sessionManipulation(status, username, task);

        return httpStatus;
    }

    private List<Session> getTodaySessionsByUsername(String username) {
        final int SESSIONS_START_HOUR = 3;
        final int SESSION_START_MINUTE = 0;

        LocalDateTime todayAt3AM = LocalDate.now().atTime(SESSIONS_START_HOUR, SESSION_START_MINUTE);
        return tasksDBService.findAllSessionsByUsername(username)
            .stream()
            .filter(session -> session.getCreatedAt().isAfter(todayAt3AM))
            .collect(Collectors.toList());
    }

    private ResponseEntity<ListOfTasksResp> createResponse(List<Task> tasks) {
        return new ResponseEntity<>(new ListOfTasksResp(tasks.stream()
            .map(tasksMapper::toDto)
            .collect(Collectors.toList())), HttpStatus.OK);
    }

    private void sessionManipulation(Session.Status status, String username, Task task) {
        Session session = createSession(username, task, status);
        tasksDBService.save(session);
    }

    private Session createSession(String username, Task task, Session.Status status) {
        return Session.builder()
            .task(task)
            .username(username)
            .status(status)
            .createdAt(LocalDateTime.now())
            .build();
    }

    private void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<CustomError> errors = bindingResult.getFieldErrors()
                .stream()
                .map(error -> new CustomError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
            throw new FieldsNotValidException(errors);
        }
    }

    private String extractUsernameFromToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        try {
            return decodeJwtService.extractUsername(token);
        } catch (Exception ex) {
            LOGGER.error("Error extracting username from token", ex);
            throw new RuntimeException("Failed to extract username from token", ex);
        }
    }
}