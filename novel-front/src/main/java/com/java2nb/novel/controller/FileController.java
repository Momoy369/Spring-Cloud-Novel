package com.java2nb.novel.controller;

import com.java2nb.novel.core.bean.ResultBean;
import com.java2nb.novel.core.cache.CacheService;
import com.java2nb.novel.core.utils.Constants;
import com.java2nb.novel.core.utils.RandomValidateCodeUtil;
import com.java2nb.novel.core.utils.RestTemplateUtil;
import com.java2nb.novel.core.utils.UUIDUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author 11797
 */
@Controller
@RequestMapping("file")
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final CacheService cacheService;

    @Value("${pic.save.path}")
    private String picSavePath;

    /**
     * Hasilkan kode verifikasi
     */
    @GetMapping(value = "getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            //Setel jenis yang sesuai dan beri tahu browser bahwa konten keluarannya adalah gambar
            response.setContentType("image/jpeg");
            //Setel informasi header respons dan beri tahu browser untuk tidak menyimpan konten ini ke dalam cache
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            //Output metode gambar kode verifikasi
            randomValidateCode.getRandcode(cacheService, response);
        } catch (Exception e) {
            log.error("Gagal mendapatkan kode verifikasi>>>> ", e);
        }
    }

    /**
     * Unggah File
     */
    @ResponseBody
    @PostMapping("/upload")
    ResultBean upload(@RequestParam("file") MultipartFile file) {
        Date currentDate = new Date();
        try {
            String savePath =
                    Constants.LOCAL_PIC_PREFIX + DateUtils.formatDate(currentDate, "yyyy") + "/" +
                    DateUtils.formatDate(currentDate, "MM") + "/" +
                    DateUtils.formatDate(currentDate, "dd") ;
            String oriName = file.getOriginalFilename();
            String saveFileName = UUIDUtil.getUUID32() + oriName.substring(oriName.lastIndexOf("."));
            File saveFile = new File( picSavePath + savePath, saveFileName);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            file.transferTo(saveFile);
            return ResultBean.ok(savePath+"/"+saveFileName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultBean.error();
        }

    }


}
