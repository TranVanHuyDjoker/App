package com.example.app.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResObj {
    private  String message;
    private Object Data;

    public ResObj(String message, Object data) {
        this.message = message;
        Data = data;
    }
}
