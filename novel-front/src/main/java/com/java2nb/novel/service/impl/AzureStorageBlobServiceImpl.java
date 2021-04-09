package com.java2nb.novel.service.impl;

import com.azure.storage.blob.BlobClientBuilder;
// import com.java2nb.novel.core.utils.AzureBlobService;
import com.java2nb.novel.core.utils.Constants;
import com.java2nb.novel.core.utils.FileUtil;
import com.java2nb.novel.service.FileService;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.BlobRequestOptions;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.logging.Level;
// import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(prefix = "pic.save", name = "storage", havingValue = "azure")

public class AzureStorageBlobServiceImpl implements FileService{

    public static final String storageConnectionString = "DefaultEndPointsProtocol=https;" +
    "AccountName=cs11003200080ded6e1;" +
    "AccountKey=hnIAgfyPj4084FZN/qSCHT3v3w8mAyUaGn+aP/hZAs1kf+ecNPiEH/HE4k7kcm7UI6agb/S8/IXD7S3tctKQgw==;";

    @Override
    public String transFile(String picSrc, String picSavePath){
        CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container=null;
        File file;
        String filePath = FileUtil.network2Local(picSrc, picSavePath, Constants.LOCAL_PIC_PREFIX);
        if (filePath.contains(Constants.LOCAL_PIC_PREFIX)){
            file = new File(picSavePath + filePath);
        } else {
            return filePath;
        }
        try {
            storageAccount = CloudStorageAccount.parse(storageConnectionString);
            blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference("lokanovels");

            container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
            
            CloudBlockBlob blob =container.getBlockBlobReference(file.getName());

            blob.uploadFromFile(file.getAbsolutePath());
        } catch (Exception e){
            log.error(e.getMessage(), e);
        } finally {
            file.delete();
        }
        return "/images/default.gif";
    }
}