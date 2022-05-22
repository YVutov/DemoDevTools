package com.example.demo;

import java.util.concurrent.atomic.AtomicBoolean;

public class OrderItem {
    private final  GroceryItem item;
    private        int         quantity;
    private static long        idCounter = 0; //UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE is also a possibility but it's overkill
    private final  long        id;

    public OrderItem(GroceryItem item, GroceryListService service) throws NullPointerException {
        this(item, service, 1);
    }

    public OrderItem(GroceryItem groceryItem, GroceryListService service, int quantity) {
        if (groceryItem == null) {
            throw new NullPointerException();
        }
        this.item     = groceryItem;
        this.quantity = quantity;
        this.id       = idCounter++;
    }

    public OrderItem(final String name,
                     final ProductType productType,
                     final ProductType.StorageType storageType,
                     final boolean saved,
                     GroceryListService service) throws IllegalArgumentException {

        this.item = new GroceryItem(name, productType, storageType, saved, service);
        quantity  = 1;
        this.id   = idCounter++;
    }

    public boolean search(String name,
                          ProductType productType,
                          ProductType.StorageType storageType,
                          GroceryListService service) {
        AtomicBoolean ret = new AtomicBoolean();
        service.getOrderItemsOfType(productType).forEach(i -> {
            if (i.getName().equals(name) && i.getProductType() == productType && i.getStorageType() == storageType) {
                ret.set(true);
            }
        });
        return ret.get();
    }

    public String getName() {
        return item.getName();
    }

    @Override
    public String toString() {
        return getGroceryItem().toString();
    }

    public int getQuantity() {
        return quantity;
    }

    public int incrementQuantity() {
        return quantity++;
    }

    public boolean isSaved() {
        return item.isSaved();
    }

    public int decrementQuantity() {
        return quantity--;
    }

    public ProductType getProductType() {
        return getGroceryItem().getProductType();
    }

    public GroceryItem getGroceryItem() {
        return item;
    }

    public ProductType.StorageType getStorageType() {
        return getGroceryItem().getStorageType();
    }

    public boolean toggleSaved() {
        return getGroceryItem().toggleSaved();
    }

    public long getId() {
        return id;
    }
}