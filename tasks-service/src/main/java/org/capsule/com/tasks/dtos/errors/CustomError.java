package org.capsule.com.tasks.dtos.errors;

import org.capsule.com.tasks.dtos.CoDto;

public record CustomError(String field, String message) implements CoDto {

}
