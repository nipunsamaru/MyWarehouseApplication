package com.vantage.myapplication.presentation.Invoice_activity;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vantage.myapplication.database.data_service.DiscountDS;
import com.vantage.myapplication.database.data_service.InvoiceDS;
import com.vantage.myapplication.database.data_service.InvoiceItemDS;
import com.vantage.myapplication.database.data_service.WarehouseDS;
import com.vantage.myapplication.database.table_entity.DiscountEntity;
import com.vantage.myapplication.database.table_entity.InvoiceCombined;
import com.vantage.myapplication.database.table_entity.InvoiceEntity;
import com.vantage.myapplication.database.table_entity.InvoiceItemEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InvoiceActivityViewModel extends ViewModel {

    private MutableLiveData<List<InvoiceCombined>> items;
    private WarehouseDS warehouseDS;
    private InvoiceItemDS invoiceItemDS;
    private InvoiceDS invoiceDS;
    private DiscountDS discountDS;

    public void init(Context context) {
        if (items != null) {
            return;
        }
        warehouseDS = new WarehouseDS(context);
        invoiceDS = new InvoiceDS(context);
        invoiceItemDS = new InvoiceItemDS(context);
        discountDS = new DiscountDS(context);
        items = new MutableLiveData<>();
        loadItems();
    }

    private void loadItems() {
        List<InvoiceCombined> combinedList = warehouseDS.combinedInvoiceData();
        items.setValue(combinedList);
    }

    public LiveData<List<InvoiceCombined>> getItems() {
        return items;
    }

    public long saveInvoice(Context context, InvoiceEntity invoice) {
        return invoiceDS.createOrUpdate(invoice);
    }

    public void saveInvoiceItems(Context context, List<InvoiceItemEntity> invoiceItems) {
        for (InvoiceItemEntity item : invoiceItems) {
            invoiceItemDS.createOrUpdate(item);
        }
    }

    public void saveInvoiceWithItems(Context context, InvoiceEntity invoice, List<InvoiceItemEntity> invoiceItems) {
        double totalQuantity = 0;

        // Calculate the total quantity before saving the invoice
        for (InvoiceItemEntity item : invoiceItems) {
            double discountQty = getDiscountQuantity(item.getQty()); // Get discount quantity based on item quantity
            double finalQuantity = item.getQty() + discountQty; // Calculate final quantity
            totalQuantity += finalQuantity; // Accumulate total quantity
        }

        // Set the total quantity in the invoice's Qty column
        invoice.setQty(totalQuantity);

        // Set the current date in the invoice
        invoice.setDate(getCurrentDate());

        // Save invoice and get its UID
        long invoiceUID = saveInvoice(context, invoice);
        invoice.setUID((int) invoiceUID); // Ensure the UID is set in the invoice entity

        for (InvoiceItemEntity item : invoiceItems) {
            item.setInvoiceUID((int) invoiceUID); // Set the UID of the newly inserted invoice
            item.setType(1); // Set type to 1 for entered quantity
            saveInvoiceItems(context, List.of(item));

            double discountQty = getDiscountQuantity(item.getQty()); // Get discount quantity based on item quantity

            if (discountQty > 0) {
                InvoiceItemEntity discountItem = new InvoiceItemEntity();
                discountItem.setInvoiceUID((int) invoiceUID);
                discountItem.setPriceUID(item.getPriceUID());
                discountItem.setItemUID(item.getItemUID());
                discountItem.setQty(discountQty); // Set discount quantity
                discountItem.setType(2); // Set type to 2 for discount quantities
                saveInvoiceItems(context, List.of(discountItem));
            }
        }
    }

    private double getDiscountQuantity(double qty) {
        List<DiscountEntity> discountList = discountDS.getAllDiscounts();

        for (DiscountEntity discount : discountList) {
            if (qty <= discount.getUpperQty() && qty >= discount.getBreakQty()) {
                return discount.getDiscountQty();
            }
        }

        return 0; // Default discount if no range matches
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }
}
