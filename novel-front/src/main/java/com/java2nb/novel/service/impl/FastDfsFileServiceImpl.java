package com.java2nb.novel.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.java2nb.novel.core.utils.Constants;
import com.java2nb.novel.core.utils.FileUtil;
import com.java2nb.novel.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author 11797
 */
@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(prefix = "pic.save", name = "storage", havingValue = "fastDfs")
public class FastDfsFileServiceImpl implements FileService {

    private final FastFileStorageClient storageClient;

    @Value("${fdfs.webUrl}")
    private String webUrl;

    @Override
    public String transFile(String picSrc, String picSavePath) {

        File file;
        String filePath = FileUtil.network2Local(picSrc, picSavePath, Constants.LOCAL_PIC_PREFIX);
        if (filePath.contains(Constants.LOCAL_PIC_PREFIX)) {
            file = new File(picSavePath + filePath);
        } else {
            //Gambar default tidak disimpan
            return filePath;
        }

        try {
            FileInputStream inputStream = new FileInputStream(file);
            StorePath storePath = storageClient.uploadFile(inputStream, file.length(),
                    FilenameUtils.getExtension(file.getName()), null);
            //Lintasan LOCAL_PIC_PREFIX ditambahkan di sini, yang menunjukkan bahwa gambar tersebut adalah sumber daya pribadi, bukan sumber daya jaringan yang dirayapi oleh perayap, dan tidak perlu diubah lagi.
            // Ketika benar-benar mengakses, tulis ulang jalur melalui instruksi rewite nginx dan hapus LOCAL_PIC_PREFIX
            return webUrl+Constants.LOCAL_PIC_PREFIX+storePath.getFullPath();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            //menghapus
            file.delete();
        }

        return "/images/default.gif";
    }
}
