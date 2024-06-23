package org.capsule.com.tasks.dtos.errors;

import java.util.List;
import org.capsule.com.tasks.dtos.CoDto;

public record ErrorsCollection<E extends CoDto>(List<E> errors) implements CoDto {

}
