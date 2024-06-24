package org.capsule.com.tasks.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDataDto implements CoDto{

    private String username;

    private Status status;

    public enum Status {
        IN_PROGRESS, COMPLETED, SKIP
    }
}