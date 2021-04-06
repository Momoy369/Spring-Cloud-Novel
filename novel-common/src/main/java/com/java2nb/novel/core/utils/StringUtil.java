package com.java2nb.novel.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * @author xiongxiaoyang
 */
public class StringUtil {

    /**
     * Mengonversi unta yang diberi nama string menjadi garis bawah kapitalisasi. Jika unta kasus-bernama string sebelum konversi kosong, string kosong dikembalikan.</br>
     * 例如：HelloWorld->HELLO_WORLD
     * @param name CamelCase dinamai string sebelum konversi
     * @return String dinamai dengan huruf kapital garis bawah setelah konversi
     */
    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // Proses karakter pertama menjadi huruf besar
            result.append(name.substring(0, 1).toUpperCase());
            // Ulangi untuk memproses karakter yang tersisa
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // Tambahkan garis bawah sebelum huruf kapital
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // Karakter lain langsung diubah menjadi huruf besar
                result.append(s.toUpperCase());
            }
        }
        return result.toString();
    }

    /**
     * Ubah string bernama dalam huruf kapital garis bawah menjadi huruf besar / kecil unta. Jika string yang disebutkan dalam huruf besar garis bawah sebelum konversi kosong, string kosong dikembalikan.</br>
     * 例如：HELLO_WORLD->HelloWorld
     * @param name String yang diberi nama dalam huruf besar garis bawah sebelum konversi
     * @return Kasus unta yang dikonversi bernama string
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // pemeriksaan cepat
        if (name == null || name.isEmpty()) {
           // tidak perlu dikonversi
            return "";
        } else if (!name.contains("_")) {
            // Tanpa garis bawah, huruf kecil hanya huruf pertama
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // Gunakan garis bawah untuk memisahkan string asli
        String camels[] = name.split("_");
        for (String camel : camels) {
            // Lewati garis bawah atau garis bawah ganda di awal dan akhir string asli
            if (camel.isEmpty()) {
                continue;
            }
            // Proses fragmen kasus camel sebenarnya
            if (result.length() == 0) {
                // Segmen camel case pertama, semua huruf adalah huruf kecil
                result.append(camel.toLowerCase());
            } else {
               // Fragmen kasus camel lainnya, dengan huruf pertama kapital
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * Dapatkan karakter Cina yang valid dalam sebuah string
     * */
    public static String getChineseValidWord(String origStr){

        // Dapat mengganti sebagian besar karakter kosong, tidak terbatas pada yang kosong. Penjelasan:\s dapat cocok dengan karakter kosong manapun seperti blank, tab, dan form feed
        origStr = origStr.replaceAll("\\s*","");

       /* // Hapus tanda baca sepenuhnya
         origStr = origStr.replaceAll ("\\ pP", ""); */

        // Hapus semua simbol, sisakan huruf, angka, dan karakter Cina Ada 3 kategori.
        origStr = origStr.replaceAll("[\\pP\\p{Punct}]","");

        // Hapus huruf dan angka
        origStr = origStr.replaceAll("[A-Za-z0-9]*","");

        return origStr;

    }

    /**
      * Dapatkan jumlah kata bahasa Inggris dalam sebuah string
      **/
    public static int getEnglishWordCount(String origStr){
        Pattern pattern = compile("\\b\\w+\\b");
        Matcher matcher = pattern.matcher(origStr);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;

    }

    /**
      * Dapatkan jumlah karakter Cina dalam sebuah string
      **/
    public static int getChineseWordCount(String origStr){
        Pattern pattern = compile("[\u4e00-\u9fa5]");
        Matcher matcher = pattern.matcher(origStr);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;

    }

    /**
    * Dapatkan jumlah digit yang valid dalam sebuah string
    **/
    public static int getNumberWordCount(String origStr){
        Pattern pattern = compile("\\d+");
        Matcher matcher = pattern.matcher(origStr);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;

    }

    /***Dapatkan jumlah karakter yang valid dalam sebuah string
    * */
    public static int getStrValidWordCount(String origStr){
        return getChineseWordCount(origStr) + getEnglishWordCount(origStr) + getNumberWordCount(origStr);

    }

    public static void main(String[] args) {
        String str = "Selamat datang di Indonesia. Halo! Indonesia, saya programmer No. 1123, di sini untuk melayani Anda selama 23 hari. Halo Word";
        System.out.println(getChineseWordCount(str));
        System.out.println(getEnglishWordCount(str));
        System.out.println(getNumberWordCount(str));
    }


}
