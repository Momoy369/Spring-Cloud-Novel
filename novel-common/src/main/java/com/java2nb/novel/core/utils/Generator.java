package com.java2nb.novel.core.utils;

import lombok.SneakyThrows;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Generator kode
 *
 * @author 11797
 */
public class Generator {

    @SneakyThrows
    public static void main(String[] args) {
        //Pesan peringatan selama eksekusi MBG
        List<String> warnings = new ArrayList<>();
        //Baca file konfigurasi MBG kami
        InputStream is = Generator.class.getResourceAsStream("/mybatis/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);
        is.close();
        //Ketika kode yang dihasilkan diulang, jangan menimpa kode aslinya
        DefaultShellCallback callback = new DefaultShellCallback(false);
        //Buat MBG
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        //Jalankan kode yang dihasilkan
        myBatisGenerator.generate(null);
        //Pesan peringatan keluaran
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}
