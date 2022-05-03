package com.example.cs468;

import java.util.ArrayList;
import java.util.List;

public class ProfileDatabase  {

    List<com.example.cs468.Profile> profiles = new ArrayList<>();

    public com.example.cs468.Profile getUserByUsername(String username){
        for (int idx = 0; idx < profiles.size(); idx++) {
            if (username == profiles.get(idx).username){
                return profiles.get(idx);
            }
        }
        return null;
    }
    public List<com.example.cs468.Profile> getUserByName(String name){
        List<com.example.cs468.Profile> found = new ArrayList<>();
        for (int idx = 0; idx < profiles.size(); idx++) {
            if (name == profiles.get(idx).username){
                found.add(profiles.get(idx));
            }
        }
        return found;
    }
    public boolean checkExistingUser(String username) {
        for (int idx = 0; idx < profiles.size(); idx++) {
            if (username == profiles.get(idx).username){
                return false;
            }
        }
        return true;
    }
    public boolean addUser(String name, String username, String password){
        if (checkExistingUser(username)){
            com.example.cs468.Profile newProf = new com.example.cs468.Profile(username, password);
            profiles.add(newProf);
            return true;
        }
        return false;
    }

    public boolean removeUser(String username, String password){
        com.example.cs468.Profile toDel = getUserByUsername(username);
        if (toDel != null){
            if (toDel.checkCred(username, password)) {
                profiles.remove(toDel);
                return true;
            }
        }
        return false;
    }


}
