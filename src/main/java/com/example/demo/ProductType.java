package com.example.demo;

/**
 * Product types and their relevant storage types.
 *
 */
public enum ProductType {
    HEALTH_AND_BEAUTY(StorageType.NONE),
    MEAT(StorageType.CHILLED, StorageType.FROZEN, StorageType.CANNED_OR_PASTEURIZED, StorageType.READY_TO_EAT),
    PRODUCE(StorageType.CHILLED, StorageType.NONE, StorageType.FROZEN, StorageType.CANNED_OR_PASTEURIZED,
            StorageType.READY_TO_EAT),
    ALCOHOL(StorageType.CHILLED, StorageType.NONE),
    HOUSEHOLD(StorageType.NONE),
    SEAFOOD(StorageType.CHILLED, StorageType.NONE, StorageType.FROZEN, StorageType.CANNED_OR_PASTEURIZED,
            StorageType.READY_TO_EAT),
    DESERT(StorageType.NONE, StorageType.CHILLED, StorageType.FROZEN, StorageType.READY_TO_EAT),
    BAKING(StorageType.NONE, StorageType.CHILLED),
    NON_ALCOHOLIC(StorageType.NONE, StorageType.CHILLED),
    DAIRY(StorageType.CHILLED, StorageType.CANNED_OR_PASTEURIZED),
    BREAD(StorageType.CHILLED, StorageType.NONE, StorageType.FROZEN, StorageType.READY_TO_EAT),
    RICE(StorageType.NONE, StorageType.FROZEN, StorageType.CANNED_OR_PASTEURIZED),
    PASTA(StorageType.CHILLED, StorageType.NONE, StorageType.FROZEN, StorageType.CANNED_OR_PASTEURIZED,
          StorageType.READY_TO_EAT),
    OTHER(StorageType.CANNED_OR_PASTEURIZED, StorageType.CHILLED, StorageType.FROZEN, StorageType.NONE,
          StorageType.READY_TO_EAT);

    /**
     * The storage types
     */
    private final StorageType[] storageMethods;

    /**
     * Constructor
     *
     * @param storageMethods - The relevant storage types for the new product type
     */
    ProductType(final StorageType... storageMethods) {
        this.storageMethods = storageMethods;
    }

    /**
     * Getter for the relevant storage types
     *
     * @return - The relevant storage type
     */
    public StorageType[] getStorageTypes() {
        return storageMethods.clone();
    }

    /**
     * Returns a beautified string.
     */
    @Override
    public String toString() {
        return switch (this) {
            case HEALTH_AND_BEAUTY -> "Health and beauty";
            case ALCOHOL -> "Alcohol";
            case BAKING -> "Baking";
            case BREAD -> "Bread";
            case DAIRY -> "Dairy";
            case DESERT -> "Desert";
            case HOUSEHOLD -> "Household";
            case MEAT -> "Meat";
            case NON_ALCOHOLIC -> "Non alcoholic beverage";
            case OTHER -> "Other";
            case PASTA -> "Pasta";
            case PRODUCE -> "Produce";
            case RICE -> "Rice";
            case SEAFOOD -> "Seafood";
        };
    }

    /**
     * Storage types
     *
     * @author Yani Vutov
     */
    public enum StorageType {
        NONE, CHILLED, FROZEN, CANNED_OR_PASTEURIZED, READY_TO_EAT;

        /**
         * Returns a beautified string.
         */
        @Override
        public String toString() {
            return switch (this) {
                case CANNED_OR_PASTEURIZED -> "Canned/Pasteurized";
                case CHILLED -> "Chilled";
                case FROZEN -> "Frozen";
                case NONE -> "Basic";
                case READY_TO_EAT -> "Ready to eat";
            };
        }
    }
}