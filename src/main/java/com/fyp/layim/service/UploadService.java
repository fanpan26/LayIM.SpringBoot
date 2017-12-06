package com.fyp.layim.service;

import com.fyp.layim.domain.result.JsonResult;
import com.fyp.layim.domain.result.UploadResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadService {
    /**
     *  上传文件
     * */
    public JsonResult uploadFile(MultipartFile file,String fileDirPath) throws IOException{
        if(!file.isEmpty()){
            String fileName = file.getOriginalFilename();
            String extensionName = StringUtils.substringAfter(fileName,".");
            //生成新的guid的文件名
            String newFileName = UUID.randomUUID()+ "."+extensionName;
            Path filePath = Paths.get(fileDirPath, newFileName);
            //保存到响应的路径下
            Files.copy(file.getInputStream(), filePath);

            UploadResult uploadResult = new UploadResult();
            uploadResult.setSrc("/upload/" + newFileName);
            uploadResult.setName(fileName);

            return JsonResult.success(uploadResult);
        }else{
            return JsonResult.fail("无文件");
        }
    }
}
