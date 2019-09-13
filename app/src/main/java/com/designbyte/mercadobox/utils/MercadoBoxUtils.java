package com.designbyte.mercadobox.utils;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

public class MercadoBoxUtils {

    public static boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return !TextUtils.isEmpty(email) && pattern.matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phone){
        return phone.length() == 10;
    }
}
