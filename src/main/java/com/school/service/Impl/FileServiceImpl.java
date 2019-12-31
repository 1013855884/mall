package com.school.service.Impl;

import com.google.common.collect.Lists;
import com.school.service.FileService;
import com.school.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file,String path){
        String fileName = file.getOriginalFilename();
        //扩展名

        String fileExtensionName = fileName.substring(fileName.lastIndexOf("."));
        String uploadFileName = UUID.randomUUID().toString() + fileExtensionName;
        logger.info("上传文件开始,上传文件的文件名{}，上传的路径{}，新文件名{}",fileName,path,uploadFileName);
        File fileDir = new File(path);
        //判断文件是否存在
        if (!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);


        try {
            file.transferTo(targetFile);
            //文件已经上传成功了

            //将targetFile上传到我们的ftp上
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));


            // 上传完之后，删除upload下面的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }

        return targetFile.getName();
    }


}
