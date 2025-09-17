package com.niels.homebanking.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.niels.homebanking.util.Constant.PASSWORD_PATTERN;

public class Common {

    public static boolean isPasswordValid(final String password) {
        return password.matches(PASSWORD_PATTERN);
    }

    public static Pageable getPagination(int page, int size, String[] sort) {

        return PageRequest.of(
                page - 1,
                size,
                Sort.by(
                        new Sort.Order(sort[1].equals("asc")
                                ? Sort.Direction.ASC
                                : Sort.Direction.DESC,
                                sort[0])
                )
        );
    }

}
