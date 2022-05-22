package com.example.demo;


import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * An interface providing sorting functionality for {@link GroceryItem} and {@link OrderItem} using the active profile from {@link SortingProfile}.
 */
public class SortingService {

    private final GroceryList groceryList;

    SortingService(GroceryList groceryList) throws NullPointerException {
        if (groceryList == null) {
            throw new NullPointerException("The provided list is null");
        }
        this.groceryList = groceryList;
    }

    /**
     * Sorts the existing grocery items based on input orders and priority.
     *
     * @param sortingProfile - The sorting profile containing the product and storage priorities.
     * @param service
     * @return A {@link List} with the ordered items.
     */
    public List<GroceryItem> getSortedGroceryItems(SortingProfile sortingProfile, GroceryList service) {
        if (!sortingProfile.isStoragePrioritized()) {
            return sortGroceryElementsByProductType(sortingProfile.getProductPriority(),
                                                    sortingProfile.getStoragePriority(), service);
        } else {
            return sortGroceryElementsByStorageType(sortingProfile.getProductPriority(),
                                                    sortingProfile.getStoragePriority(), service);
        }
    }

    /**
     * Sorts elements firstly by {@link ProductType}, followed by {@link ProductType.StorageType StorageType}.
     *
     * @param productTypeOrder The order of product types ordered by index. 0 is first.
     * @param storageTypeOrder The order of storage types ordered by index. 0 is first.
     * @return An ArrayList with the ordered elements. 0 is first.
     */
    private List<GroceryItem> sortGroceryElementsByProductType(final TreeMap<Integer, ProductType> productTypeOrder,
                                                               final TreeMap<Integer, ProductType.StorageType> storageTypeOrder,
                                                               GroceryListService service) {
        List<GroceryItem> sortedItems = new ArrayList<>();
        //Sort by product type
        for (final Integer productPriority : productTypeOrder.keySet()) {
            for (final GroceryItem item : service.getGroceryItemsOfType(productTypeOrder.get(productPriority))) {
                //Sort products of product type by storage type
                for (final Integer storagePriority : storageTypeOrder.keySet()) {
                    if (item.getStorageType() == storageTypeOrder.get(storagePriority)) {
                        sortedItems.add(item);
                    }
                }
            }
        }
        return sortedItems;
    }

    /**
     * Sorts elements firstly by {@link ProductType.StorageType StorageType}, followed by {@link ProductType}.
     *
     * @param productTypeOrder The order of product types ordered by index. 0 is first.
     * @param storageTypeOrder The order of storage types ordered by index. 0 is first.
     * @return An ArrayList with the ordered elements. 0 is first.
     */
    private List<GroceryItem> sortGroceryElementsByStorageType(final TreeMap<Integer, ProductType> productTypeOrder,
                                                               final TreeMap<Integer, ProductType.StorageType> storageTypeOrder,
                                                               GroceryListService service) {
        List<GroceryItem> sortedItems = new ArrayList<>();
        //Sort by storage type
        for (final Integer storagePriority : storageTypeOrder.keySet()) {
            for (final GroceryItem item : service.getGroceryItemsOfType(storageTypeOrder.get(storagePriority))) {
                //Sort products of storage type by product type
                for (final Integer productPriority : productTypeOrder.keySet()) {
                    if (item.getProductType() == productTypeOrder.get(productPriority)) {
                        sortedItems.add(item);
                    }
                }
            }
        }
        return sortedItems;
    }

    /**
     * Sorts the existing grocery items based on input orders and priority.
     *
     * @param sortingProfile - The sorting profile containing the product and storage priorities.
     * @return A {@link List} with the ordered items.
     */
    public List<OrderItem> getSortedOrderItems(SortingProfile sortingProfile, GroceryListService service) {
        if (!sortingProfile.isStoragePrioritized()) {
            return sortOrderElementsByProductType(sortingProfile.getProductPriority(),
                                                  sortingProfile.getStoragePriority(), service);
        } else {
            return sortOrderElementsByStorageType(sortingProfile.getProductPriority(),
                                                  sortingProfile.getStoragePriority(), service);
        }
    }

    /**
     * Sorts elements firstly by {@link ProductType}, followed by {@link ProductType.StorageType StorageType}.
     *
     * @param productTypeOrder The order of product types ordered by index. 0 is first.
     * @param storageTypeOrder The order of storage types ordered by index. 0 is first.
     * @return An ArrayList with the ordered elements. 0 is first.
     */
    private List<OrderItem> sortOrderElementsByProductType(final TreeMap<Integer, ProductType> productTypeOrder,
                                                           final TreeMap<Integer, ProductType.StorageType> storageTypeOrder,
                                                           GroceryListService service) {
        List<OrderItem> sortedItems = new ArrayList<>();
        //Sort by product type
        for (final Integer productPriority : productTypeOrder.keySet()) {
            for (final OrderItem item : service.getOrderItemsOfType(productTypeOrder.get(productPriority))) {
                //Sort products of product type by storage type
                for (final Integer storagePriority : storageTypeOrder.keySet()) {
                    if (item.getStorageType() == storageTypeOrder.get(storagePriority)) {
                        sortedItems.add(item);
                    }
                }
            }
        }
        return sortedItems;
    }

    /**
     * Sorts elements firstly by {@link ProductType.StorageType StorageType}, followed by {@link ProductType}.
     *
     * @param productTypeOrder The order of product types ordered by index. 0 is first.
     * @param storageTypeOrder The order of storage types ordered by index. 0 is first.
     * @return An ArrayList with the ordered elements. 0 is first.
     */
    private List<OrderItem> sortOrderElementsByStorageType(final TreeMap<Integer, ProductType> productTypeOrder,
                                                           final TreeMap<Integer, ProductType.StorageType> storageTypeOrder,
                                                           GroceryListService service) {
        List<OrderItem> sortedItems = new ArrayList<>();
        //Sort by storage type
        for (final Integer storagePriority : storageTypeOrder.keySet()) {
            for (final OrderItem item : service.getOrderItemsOfType(storageTypeOrder.get(storagePriority))) {
                //Sort products of storage type by product type
                for (final Integer productPriority : productTypeOrder.keySet()) {
                    if (item.getProductType() == productTypeOrder.get(productPriority)) {
                        sortedItems.add(item);
                    }
                }
            }
        }
        return sortedItems;
    }

    public List<OrderItem> getSortedOrderItems(String name) throws IllegalArgumentException {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name is blank");
        }
        AtomicReference<SortingProfile> profile = new AtomicReference<>();
        groceryList.getSortingProfiles().forEach(i -> {
            if (i.getName().equals(name)) {
                profile.set(i);
            }
        });
        if (profile.get() != null) {
            return getSortedOrderItems(profile.get(), groceryList);
        } else {
            throw new IllegalArgumentException("Sorting profile with the name " + name + " does not exist.");
        }
    }
}