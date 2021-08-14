package com.project.domain;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class GenericPaging<T> implements GenericPagination<T> {

    @Override
    public <T> PaginationResult<T> pagination(Page<T> page, Integer pageNo,
                                              Integer limit, String sortField, String sortDir) {
        PaginationResult<T> pagination = new PaginationResult<>();

        pagination.setTotalPage(page.getTotalPages());
        pagination.setTotalItem(page.getTotalElements());
        pagination.setList(page.toList());
        pagination.setPageNo(pageNo);
        pagination.setLimit(limit);
        pagination.setSortField(sortField);
        pagination.setSortDir(sortDir);

        return pagination;
    }
}
