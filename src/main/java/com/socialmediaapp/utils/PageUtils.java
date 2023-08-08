package com.socialmediaapp.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtils {
    public static Pageable normalisePageRequest(Pageable pageable) { //
      return  PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
    }
}
