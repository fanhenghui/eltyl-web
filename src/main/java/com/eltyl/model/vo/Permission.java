package com.eltyl.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties({"pid"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Permission {
    private Integer id;
    private Integer pid;
    private String title;
    private List<Permission> children;
    private boolean checked;
    private boolean open;
}
