package com.bikeshop.rydex.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PagedResponse<T> {
    private List<T> data;
    private int total;
    private int page;
    private int pageSize;
}