package com.eltyl.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AjaxResponse<T> {
    private Integer code;
    private String msg;
    private T data;
    private Long total;
}
