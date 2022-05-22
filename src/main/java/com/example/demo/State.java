package com.example.demo;


import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class State {
    private final byte[] sortingProfiles;
    private final byte[] groceryItems;
    //private final byte[] activeSortingProfile;

    public State(Object profiles, GroceryListService service, Map items) throws IOException {
        sortingProfiles      = convertToBytes(service.getSortingProfiles());
        //activeSortingProfile = convertToBytes(service.getActiveSortingProfile());
        groceryItems         = convertToBytes(service.getUnsortedGroceryItems());
    }

    private byte[] convertToBytes(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

    public void readState(HashSet<SortingProfile> profiles, GroceryListService service, Map items)
            throws IOException, ClassNotFoundException {
        items.clear();
        items.putAll((HashMap<ProductType, HashSet<GroceryItem>>) convertFromBytes(groceryItems));

        profiles.clear();
        profiles.addAll((HashSet<SortingProfile>) convertFromBytes(sortingProfiles));

        //service.setActiveSortingProfile(((SortingProfile) convertFromBytes(activeSortingProfile)).getName());
    }

    private Object convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream in = new ObjectInputStream(bis)) {
            return in.readObject();
        }
    }
}