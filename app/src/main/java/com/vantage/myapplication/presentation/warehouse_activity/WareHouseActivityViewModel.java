package com.vantage.myapplication.presentation.warehouse_activity;


import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.vantage.myapplication.database.data_service.DiscountDS;
import com.vantage.myapplication.database.data_service.ItemDS;
import com.vantage.myapplication.database.data_service.PriceDS;
import com.vantage.myapplication.database.table_entity.DiscountEntity;
import com.vantage.myapplication.database.table_entity.ItemEntity;
import com.vantage.myapplication.database.table_entity.PriceEntity;

import java.util.ArrayList;
import java.util.List;

public class WareHouseActivityViewModel extends ViewModel {

    public  void saveItems(Context context) {
        List<ItemEntity> items = new ArrayList<>();

        ItemEntity item1 = new ItemEntity();
        item1.setUID(1);
        item1.setName("Test1");
        items.add(item1);

        ItemEntity item2 = new ItemEntity();
        item2.setUID(2);
        item2.setName("Test2");
        items.add(item2);

        saveItems(context, items);

    }

    private void saveItems(Context context, List<ItemEntity> items) {
        ItemDS itemDS = new ItemDS(context);
        for (ItemEntity entity : items) {
            itemDS.createOrUpdate(entity);
        }
    }

   public void savePrice(Context context){
        List<PriceEntity>  prices = new ArrayList<>();

        PriceEntity price1 = new PriceEntity();
        price1.setUID(1);
        price1.setMRP(100.00);
        price1.setRPL(95.00);
        price1.setItemUID(1);
        prices.add(price1);

        PriceEntity price2 = new PriceEntity();
        price2.setUID(2);
        price2.setMRP(110.00);
        price2.setRPL(100.00);
        price2.setItemUID(1);
        prices.add(price2);

        PriceEntity price3 = new PriceEntity();
        price3.setUID(3);
        price3.setMRP(80.00);
        price3.setRPL(70.00);
        price3.setItemUID(2);
        prices.add(price3);

        savePrice(context, prices);

    }

    private void savePrice(Context context, List<PriceEntity> prices) {
        PriceDS priceDS = new PriceDS(context);
        for (PriceEntity entity : prices) {
            priceDS.createOrUpdate(entity);
        }
    }

    public void saveDiscount(Context context){
        List<DiscountEntity>  discounts = new ArrayList<>();

        DiscountEntity discount1 = new DiscountEntity();
        discount1.setUID(1);
        discount1.setUpperQty(20);
        discount1.setBreakQty(0);
        discount1.setDiscountQty(1);
        discounts.add(discount1);

        DiscountEntity discount2 = new DiscountEntity();
        discount2.setUID(2);
        discount2.setUpperQty(100);
        discount2.setBreakQty(21);
        discount2.setDiscountQty(3);
        discounts.add(discount2);

        DiscountEntity discount3 = new DiscountEntity();
        discount3.setUID(3);
        discount3.setUpperQty(999);
        discount3.setBreakQty(101);
        discount3.setDiscountQty(5);
        discounts.add(discount3);

        saveDiscount(context, discounts);

    }

    private void saveDiscount(Context context, List<DiscountEntity> discounts) {
        DiscountDS discountDS = new DiscountDS(context);
        for (DiscountEntity entity : discounts) {
            discountDS.createOrUpdate(entity);
        }
    }


}
