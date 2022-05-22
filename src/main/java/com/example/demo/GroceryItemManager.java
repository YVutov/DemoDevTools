package com.example.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class GroceryItemManager {
    private final GroceryList groceryList;
    private final HashMap<ProductType, HashSet<GroceryItem>>            existingGroceryItems = new HashMap<>();

    public GroceryItemManager(GroceryList groceryList) throws NullPointerException {
        if (groceryList == null) {
            throw new NullPointerException("The provided list is null");
        }
        this.groceryList = groceryList;
        init();
    }

    protected void init() {
        for (final ProductType type : ProductType.values()) {
            existingGroceryItems.put(type, new HashSet<>());
        }
    }

    /**
     * Searches for and if found removes an item by id.
     *
     * @param id the id of the item
     * @throws IllegalArgumentException see {@link GroceryItemManager#getGroceryItem(long)}
     */
    public void remove(long id) throws IllegalArgumentException {
        remove(getGroceryItem(id));
    }

    public Optional<GroceryItem> getGroceryItem(final String name, final ProductType productType,
                                                final ProductType.StorageType storageType) {
        GroceryItem ret = null;
        for (final GroceryItem item : getGroceryItemsOfType(productType)) {
            if (name.contentEquals(item.getName()) &&
                    storageType == item.getStorageType()) {
                ret = item;
                break;
            }
        }
        return Optional.ofNullable(ret);
    }

    /**
     * Getter for all items of a certain {@link ProductType product type}
     *
     * @param type - The {@link ProductType product type} in question
     * @return - All items of the corresponding {@link ProductType product type}
     */
    public HashSet<GroceryItem> getGroceryItemsOfType(final ProductType type) {
        return new HashSet<>(existingGroceryItems.get(type));
    }


    public HashSet<GroceryItem> getGroceryItemsOfType(ProductType.StorageType storageType) {
        HashSet<GroceryItem> items = new HashSet<>();
        for (ProductType productType : existingGroceryItems.keySet()) {
            //Check if the product type can have the storage type in question
            if (Arrays.asList(productType.getStorageTypes()).contains(storageType)) {
                //Check individual items and add
                for (GroceryItem item : existingGroceryItems.get(productType)) {
                    if (item.getStorageType() == storageType) {
                        items.add(item);
                    }
                }
            }
        }
        return items;
    }


    public HashMap<ProductType, HashSet<GroceryItem>> getUnsortedGroceryItems() {
        return new HashMap<>(existingGroceryItems);
    }

    public void add(HashMap<ProductType, HashSet<GroceryItem>> existingItems, GroceryItem newItem) {
        existingItems.get(newItem.getProductType()).add(newItem);
    }


    public void add(GroceryItem item) throws NullPointerException {
        if (item != null) {
            existingGroceryItems.get(item.getProductType()).add(item);
        } else {
            throw new NullPointerException();
        }
    }


    public HashSet<GroceryItem> getSavedGroceryItems() {
        final HashSet<GroceryItem> savedItems = new HashSet<>();
        existingGroceryItems.forEach((k, v) -> v.forEach(element -> {
            if (element.isSaved()) {
                savedItems.add(element);
            }
        }));
        return savedItems;
    }


    public boolean toggleSaved(GroceryItem item) throws NullPointerException {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        return item.toggleSaved();
    }


    public void remove(final GroceryItem item) throws NullPointerException {
        if (item != null) {
            AtomicReference<OrderItem> foundOrderItem = new AtomicReference<>();
            groceryList.getOrderItemsOfType(item.getProductType()).forEach(i -> {
                if (i.getGroceryItem().equals(item)) {
                    foundOrderItem.set(i);
                }
            });
            if (foundOrderItem.get() != null) {
                groceryList.removeOrderItem(foundOrderItem.get().getId());
            }
            existingGroceryItems.get(item.getProductType()).remove(item);
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * Search for a grocery item by id.
     *
     * @param id of the searched item.
     * @return The GroceryItem if found.
     * @throws IllegalArgumentException If id < 0 or no item with that ID exists.
     */
    public GroceryItem getGroceryItem(long id) throws IllegalArgumentException {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be > 0. Current value: " + id);
        }
        AtomicReference<GroceryItem> ret = new AtomicReference<>();
        existingGroceryItems.values().forEach(i -> i.forEach(k -> {
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


    public GroceryItem createGroceryItem(String name,
                                         ProductType productType,
                                         ProductType.StorageType storageType,
                                         boolean saved) throws IllegalArgumentException {
        GroceryItem item = new GroceryItem(name, productType, storageType, saved, groceryList);
        add(item);
        return item;
    }

    /**
     * Checks if the item is saved.
     *
     * @param newItem - The item to check
     * @return - true if saved
     */
    public boolean isAlreadySaved(final GroceryItem newItem) {
        for (final GroceryItem existingItem : getGroceryItemsOfType(newItem)) {
            if (GroceryItem.areEqual(newItem, existingItem)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for all items of the item's {@link ProductType product type}
     *
     * @param item - The item from which the {@link ProductType product type} is derived
     * @return - All items of the corresponding {@link ProductType product type}
     */
    public HashSet<GroceryItem> getGroceryItemsOfType(final GroceryItem item) {
        return getGroceryItemsOfType(item.getProductType());
    }

    /**
     * Returns all items
     *
     * @return - All items
     */
    public HashSet<GroceryItem> getUnsortedGroceryItemsSet() {
        final HashSet<GroceryItem> items = new HashSet<>();
        existingGroceryItems.forEach((k, v) -> items.addAll(v));
        return items;
    }

    public void setGroceryItems(HashMap<ProductType, HashSet<GroceryItem>> in) {
        existingGroceryItems.clear();
        existingGroceryItems.putAll(in);
    }

    public HashMap<ProductType, HashSet<GroceryItem>> getExistingGroceryItems() {
        return existingGroceryItems;
    }
}