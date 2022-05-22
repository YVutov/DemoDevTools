package com.example.demo;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * A saved or preexisting sorting profile.
 */
public class SortingProfile implements Serializable {
    private Map<Integer, ProductType>             productPriority;
    private Map<Integer, ProductType.StorageType> storagePriority;
    private String                                                                    name;
    private boolean                               isStoragePrioritized = false;

    public SortingProfile(String name, GroceryListService service) throws IllegalArgumentException {
        if (name == null || service == null) {
            throw new NullPointerException("A parameter is null.");
        } else if (name.isBlank()) {
            throw new IllegalArgumentException("The name is blank.");
        } else if (checkNameAvailability(name, service)) {
            throw new IllegalArgumentException("Profile with that name already exists.");
        }
        //Set the priorities for the different product types
        productPriority = new TreeMap<>();
        productPriority.put(1, ProductType.BREAD);
        productPriority.put(2, ProductType.HEALTH_AND_BEAUTY);
        productPriority.put(3, ProductType.ALCOHOL);
        productPriority.put(4, ProductType.HOUSEHOLD);
        productPriority.put(5, ProductType.MEAT);
        productPriority.put(6, ProductType.BAKING);
        productPriority.put(7, ProductType.DAIRY);
        productPriority.put(8, ProductType.DESERT);
        productPriority.put(9, ProductType.NON_ALCOHOLIC);
        productPriority.put(10, ProductType.OTHER);
        productPriority.put(11, ProductType.PASTA);
        productPriority.put(12, ProductType.PRODUCE);
        productPriority.put(13, ProductType.RICE);
        productPriority.put(14, ProductType.SEAFOOD);

        //Set the priorities for the different storage types
        storagePriority = new TreeMap<>();
        storagePriority.put(1, ProductType.StorageType.FROZEN);
        storagePriority.put(2, ProductType.StorageType.NONE);
        storagePriority.put(3, ProductType.StorageType.CANNED_OR_PASTEURIZED);
        storagePriority.put(4, ProductType.StorageType.READY_TO_EAT);
        storagePriority.put(5, ProductType.StorageType.CHILLED);

        this.name = name;
    }

    public boolean checkNameAvailability(String name, GroceryListService service) {
        Optional<SortingProfile> optional = service.getSortingProfiles().stream().filter(x -> name.equals(x.getName())).findFirst();
        return optional.isPresent();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isStoragePrioritized() {
        return isStoragePrioritized;
    }

    /**
     * Toggles whether storage order is prioritized.
     *
     * @return Is storage order prioritized?
     */
    public boolean toggleStoragePriority() {
        isStoragePrioritized = !isStoragePrioritized;
        return isStoragePrioritized;
    }

    /**
     * Returns a copy of the product priorities.
     *
     * @return - a copy of the product priorities.
     */
    public TreeMap<Integer, ProductType> getProductPriority() {
        return new TreeMap<>(productPriority);
    }

    /**
     * Returns a copy of the storage priorities.
     *
     * @return - a copy of the storage priorities.
     */
    public TreeMap<Integer, ProductType.StorageType> getStoragePriority() {
        return new TreeMap<>(storagePriority);
    }

    /**
     * Swaps the {@param type product type} with the one at the specified index
     *
     * @param type        - The product type to change priority
     * @param newPriority - The new priority
     */
    public void changePriority(ProductType type, int newPriority) throws IllegalArgumentException {
        if (type == null || newPriority < 1 || newPriority > ProductType.values().length) {
            throw new IllegalArgumentException();
        }
        swapPriority(type, productPriority, newPriority);
    }

    /**
     * Swaps the position of a value with the value at a certain integer key.
     *
     * @param value - The value to be moved to the new key.
     * @param map   - The map to work on
     * @param key   - The integer key
     * @param <E>   - The value type
     */
    private <E> void swapPriority(E value, Map<Integer, E> map, int key) {
        E storage = map.get(key);
        for (Integer i : map.keySet()) {
            if (map.get(i) == value) {
                map.put(i, storage);
                break;
            }
        }
        map.put(key, value);
    }

    /**
     * Swaps the {@param type storage type} with the one at the specified index
     *
     * @param type        - The product type to change priority
     * @param newPriority - The new priority
     */
    public void changePriority(ProductType.StorageType type, int newPriority) throws IllegalArgumentException {
        if (type == null || newPriority < 1 || newPriority > ProductType.StorageType.values().length) {
            throw new IllegalArgumentException();
        }
        swapPriority(type, storagePriority, newPriority);
    }

    public void setName(String name) throws NullPointerException, IllegalArgumentException {
        if (name == null) {
            throw new NullPointerException("Name is null");
        } else if (name.isBlank()) {
            throw new IllegalArgumentException("Name is blank");
        }
        this.name = name;
    }

    public void setProductPriority(TreeMap<Integer, ProductType> productPriority)
            throws NullPointerException, IllegalArgumentException {
        if (productPriority == null) {
            throw new NullPointerException();
        } else if (productPriority.keySet().size() != ProductType.values().length) {
            throw new IllegalArgumentException();
        }
        this.productPriority = productPriority;
    }

    public void setStoragePriority(TreeMap<Integer, ProductType.StorageType> storagePriority)
            throws NullPointerException, IllegalArgumentException {
        if (storagePriority == null) {
            throw new NullPointerException("storagePriority is null");
        } else if (storagePriority.keySet().size() != ProductType.StorageType.values().length) {
            throw new IllegalArgumentException("storagePriority has a length of " + storagePriority.keySet().size() + " whereas it should be " + ProductType.StorageType.values().length);
        }
        this.storagePriority = storagePriority;
    }
}