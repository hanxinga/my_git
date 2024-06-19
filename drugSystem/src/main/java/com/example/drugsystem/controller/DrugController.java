package com.example.drugsystem.controller;
import com.example.drugsystem.pojo.Drug;
import com.example.drugsystem.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/drug")
public class DrugController<Items> {

    @Autowired
    private DrugService drugService;


    @PostMapping("")
    public ResponseEntity<String> addDrug(@RequestBody Drug drug) {
        return ResponseEntity.ok("药品添加成功!");
    }


    @GetMapping("")
    public List<Drug> findAllDrug() {
        return drugService.findAllDrugs();

    }

    @GetMapping("/{id}")
    public Drug findDrugById(@PathVariable("id") int id) {
        return drugService.findDrugById(id);
    }

    @GetMapping("/checkShoppingCart/{id}")
    public int checkShoppingCartById(@PathVariable("id") int id) {
        return drugService.checkShoppingCartById(id);
    }

    @GetMapping("/purchase/{id}")
    public String purchaseDrugById(@PathVariable("id") int id) {
        return drugService.purchaseDrugById(id);
    }

    @GetMapping("/shoppingCart")
    public List<Drug> shoppingCart() {
        return drugService.shoppingCart();
    }

    @GetMapping("/removePurchase/{id}")
    public String removePurchaseById(@PathVariable("id") int id) {
        return drugService.removePurchaseById(id);
    }

    @GetMapping("/checkPurchaseRecord/{id}")
    public int checkPurchaseRecordById(@PathVariable("id") int id) {
        return drugService.checkPurchaseRecordById(id);
    }

    @GetMapping("/purchaseRecord")
    public List<Drug> PurchaseRecord() {
        return drugService.PurchaseRecord();
    }

    @PostMapping("/name")
    public String save(@RequestBody Drug user) {
        return user.toString();
    }

        @PostMapping("/upload")
        public String handleFileUpload(@RequestParam("file") MultipartFile file) {
            String name = file.getOriginalFilename();
            try {
                Files.copy(file.getInputStream(), Paths.get("D:/assets", name),
                        StandardCopyOption.REPLACE_EXISTING);
                return "文件上传成功";
            } catch (IOException e) {
                e.printStackTrace();
                return "文件上传失败: " + e.getMessage();
            }
        }
}

