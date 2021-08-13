package com.project.domain;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class GenericPaging<T> implements GenericPagination<T> {

    @Override
    public <T> PaginationResult<T> pagination(Page<T> page, Object... vargs) {
        PaginationResult<T> pagination = new PaginationResult<>();
        pagination.setTotalPage(page.getTotalPages());
        pagination.setTotalItem(page.getTotalElements());
        pagination.setList(page.toList());
        for (int i=0; i< vargs.length; i++){
            Object parameter = vargs[i];
            if (parameter instanceof Integer){
                if (i == 0)
                    pagination.setPageNo((Integer) parameter);
                if (i == 1)
                    pagination.setLimit((Integer) parameter);
            }else if(parameter instanceof String){
                if (i == 2)
                    pagination.setSortField((String) parameter);
                if (i == 3)
                    pagination.setSortDir((String) parameter);
            }
        }
        return pagination;
    }
}
