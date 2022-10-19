package com.example.demo.domain;

import lombok.Data;

@Data
public class UserData {
    String userName; //NOT NULL but can have duplicate entries
    String addressId; //this is a fKey which references pKey of Address
}
