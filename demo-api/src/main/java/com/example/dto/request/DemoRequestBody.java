package com.example.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
@Data
@EqualsAndHashCode(callSuper = false)
public class DemoRequestBody implements Serializable {
    private String demo;
}
