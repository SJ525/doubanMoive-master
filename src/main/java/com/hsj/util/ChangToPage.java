package com.hsj.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
public class ChangToPage<T> {
    public  Page<T>  getPage(List<T> list, Pageable pageable){
        int start = (int)pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size():(start + pageable.getPageSize());
        Page<T> page = new PageImpl(list.subList(start, end), pageable, list.size());
        return page;
    }
}
