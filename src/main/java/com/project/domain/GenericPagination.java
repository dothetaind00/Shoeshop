package com.project.domain;

import org.springframework.data.domain.Page;

public interface GenericPagination<T> {
    <T> PaginationResult<T> pagination(Page<T> page, Object... vargs);
}
