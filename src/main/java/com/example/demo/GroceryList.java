package com.example.demo;


import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroceryList implements GroceryListService {

    private final        StateManager stateManager = new StateManager(this);

    private final SortingProfileManager sortingProfileManager = new SortingProfileManager(this);
    private final OrderItemManager      orderItemManager      = new OrderItemManager(this);
    private final GroceryItemManager                                              groceryItemManager    = new GroceryItemManager(this);
    private final SortingService        sortingService        = new SortingService(this);

    public GroceryList() {

    }

    public void init() {
        sortingProfileManager.createSortingProfile("Default");
        groceryItemManager.init();
        orderItemManager.init();
    }

    public void save() throws IOException {
        stateManager.save();
    }

    public void load() throws IOException, ClassNotFoundException {
        stateManager.load();
    }

    @Override
    public GroceryItem createGroceryItem(String name,
                                         ProductType productType,
                                         ProductType.StorageType storageType,
                                         boolean saved) throws IllegalArgumentException {
        return groceryItemManager.createGroceryItem(name, productType, storageType, saved);
    }

    @Override
    public GroceryItem getGroceryItem(long groceryItemId) throws IllegalArgumentException {
        return groceryItemManager.getGroceryItem(groceryItemId);
    }

    @Override
    public void removeGroceryItem(long groceryItemId) throws IllegalArgumentException {
        groceryItemManager.remove(groceryItemId);
    }

    @Override
    public OrderItem createOrderItem(long groceryItemId, int quantity) throws IllegalArgumentException {
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1. Value: " + quantity);
        }
        if (groceryItemId < 0) {
            throw new IllegalArgumentException("Id can't be < 0");
        }
        return orderItemManager.createOrderItem(getGroceryItem(groceryItemId), quantity);
    }

    @Override
    public void removeOrderItem(long orderItemId) throws IllegalArgumentException {
        orderItemManager.remove(orderItemId);
    }

    @Override
    public List<OrderItem> getSortedOrderItems(String profile) {
        return sortingService.getSortedOrderItems(profile);
    }

    @Override
    public int incrementQuantity(long orderItemId) throws IllegalArgumentException {
        return orderItemManager.incrementQuantity(orderItemId);
    }

    @Override
    public int decrementQuantity(long orderItemId) throws IllegalArgumentException {
        return orderItemManager.decrementQuantity(orderItemId);
    }

    @Override
    public void createSortingProfile(String name) throws IllegalArgumentException {
        sortingProfileManager.createSortingProfile(name);
    }

    @Override
    public void copySortingProfile(String oldProfile, String newProfile) throws IllegalArgumentException {
        sortingProfileManager.copySortingProfile(oldProfile, newProfile);
    }

    @Override
    public Set<SortingProfile> getSortingProfiles() {
        return sortingProfileManager.getSortingProfiles();
    }

    @Override
    public boolean removeSortingProfile(String profile) {
        return sortingProfileManager.remove(profile);
    }

    @Override
    public void changePriority(String profile, ProductType type, int newPriority) throws IllegalArgumentException {
        sortingProfileManager.changePriority(profile, type, newPriority);
    }

    @Override
    public void changePriority(String profile, ProductType.StorageType type, int newPriority)
            throws IllegalArgumentException {
        sortingProfileManager.changePriority(profile, type, newPriority);
    }

    @Override
    public AbstractMap<ProductType, HashSet<GroceryItem>> getUnsortedGroceryItems() {
        return groceryItemManager.getUnsortedGroceryItems();
    }

    @Override
    public Set<GroceryItem> getGroceryItemsOfType(ProductType type) {
        return groceryItemManager.getGroceryItemsOfType(type);
    }

    @Override
    public Set<GroceryItem> getGroceryItemsOfType(ProductType.StorageType storageType) {
        return groceryItemManager.getGroceryItemsOfType(storageType);
    }

    @Override
    public Set<OrderItem> getOrderItemsOfType(ProductType type) {
        return orderItemManager.getOrderItemsOfType(type);
    }

    @Override
    public Set<OrderItem> getOrderItemsOfType(ProductType.StorageType storageType) {
        return orderItemManager.getOrderItemsOfType(storageType);
    }


    public void createOrderItem(String name, ProductType type, ProductType.StorageType stype, boolean b) {
        GroceryItem item = createGroceryItem(name, type, stype, b);
        createOrderItem(item.getId(), 1);
    }
}