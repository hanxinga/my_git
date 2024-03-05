package com.sike.project;
import com.sike.project.User;
import org.springframework.web.bind.annotation.*;
import com.sike.project.Goods;
@RestController
@RequestMapping("/goods")
public class Controller {
    @PostMapping("")
    public String addGoods(@RequestBody Goods goods ){
        System.out.println("新增商品信息："+goods);
        return "新增商品成功！";
    }
    @DeleteMapping("/{id}")
    public String deleteGoods(@PathVariable int id){
        System.out.println("删除商品id；"+id);
        return "删除商品成功！";
    }
    @PutMapping("")
    public String updateGoods(@RequestBody Goods goods){
        System.out.println("修改后的商品信息；"+goods);
        return "修改商品成功!";
    }
    @GetMapping("/{id}")
    public String getUser(@PathVariable int id){
        System.out.println("查找商品信息id："+id);
        return "查找商品id成功！";
    }
    @GetMapping("")
    public String getGoods(){
        System.out.println("查找所有的商品");
        return "查找所有商品成功！";
    }
}
