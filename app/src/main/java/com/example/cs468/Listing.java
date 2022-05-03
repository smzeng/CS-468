package com.example.cs468;

import java.io.Serializable;
import java.util.List;

class Listing implements Serializable {
    String name;
    String imageFile;
    String unitCost;
    String timeFrame;
    String quantity;
    String location;
    String description;
    List<Profile> customerProfiles;

    public Listing(String name, String imageFile) {
        this.name = name;
        this.imageFile = imageFile;
    }

    public Listing(String name, String imageFile, String unitCost, String timeFrame, String quantity, String location, String description, List<Profile> customerProfiles){
        this.name = name;
        this.imageFile = imageFile;
        this.unitCost = unitCost;
        this.timeFrame = timeFrame;
        this.quantity = quantity;
        this.location = location;
        this.description = description;
        this.customerProfiles = customerProfiles;
    }

    public String getName() {
        return name;
    }

    public String getImageFile() {
        return imageFile;
    }

    public String getUnitCost() {
        return unitCost;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public List<Profile> getCustomerProfiles() {
        return customerProfiles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public void setUnitCost(String unitCost) {
        this.unitCost = unitCost;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCustomerProfiles(List<Profile> customerProfiles) {
        this.customerProfiles = customerProfiles;
    }

    public String print(){
        String toString = "";
        toString += "name is: " + name + "\n";
        toString += "unitCost is: " + unitCost + "\n";
        toString += "timeFrame is: " + timeFrame + "\n";
        toString += "quantity is: " + quantity + "\n";
        toString += "location is: " + location + "\n";
        toString += "description is: " + description + "\n";
        return toString;
    }
}
