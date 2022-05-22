package com.example.demo;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Stack;

public class TestGroceryApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        GroceryList                                                   list = new GroceryList();

        HashSet<OrderItemFX> items = new HashSet<>();

        items.add(new OrderItemFX("Cevapici", ProductType.MEAT, ProductType.StorageType.FROZEN, false, list));
        items.add(new OrderItemFX("Wine", ProductType.ALCOHOL, ProductType.StorageType.NONE, false, list));
        items.add(new OrderItemFX("Apples", ProductType.PRODUCE, ProductType.StorageType.NONE, false, list));
        items.add(new OrderItemFX("Carrots", ProductType.PRODUCE, ProductType.StorageType.NONE, false, list));
        items.add(new OrderItemFX("Milk", ProductType.DAIRY, ProductType.StorageType.CHILLED, false, list));
        items.add(new OrderItemFX("Baguette", ProductType.BREAD, ProductType.StorageType.NONE, false, list));
        items.add(new OrderItemFX("Sandwich Bread", ProductType.BREAD, ProductType.StorageType.NONE, false, list));
        items.add(new OrderItemFX("Sweetened Pineapple", ProductType.DESERT, ProductType.StorageType.CANNED_OR_PASTEURIZED, false, list));
        items.add(new OrderItemFX("Mince", ProductType.MEAT, ProductType.StorageType.CHILLED, false, list));
        items.add(new OrderItemFX("Steak", ProductType.MEAT, ProductType.StorageType.CHILLED, false, list));

        items.forEach(i -> {
            int k = (int) ((Math.random() * 5));
            for (int z = 0; z < k; z++) {
                i.incrementQuantity();
            }
        });

        HashSet<StackPane>  childPanes = new HashSet<>();
        for (OrderItemFX item : items) {
            childPanes.add(item.visualize());
        }

        GridPane pane = new GridPane();
        pane.setHgap(22);
        pane.setVgap(22);

        int maxcol = 8;
        int col = 0;
        int row = 0;
        for (StackPane i : childPanes) {
             GridPane.setConstraints(i, col, row);
            if (col < maxcol) {
                col++;
            } else {
                col = 0;
                row++;
            }
        }


        pane.getChildren().addAll(childPanes);

        VBox vbox = new VBox();
        HBox hbox = new HBox();

        list.createSortingProfile("Kaufland");
        list.createSortingProfile("Penny");

        ComboBox<SortingProfile> box = new ComboBox<>();
        box.getItems().addAll(list.getSortingProfiles());
        box.setPrefWidth(1030);

        hbox.setSpacing(5);

        Button done = new Button();
        done.setText("Done");

        Button back = new Button();
        back.setText("Back");

        hbox.getChildren().add(box);
        hbox.getChildren().add(back);
        vbox.getChildren().add(hbox);
        VBox.setVgrow(pane, Priority.ALWAYS);
        vbox.getChildren().add(pane);
        vbox.getChildren().add(done);

        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        vbox.setMinHeight(680);
        vbox.setMinWidth(1080);
        vbox.setMaxHeight(680);
        vbox.setMaxWidth(1080);

        //Creating a scene object
        Scene scene = new Scene(vbox);

        //Setting title to the Stage
        stage.setTitle("Stack Pane Example");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }
}