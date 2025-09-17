package com.niels.homebanking.util;

public class Constant {

    /*
        # start-of-string
        (?=.*[0-9])       # a digit must occur at least once
        (?=.*[a-z])       # a lower case letter must occur at least once
        (?=.*[A-Z])       # an upper case letter must occur at least once
        (?=.*[@#$%^&+=])  # a special character must occur at least once
        (?=\S+$)          # no whitespace allowed in the entire string
        .{8,}             # anything, at least eight places though
        $                 # end-of-string
    */
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    // Errors
    public static final String ERR_0001 = "An account with the same tax code already exists";
    public static final String ERR_0002 = "An account with the same email already exists";
    public static final String ERR_0003 = "Resource not found";
    public static final String ERR_0004 = "Status already exists";
    public static final String ERR_0005 = "Transaction Status not found";
    public static final String ERR_0006 = "User Account not found";
    public static final String ERR_0007 = "Currency not found";
    public static final String ERR_0008 = "A Bank Account with the same iban already exists";
    public static final String ERR_0009 = "A Bank Account with the same number already exists";
    public static final String ERR_0010 = "Bank Account not found";
    public static final String ERR_0011 = "A Currency with the same ISO already exists";
    public static final String ERR_0012 = "User Account not found";
}
