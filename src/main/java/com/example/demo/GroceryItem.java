package com.example.demo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class GroceryItem implements Serializable {
    private final        ProductType             productType;
    private final        ProductType.StorageType storageType;
    private final        String                                                      name;
    private static long                    idCounter = 0; //UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE is also a possibility but it's overkill
    private final  long                    id;
    private        boolean                 saved;

    public GroceryItem(final String name,
                       final ProductType productType,
                       final ProductType.StorageType storageType,
                       final boolean saved,
                       GroceryListService service) throws IllegalArgumentException {

        this.name        = name;
        this.productType = productType;
        this.storageType = storageType;
        this.id          = idCounter++;
        setSaved(saved);
    }

    public boolean alreadyExists(String name,
                                 ProductType productType,
                                 ProductType.StorageType storageType,
                                 GroceryListService service) {
        AtomicBoolean ret = new AtomicBoolean(false);
        service.getUnsortedGroceryItems().values().forEach(i -> i.forEach(k -> {
            if (k.getName().equals(name) && k.getProductType() == productType && k.getStorageType() == storageType) {
                ret.set(true);
            }
        }));
        return ret.get();
    }

    public ProductType getProductType() {
        return productType;
    }

    public String getName() {
        return name;
    }

    public ProductType.StorageType getStorageType() {
        return storageType;
    }



    /**
     * Compare two GroceryItem objects by name, ProductType and StorageType
     *
     * @param item1 First item
     * @param item2 Second item
     * @return Equal or not
     */
    public static boolean areEqual(final GroceryItem item1, final GroceryItem item2) {
        return item2.getName().contentEquals(item1.getName()) &&
                item2.getProductType().equals(item1.getProductType()) &&
                item2.getStorageType().equals(item1.getStorageType());
    }

    public boolean isSaved() {
        return saved;
    }

    protected void setSaved(final boolean saved) {
        this.saved = saved;
    }

    public boolean toggleSaved() {
        saved = !saved;
        return saved;
    }

    @Override
    public String toString() {
        return getName();
    }

    public long getId() {
        return id;
    }
}