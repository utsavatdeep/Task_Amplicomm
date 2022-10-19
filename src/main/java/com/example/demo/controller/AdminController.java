package com.example.demo.controller;

import com.example.demo.domain.MultipleUserJSONData;
import com.example.demo.domain.UserJSONData;
import com.example.demo.service.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    private Users users;
    @PostMapping("/addNewUserAddress")
    public String addNewUserAddress(@RequestBody UserJSONData userData) {
        if(!users.addAddress(userData)) {
            return "403 ERROR: Address-id associated with same username exists in database.";
        }
        return "200: User created successfully";
    }

    @PostMapping("/deleteUserAddress")
    public String deleteUserAddress(@RequestBody UserJSONData userData) {
        if(!users.deleteAddress(userData)) {
            return "403 ERROR: Address-id associated with same username doesn't exist in database.";
        }
        return "200: Address associated with username deleted successfully";
    }

    @PostMapping("/updateAddress")
    public String updateAddress(@RequestBody UserJSONData userData) {
        if(!users.updateAddress(userData)) {
            return "403 ERROR: Address-id associated with same username exists in database.";
        }
        return "User Create successfully";
    }
    @PostMapping("/addMultiUsers")
    public String addMultiUserAddress(@RequestBody UserJSONData[] userData) {
         int successes=0, failures=0;
        for (int i = 0; i < userData.length; i++) {
            if (!users.addAddress(userData[i])) {
                failures++;
            }
            else{
                successes++;
            };
        }
        return "200: No of Successful addresses inserted: " +successes+" ,and number of failed insertions:"+failures;
    }

    @PostMapping("/multiAddress")
    public String multiAddress(@RequestBody MultipleUserJSONData userData) {
        if(!users.addMultiAddress(userData)) {
            return "403 ERROR: Invalid JSON Request.";
        }
        return "200: Address inserted into the database";
    }

    @GetMapping("/isAddressIdAvailable")
    public String isAddressIdAvailable(@RequestBody UserJSONData userData) {
        if(users.isAddressIdAvailable(userData)) {
            return "TRUE: Your address id doesn't exist in database. Hence, it is available for fresh use.";
        }
        else {
            return "FALSE: Your address id exists in database. Call GET API '/getAddress to fetch address";
        }
    }
    @GetMapping("/getAddress")
    public String getAddress(@RequestBody UserJSONData userData) {
        String address = users.getAddress(userData);
        if(address.equalsIgnoreCase("false")) {
            return "404 ERROR: Address with given addressId not found!!";
        }
        else {
            return address;
        }
    }
}
