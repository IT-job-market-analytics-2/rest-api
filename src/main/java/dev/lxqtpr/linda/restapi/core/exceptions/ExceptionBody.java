package dev.lxqtpr.linda.restapi.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionBody {
    private String message;
    private Integer status;
}