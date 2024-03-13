package com.sike.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class FilesUploadController {

    @PostMapping("/uploads")
    public String upload(@RequestParam("uploadFiles") MultipartFile[] uploadFiles,HttpServletRequest request){

        if(uploadFiles==null||uploadFiles.length==0){
            return "NULL FILE";
        }
        //获取上传目标文件夹的绝对路径(也可以是本地路径)
        String realPath=request.getServletContext().getRealPath("E:\\spring\\fileupload\\2024-3-13");

        //创建为File 对象
        File parentDir=new File(realPath);
        if(!parentDir.isDirectory()){ //如果不存在这个目录则创建
            parentDir.mkdir();
        }
        for(MultipartFile uploadFile:uploadFiles) {//循环上传
            String filename = uploadFile.getOriginalFilename();
            File target = new File(parentDir, filename);//创建上传目标文件
            try {
                uploadFile.transferTo(target);//源文件拷贝到目标文件
                System.out.println(target.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "SUCCESS";
    }

}
