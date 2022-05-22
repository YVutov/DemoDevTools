package com.example.demo;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GroceryListService {

    //GroceryItems

    /**
     * Creates a new grocery item
     *
     * @param name        The name of the item
     * @param productType The product type of the item
     * @param storageType The storage type of the item
     * @param saved       Is the item saved upon creation
     * @return The newly created item.
     * @throws IllegalArgumentException Name is blank or taken or a type is invalid.
     */
    GroceryItem createGroceryItem(String name,
                                  ProductType productType,
                                  ProductType.StorageType storageType,
                                  boolean saved) throws IllegalArgumentException;

    /**
     * Getter for grocery items per ID
     *
     * @param groceryItemId of the searched for groceryItem
     * @return the selected groceryItem.
     * @throws IllegalArgumentException if the id is < 0 or no item with it exists.
     */
    GroceryItem getGroceryItem(long groceryItemId) throws IllegalArgumentException;


    /**
     * Search for and remove a {@link GroceryItem grocery item}.
     *
     * @param groceryItemId The ID of the item to be removed.
     * @throws IllegalArgumentException if the id is < 0 or no item with it exists.
     */
    void removeGroceryItem(long groceryItemId) throws IllegalArgumentException;

    //
    // OrderItems
    //

    /**
     * Create an OrderItem
     *
     * @param groceryItemId the selected groceryItem
     * @param quantity      The quantity of the selected groceryItem
     * @return The newly created item
     * @throws IllegalArgumentException groceryItemId < 0, quantity < 1 or an item with that ID does not exist.
     */
    OrderItem createOrderItem(long groceryItemId, int quantity) throws IllegalArgumentException;

    /**
     * Search for and remove an OrderItem
     *
     * @param orderItemId The Order item to be removed.
     * @throws IllegalArgumentException orderItemId < 0 or an item with that id does not exist.
     */
    void removeOrderItem(long orderItemId) throws IllegalArgumentException;

    /**
     * Getter for all order items sorted via a provided profile.
     *
     * @param profile Name of profile for sorting of items
     * @return A list of all OrderItems sorted by the Sorting Profile
     * @throws IllegalArgumentException profile name is blank or no such profile was found.
     */
    List<OrderItem> getSortedOrderItems(String profile) throws IllegalArgumentException;

    /**
     * Search for and increment the quantity of an item
     *
     * @param orderItemId The item to increment.
     * @return The resulting quantity of the item.
     * @throws IllegalArgumentException orderItemId < 0 or an item with that id does not exist.
     */
    int incrementQuantity(long orderItemId) throws IllegalArgumentException;

    /**
     * Search for and decrement the quantity of an item. Upon quantity = 0, the item is removed.
     *
     * @param orderItemId The item to decrement.
     * @return The resulting quantity of the item.
     * @throws IllegalArgumentException orderItemId < 0 or an item with that id does not exist.
     */
    int decrementQuantity(long orderItemId) throws IllegalArgumentException;

    //
    // SortingProfiles
    //

    /**
     * Creates a new sorting profile.
     *
     * @param name The name of the profile.
     * @throws IllegalArgumentException The name is blank or taken.
     */
    void createSortingProfile(String name) throws IllegalArgumentException;

    /**
     * Copies a SortingProfile
     *
     * @param copiedProfileName The name of the copied from profile.
     * @param newProfileName    The name of the copied to profile.
     * @throws IllegalArgumentException The name is blank or taken.
     */
    void copySortingProfile(String copiedProfileName, String newProfileName) throws IllegalArgumentException;

    /**
     * Getter for all sorting profiles.
     *
     * @return A <strong>copy</strong> of the Set containing all profiles.
     */
    Set<SortingProfile> getSortingProfiles();


    /**
     * Searches for and removes a sorting profile.
     *
     * @param profileName Profile to be removed.
     * @return true if successful
     * @throws IllegalArgumentException profileName is blank or no such profile exists.
     */
    boolean removeSortingProfile(String profileName) throws IllegalArgumentException;

    /**
     * Swap the {@link ProductType product type} with the one at the given priority index.
     *
     * @param profileName The profile's name.
     * @param type        The product type to reprioritize.
     * @param newPriority The new priority index of the product type. The indexes allowed correspond to ProductType.values().length.
     * @throws IllegalArgumentException  profileName is blank or no such profile exists
     * @throws IndexOutOfBoundsException newPriority is > ProductType.values().length or < 0
     * @throws NullPointerException      type is null
     */
    void changePriority(String profileName, ProductType type, int newPriority)
            throws IllegalArgumentException, NullPointerException, IndexOutOfBoundsException;

    /**
     * Swap the {@link java/de/hhn/it/devtools/components/grocerylist/ProductType.StorageType storage type} with the one at the given index.
     *
     * @param profileName The profile's name.
     * @param type        The product type to reprioritize.
     * @param newPriority The new priority index of the product type. The indexes allowed correspond to ProductType.values().length.
     * @throws IllegalArgumentException  profileName is blank or no such profile exists
     * @throws IndexOutOfBoundsException newPriority is > ProductType.StorageType.values().length or < 0
     * @throws NullPointerException      type is null
     */
    void changePriority(String profileName, ProductType.StorageType type, int newPriority)
            throws IllegalArgumentException, NullPointerException, IndexOutOfBoundsException;

    //
    // Search and Select
    //

    /**
     * Getter for all grocery items.
     *
     * @return A copy AbstractMap of all grocery items
     */
    Map<ProductType, HashSet<GroceryItem>> getUnsortedGroceryItems();

    /**
     * Searches for all grocery items of a certain product type
     *
     * @param type searched type
     * @return a hashset of all items of type
     * @throws NullPointerException type is null
     */
    Set<GroceryItem> getGroceryItemsOfType(final ProductType type) throws NullPointerException;

    /**
     * Searches for all grocery items of a certain storage type
     *
     * @param storageType searched type
     * @return a hashset of all items of type
     * @throws NullPointerException type is null
     */
    Set<GroceryItem> getGroceryItemsOfType(ProductType.StorageType storageType) throws NullPointerException;

    /**
     * Searches for all order items of a certain product type
     *
     * @param type searched type
     * @return a hashset of all items of type
     * @throws NullPointerException type is null
     */
    Set<OrderItem> getOrderItemsOfType(final ProductType type) throws NullPointerException;

    /**
     * Searches for all order items of a certain storage type
     *
     * @param storageType searched type
     * @return a hashset of all items of type
     * @throws NullPointerException type is null
     */
    Set<OrderItem> getOrderItemsOfType(ProductType.StorageType storageType) throws NullPointerException;
}