package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({ "username", "addressIds", "addresses" })
public class MultipleUserJSONData {
    private String username;
    private String[] addressIds;
    private String[] addresses;
}
