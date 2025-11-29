package com.niels.homebanking.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Random;

public class Common {

    private static final Random random = new Random();

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

    public static String createIban() {
        // Start with IT00K (we'll replace check digits later)
        StringBuilder iban = new StringBuilder("IT00K");
        // ABI (5 digits) - Italian bank code
        iban.append(String.format("%05d", random.nextInt(100000)));
        // CAB (5 digits) - branch code
        iban.append(String.format("%05d", random.nextInt(100000)));
        // Account number (12 characters, usually digits but can have leading zeros)
        for (int i = 0; i < 12; i++) {
            iban.append(random.nextInt(10));
        }
        return iban.toString();
    }

    public static int setPage(int page) {
        return Math.max(page, 1);
    }

}
