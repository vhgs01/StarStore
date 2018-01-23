package com.starstore.hugo.victor.starstore.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Victor Hugo on 18/01/2018.
 */

// CLASSE QUE RETORNA UMA STRING FORMATADA PARA A MOEDA BRASILEIRA
public abstract class Util {
    public static String formatLocalCoin(double value, boolean removeDecimal) throws Exception {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String formatedValue = numberFormat.format(value);

        if (!removeDecimal) {
            return formatedValue;
        }

        return formatedValue.substring(0, formatedValue.length() - 3);
    }

}