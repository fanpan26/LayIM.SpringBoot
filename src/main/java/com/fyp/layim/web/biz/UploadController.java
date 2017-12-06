package com.fyp.layim.web.biz;

import com.fyp.layim.domain.result.JsonResult;
import com.fyp.layim.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

@RestController
@RequestMapping(value = "/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传文件的路径配置
     * */
    @Value("${layim.upload.dir}")
    private String fileDirPath;

    private final ResourceLoader resourceLoader;

    @Autowired
    public UploadController(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }

    @PostMapping(value = "/file")
    public JsonResult uploadImg(@RequestParam("file")MultipartFile file) throws IOException{
       return uploadService.uploadFile(file, fileDirPath);
    }

    /**
     * 访问文件路径，使用resourceLoader获取文件
     * */
    @GetMapping(value = "/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        try {
            String location = "file:"+ Paths.get(fileDirPath, filename).toString();
            Resource resource = resourceLoader.getResource(location);
            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
