package com.java2nb.novel.core.utils;

import com.java2nb.novel.core.cache.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author xiongxy
 */
public class RandomValidateCodeUtil {


    /**
     * Kuncinya dimasukkan ke dalam sesi
     * */
    public static final String RANDOM_CODE_KEY = "randomValidateCodeKey";
    /**
     *Buat string secara acak dengan hanya nomor String pribadi
     * */
    private String randString = "0123456789";
    /**
     * Gambar lebar
     * */
    private int width = 100;
    /**
     * Gambar tinggi
     * */
    private int height = 38;
    /**
     * Jumlah jalur interferensi
     * */
    private int lineSize = 40;
    /**
     * Buat jumlah karakter secara acak
     * */
    private int stringNum = 4;

    private static final Logger logger = LoggerFactory.getLogger(RandomValidateCodeUtil.class);

    private Random random = new Random();

    /**
     * Dapatkan font
     */
    private Font getFont() {
        return new Font("Fixedsys", Font.ROMAN_BASELINE, 23);
    }

    /**
     * Dapatkan warna
     */
    private Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    /**
     * Hasilkan gambar acak
     */
    public void getRandcode(CacheService cacheService, HttpServletResponse response) {
        // Kelas BufferedImage adalah kelas Image dengan buffer, dan kelas Image adalah kelas yang digunakan untuk mendeskripsikan informasi gambar
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        // Objek Graphics yang menghasilkan objek Image, objek tersebut dapat diubah untuk melakukan berbagai operasi menggambar pada gambar
        Graphics g = image.getGraphics();
        //ukuran gambar
        g.fillRect(0, 0, width, height);
        //ukuran huruf
        //warna huruf
        g.setColor(new Color(204,204,204));
        // Gambar garis interferensi
        for (int i = 0; i <= lineSize; i++) {
            drowLine(g);
        }
        // Gambar karakter acak
        String randomString = "";
        for (int i = 1; i <= stringNum; i++) {
            randomString = drowString(g, randomString, i);
        }
        logger.info(randomString);
        //Simpan string acak yang dibuat ke cache
        cacheService.set(RANDOM_CODE_KEY,randomString,60*5);
        g.dispose();
        try {
            // Keluarkan gambar dalam memori ke klien dalam bentuk streaming
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            logger.error("Gagal mengeluarkan gambar di memori ke klien dalam bentuk streaming >>>> ", e);
        }

    }

    /**
     * Gambar string
     */
    private String drowString(Graphics g, String randomString, int i) {
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
                .nextInt(121)));
        String rand = String.valueOf(getRandomString(random.nextInt(randString
                .length())));
        randomString += rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 13 * i, 23);
        return randomString;
    }

    /**
     * Gambar garis interferensi
     */
    private void drowLine(Graphics g) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /**
     * Dapatkan karakter acak
     */
    public String getRandomString(int num) {
        return String.valueOf(randString.charAt(num));
    }
}
