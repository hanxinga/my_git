package com.example.drugsystem.mapper;

import com.example.drugsystem.pojo.Drug;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DrugMapper {

    //查找所有药品
//    @Select("SELECT * FROM `drug`")
    List<Drug> findAllDrugs();

    Drug findDrugById(int id);

    int checkShoppingCartById(int id);

    String purchaseDrugById(int id);

    List<Drug> shoppingCart();

    String removePurchaseById(int id);

    List<Drug> PurchaseRecord();

    int PurchaseRecordById(int id);
}
