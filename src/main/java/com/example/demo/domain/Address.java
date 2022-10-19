package com.example.demo.domain;

import lombok.Data;

@Data
public class Address {
    String addressId; // pKey and auto-incremental value
    String address; // unique value
}
