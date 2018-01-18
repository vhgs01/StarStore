package com.starstore.hugo.victor.starstore.utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Victor Hugo on 18/01/2018.
 */

public abstract class Util {
    /**
     * Formats a double value into a String with the local coin. Ex. 3,65 = R$ 3,65
     *
     * @param value The value to be converted
     * @return A String with the value
     */
    public static String formatLocalCoin(double value, boolean removeDecimal) throws Exception {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String formatedValue = numberFormat.format(value);

        if (!removeDecimal) {
            return formatedValue;
        }

        return formatedValue.substring(0, formatedValue.length() - 3);
    }

}