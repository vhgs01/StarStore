package com.starstore.hugo.victor.starstore.utils;

import java.text.NumberFormat;
import java.util.Locale;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

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

    /**
     * Copia o banco de dados para a pasta raiz do sistema
     * @param context O contexto da senha
     */
    public static void copyDbToExternal(Context context) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "/data/" + context.getApplicationContext().getPackageName() + "/databases/" + "cart";
                String backupDBPath = "cart";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}