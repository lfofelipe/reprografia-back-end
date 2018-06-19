package br.com.reprografia.prod.common.util;

import org.apache.tika.Tika;

import java.io.File;

public class FileUtil {

    public static String getMimeType(byte[] byteArr) {
        try {
            Tika tika = new Tika();
            return tika.detect(byteArr);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static String getMimeType(File file) {
        try {
            Tika tika = new Tika();
            return tika.detect(file);
        }
        catch (Exception e) {
            return null;
        }
    }

}
