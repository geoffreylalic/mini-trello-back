package com.geoffrey.mini_trello_back.common;

public record ResponsePaginatedDto<T>(T content,
                                      int page,
                                      int size,
                                      long totalElements,
                                      int totalPages) {

}
