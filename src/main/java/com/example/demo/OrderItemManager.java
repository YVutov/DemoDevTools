package com.example.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;

public class OrderItemManager {
    private final HashMap<ProductType, HashSet<OrderItem>> existingOrderItems = new HashMap<>();
    private final GroceryList                              groceryList;

    public OrderItemManager(GroceryList groceryList) throws NullPointerException {
        if (groceryList == null) {
            throw new NullPointerException("The provided list is null");
        }
        this.groceryList = groceryList;
        init();
    }

    protected void init() {
        for (final ProductType type : ProductType.values()) {
            existingOrderItems.put(type, new HashSet<>());
        }
    }

    private OrderItem createOrderItem(GroceryItem item) throws NullPointerException {
        if (item == null) {
            throw new NullPointerException();
        }
        return new OrderItem(item, groceryList);
    }

    public OrderItem createOrderItem(String name,
                                     ProductType productType,
                                     ProductType.StorageType storageType,
                                     boolean saved) throws IllegalArgumentException {
        return new OrderItem(groceryList.createGroceryItem(name, productType, storageType, saved), groceryList);
    }

    public HashSet<OrderItem> getOrderItemsOfType(ProductType type) {
        return new HashSet<>(existingOrderItems.get(type));
    }

    public HashSet<OrderItem> getOrderItemsOfType(ProductType.StorageType storageType) {
        HashSet<OrderItem> items = new HashSet<>();
        for (ProductType productType : existingOrderItems.keySet()) {
            //Check if the product type can have the storage type in question
            if (Arrays.asList(productType.getStorageTypes()).contains(storageType)) {
                //Check individual items and add
                for (OrderItem item : existingOrderItems.get(productType)) {
                    if (item.getStorageType() == storageType) {
                        items.add(item);
                    }
                }
            }
        }
        return items;
    }

    public void add(OrderItem item) throws NullPointerException {
        if (item != null) {
            existingOrderItems.get(item.getGroceryItem().getProductType()).add(item);
        } else {
            throw new NullPointerException();
        }
    }

    public int decrementQuantity(long id) throws IllegalArgumentException {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be > 0. Current value: " + id);
        }
        OrderItem item        = getOrderItem(id);
        int       newQuantity = item.decrementQuantity();
        if (newQuantity == 0) {
            remove(item);
        }
        return newQuantity;
    }

    /**
     * Search for an order item by id.
     *
     * @param id of the searched item.
     * @return The GroceryItem if found.
     * @throws IllegalArgumentException If id < 0 or no item with that ID exists.
     */
    public OrderItem getOrderItem(long id) throws IllegalArgumentException {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be > 0. Current value: " + id);
        }
        AtomicReference<OrderItem> ret = new AtomicReference<>();
        existingOrderItems.values().forEach(i -> i.forEach(k -> {
            if (k.getId() == id) {
                ret.set(k);
            }
        }));
        if (ret.get() != null) {
            return ret.get();
        } else {
            throw new IllegalArgumentException("Item with an id of " + id + " does not exist.");
        }
    }

    public void remove(final OrderItem item) throws NullPointerException {
        if (item == null) {
            throw new NullPointerException();
        }
        if (!item.isSaved()) {
            groceryList.removeGroceryItem(item.getGroceryItem().getId());
        }
        existingOrderItems.get(item.getProductType()).remove(item);
    }

    public int incrementQuantity(long id) throws IllegalArgumentException {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be > 0. Current value: " + id);
        }
        OrderItem item = getOrderItem(id);
        return item.incrementQuantity();
    }

    public void remove(long id) throws IllegalArgumentException {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be > 0. Current value: " + id);
        }
        remove(getOrderItem(id));
    }

    public OrderItem createOrderItem(GroceryItem groceryItem, int quantity) throws NullPointerException {
        if (groceryItem == null) {
            throw new NullPointerException();
        }
        //Check if an item already exists and just increment if it does.
        AtomicReference<OrderItem> item = new AtomicReference<>();
        existingOrderItems.values().forEach(i -> {
            i.forEach(k -> {
                if (k.getGroceryItem().equals(groceryItem)) {
                    item.set(k);
                }
            });
        });
        OrderItem ret;
        if (item.get() == null) {
            ret = new OrderItem(groceryItem, groceryList, quantity);
            add(ret);
            return ret;
        } else {
            ret = item.get();
            for (int i = 0; i < quantity; i++) {
                ret.incrementQuantity();
            }
            return ret;
        }
    }
}