package org.capsule.com.tasks.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.capsule.com.tasks.dtos.CoDto;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto implements CoDto {

    private long id;

    private String title;

    private String description;

    private String status;
}
