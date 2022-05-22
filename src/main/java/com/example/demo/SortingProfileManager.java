package com.example.demo;


import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class SortingProfileManager {
    private final HashSet<SortingProfile> profiles = new HashSet<>();
    private final GroceryList             groceryList;
    private       SortingProfile          activeProfile;

    public SortingProfileManager(GroceryList groceryList) throws NullPointerException {
        if (groceryList == null) {
            throw new NullPointerException("The provided list is null");
        }
        this.groceryList = groceryList;
    }

    public void activateSortingProfile(SortingProfile profile) throws NullPointerException {
        if (profile == null) {
            throw new NullPointerException();
        }
        activeProfile = profile;
    }

    public void activateSortingProfile(String profile) throws IllegalArgumentException {
        if (profile.isBlank()) {
            throw new IllegalArgumentException("Profile name is blank");
        }
        activateSortingProfile(get(profile));
    }

    public void createSortingProfile(String name) throws IllegalArgumentException {
        SortingProfile profile = new SortingProfile(name, groceryList);
        add(profile);
        activateSortingProfile(profile);
    }

    public void changePriority(ProductType type, int newPriority) throws IllegalArgumentException {
        //TODO check for the exceptions specified in the facade
        getActiveProfile().changePriority(type, newPriority);
    }

    public void changePriority(String profile, ProductType type, int newPriority) {
        //TODO check for the exceptions specified in the facade
        get(profile).changePriority(type, newPriority);
    }

    public SortingProfile getActiveProfile() {
        return activeProfile;
    }

    public void changePriority(ProductType.StorageType type, int newPriority) throws IllegalArgumentException {
        getActiveProfile().changePriority(type, newPriority);
    }

    public void changePriority(String profile, ProductType.StorageType type, int newPriority) {
        get(profile).changePriority(type, newPriority);
    }


    public void add(SortingProfile sortingProfile) throws NullPointerException {
        if (sortingProfile != null) {
            profiles.add(sortingProfile);
        } else {
            throw new NullPointerException();
        }
    }


    public SortingProfile addSortingProfile(String name) throws IllegalArgumentException {
        if (searchForSortingProfile(name).isPresent()) {
            throw new IllegalArgumentException();
        }
        SortingProfile profile = new SortingProfile(name, groceryList);
        profiles.add(profile);
        return profile;
    }

    public Optional<SortingProfile> searchForSortingProfile(String name) {
        SortingProfile ret = null;
        for (SortingProfile profile : profiles) {
            if (profile.toString().equals(name)) {
                ret = profile;
                break;
            }
        }
        return Optional.ofNullable(ret);
    }

    public boolean containsProfile(String name) {
        return searchForSortingProfile(name).isPresent();
    }

    public boolean searchAndRemoveSortingProfile(String name) {
        return searchForAndRemoveSortingProfile(name);
    }

    /**
     * Searches for a sorting profile by name and, if found, removes it from the manager, returning "true".
     *
     * @param name The name of the profile to be searched for.
     * @return Was it removed?
     */
    public boolean searchForAndRemoveSortingProfile(String name) {
        Optional<SortingProfile> sortingProfile = searchForSortingProfile(name);
        if (sortingProfile.isPresent()) {
            return remove(sortingProfile.get());
        } else {
            return false;
        }
    }

    public boolean remove(SortingProfile profile) {
        return profiles.remove(profile);
    }

    public boolean remove(String profileName) throws IllegalArgumentException {
        if (profileName.isBlank()) {
            throw new IllegalArgumentException("The name is blank");
        }
        SortingProfile profile = get(profileName);
        return profiles.remove(profile);
    }

    public SortingProfile get(String name) throws IllegalArgumentException {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name is blank");
        }
        AtomicReference<SortingProfile> ret = new AtomicReference<>();
        profiles.forEach(i -> {
            if (i.getName().equals(name)) {
                ret.set(i);
            }
        });
        if (ret.get() != null) {
            return ret.get();
        } else {
            throw new IllegalArgumentException("Profile with the name " + name + " does not exist.");
        }
    }

    public void setSortingProfiles(HashSet<SortingProfile> in) {
        profiles.clear();
        profiles.addAll(in);
    }

    public void copySortingProfile(String copiedProfileName, String newProfileName)
            throws IllegalArgumentException, NullPointerException {
        SortingProfile copiedProfile = get(copiedProfileName);
        SortingProfile newProfile    = get(newProfileName);
        newProfile.setName(copiedProfile.getName());
        newProfile.setProductPriority(copiedProfile.getProductPriority());
        newProfile.setStoragePriority(copiedProfile.getStoragePriority());
    }

    public HashSet<SortingProfile> getSortingProfiles() {
        return new HashSet<>(profiles);
    }
}