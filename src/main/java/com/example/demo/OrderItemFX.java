package com.example.demo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class OrderItemFX extends OrderItem {
    private final Label     title         = new Label();
    private Label quantity = new Label();
    private final ImageView productType   = new ImageView();
    private final ImageView storageType   = new ImageView();
    private       StackPane visualization = new StackPane();
    private boolean checked = false;

    public OrderItemFX(GroceryItem item,
                       GroceryListService service) throws NullPointerException {
        super(item, service);
        init();
    }

    @Override
    public int incrementQuantity() {
        int ret = super.incrementQuantity();
        quantity.setText("Qty: " + ret);
        return ret;
    }

    private void init() {
        visualization = new StackPane();
        title.setText(this.getName());
        StackPane.setAlignment(title, Pos.BOTTOM_CENTER);
        quantity.setText("Qty: " + getQuantity());
        StackPane.setAlignment(quantity, Pos.TOP_LEFT);
        switch (this.getProductType()) {
            case HEALTH_AND_BEAUTY -> {productType.setImage(new Image("resources/Beauty.png"));}
            case MEAT -> {productType.setImage(new Image("https://cdn-icons-png.flaticon.com/512/1046/1046769.png"));}
            case PRODUCE -> {productType.setImage(new Image("https://static.vecteezy.com/system/resources/thumbnails/001/833/454/small/avocado-beet-and-broccoli-fresh-nutrition-healthy-food-isolated-icon-design-free-vector.jpg"));}
            case ALCOHOL -> {productType.setImage(new Image("https://cdn-icons-png.flaticon.com/512/920/920582.png"));}
            case HOUSEHOLD -> {productType.setImage(new Image("resources/Household.png"));}
            case SEAFOOD -> {productType.setImage(new Image("resources/Seafood.png"));}
            case DESERT -> {productType.setImage(new Image("https://cdn-icons-png.flaticon.com/512/3081/3081949.png"));}
            case BAKING -> {productType.setImage(new Image("resources/Baking.png"));}
            case NON_ALCOHOLIC -> {productType.setImage(new Image("resources/Drinks.png"));}
            case DAIRY -> {productType.setImage(new Image("https://cdn-icons-png.flaticon.com/512/3050/3050158.png"));}
            case BREAD -> {productType.setImage(new Image("https://cdn-icons-png.flaticon.com/512/4241/4241664.png"));}
            case RICE -> {productType.setImage(new Image("resources/Rice.png"));}
            case PASTA -> {productType.setImage(new Image("resources/Pasta.png"));}
            case OTHER -> {productType.setImage(new Image("resources/Other.png"));}
        }
        StackPane.setAlignment(productType, Pos.CENTER);
        productType.setFitHeight(64);
        productType.setFitWidth(64);
        switch (this.getStorageType()) {
            case NONE -> {storageType.setImage(new Image("https://cdn-icons-png.flaticon.com/512/699/699044.png"));}
            case CHILLED -> {storageType.setImage(new Image("https://icon-library.com/images/temperature-icon/temperature-icon-13.jpg"));}
            case FROZEN -> {storageType.setImage(new Image("https://cdn-icons-png.flaticon.com/512/4669/4669912.png"));}
            case CANNED_OR_PASTEURIZED -> {storageType.setImage(new Image("https://cdn-icons-png.flaticon.com/512/135/135540.png"));}
            case READY_TO_EAT -> {storageType.setImage(new Image("resources/Ready.png"));}
        }
        StackPane.setAlignment(storageType, Pos.TOP_RIGHT);
        storageType.setFitHeight(24);
        storageType.setFitWidth(24);

        visualization.setBorder(new Border(new BorderStroke(Color.BLACK,
                                                            BorderStrokeStyle.SOLID, new CornerRadii(6), BorderWidths.DEFAULT)));

        visualization.getChildren().addAll(title, productType, storageType, quantity);

        visualization.setMinWidth(100);
        visualization.setMinHeight(100);

        ImageView checkmark = new ImageView(new Image("https://freeiconshop.com/wp-content/uploads/edd/checkmark-flat.png"));
        StackPane.setAlignment(checkmark, Pos.CENTER);
        checkmark.setFitHeight(70);
        checkmark.setFitWidth(70);

        visualization.setOnMouseClicked(e -> {
            if (!checked) {
                checked = true;
                visualization.getChildren().add(checkmark);
            } else {
                checked = false;
                visualization.getChildren().remove(checkmark);
            }
        });
    }

    public OrderItemFX(GroceryItem groceryItem, GroceryListService service, int quantity) {
        super(groceryItem, service, quantity);
        init();
    }

    public OrderItemFX(String name,
                       ProductType productType,
                       ProductType.StorageType storageType,
                       boolean saved,
                       GroceryListService service) throws IllegalArgumentException {
        super(name, productType, storageType, saved, service);
        init();
    }

    public static void main(String[] args) {
        ShoppingViewController shoppingView = new ShoppingViewController();
    }

    public StackPane visualize() {
        return visualization;
    }
}