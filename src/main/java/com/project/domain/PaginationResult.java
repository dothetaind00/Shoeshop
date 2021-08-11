package com.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaginationResult<T> {

    private Integer pageNo;
    private Integer limit;
    private Integer totalPage;
    private Long totalItem;
    private String sortField;
    private String sortDir;
    private List<T> list = new ArrayList<>();
}
