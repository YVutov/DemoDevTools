package com.example.demo;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class StateManager {
    private final GroceryList groceryList;
    List<State> saves = new ArrayList<>();

    public StateManager(GroceryList groceryList) throws NullPointerException {
        if (groceryList == null) {
            throw new NullPointerException("The provided list is null");
        }
        this.groceryList = groceryList;
    }

    public State save(byte id) throws IOException {
        State save = new State(groceryList.getSortingProfiles(), groceryList, groceryList.getUnsortedGroceryItems());
        saves.set(id, save);
        return save;
    }

    public State save() throws IOException {
        State save = new State(groceryList.getSortingProfiles(), groceryList, groceryList.getUnsortedGroceryItems());
        saves.add(save);
        return save;
    }

    public void load(byte id) throws IOException, ClassNotFoundException {
        saves.get(id).readState((HashSet<SortingProfile>) groceryList.getSortingProfiles(), groceryList, groceryList.getUnsortedGroceryItems());
    }

    public void load() throws IOException, ClassNotFoundException {
        saves.get(saves.size() - 1).readState((HashSet<SortingProfile>) groceryList.getSortingProfiles(), groceryList, groceryList.getUnsortedGroceryItems());
    }
}