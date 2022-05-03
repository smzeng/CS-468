package com.example.cs468;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Profile implements Serializable {
    String name;
    String username;
    String password;
    String description;
    String contact;
    String area;
    int votes = 0;
    int score = 0;
    List<Listing> curListings = new ArrayList<>();
    List<Listing> pastListings = new ArrayList<>();

    // constructor
    public Profile(String username, String password){
        this.username = username;
        this.password = password;
    }

    // rating
    public void addVote(int vote){
        this.score += vote;
        this.votes += 1;
    }
    public float getScore(){
        if (votes == 0){
            return -1;
        }
        else{
            return score/votes;
        }

    }
    // login
    public boolean checkCred(String username, String password) {
        if (username.equals(this.username) && password.equals(this.password)){
            return true;
        }
        return false;
    }
    public boolean changePassword(String username, String oldPassword, String newPassword){
        if (checkCred(username, oldPassword)) {
            this.password = newPassword;
            return true;
        }
        return false;
    }
    public boolean changeUsername(String username, String oldPassword, String newUsername){
        if (checkCred(username, oldPassword)) {
            this.username = newUsername;
            return true;
        }
        return false;
    }

    //list manipulation
    public boolean retire(int idx){
        Listing toAdd = curListings.remove(idx);
        if (toAdd !=  null) {
            return pastListings.add(toAdd);
        }
        return false;
    }
}