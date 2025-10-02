package com.geoffrey.mini_trello_back.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResponsePaginatedMapper {

    public <T> ResponsePaginatedDto<List<T>> toResponsePaginatedDto(Page<T> page, Pageable pageable) {
        return new ResponsePaginatedDto<>(
                page.getContent(),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
