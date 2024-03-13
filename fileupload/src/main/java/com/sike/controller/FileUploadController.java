package com.sike.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public String upload(@RequestParam("uploadFile") MultipartFile uploadFile,HttpServletRequest request){
        if(uploadFile==null||uploadFile.isEmpty()){
            return "NULL FILE";
        }
        //获取上传目标文件夹的绝对路径(也可以是本地路径)
        String realPath=request.getServletContext().getRealPath("/uploadFile");
        System.out.println("realPath:"+realPath);//观察实际目录
        //创建为File 对象
        File parentDir=new File(realPath);
        if(!parentDir.isDirectory()){ //如果不存在这个目录则创建
            parentDir.mkdir();
        }
        String filename=uploadFile.getOriginalFilename();
        File target=new File(parentDir,filename);//创建上传目标文件
        try {
            uploadFile.transferTo(target);//源文件拷贝到目标文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }

}
