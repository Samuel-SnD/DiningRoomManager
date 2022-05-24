package com.example.diningroommanager.mapping;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Token implements Serializable {
    @SerializedName("access_token")
    String accessToken;

    @SerializedName("token_type")
    String tokenType;

    public String getAccessToken() {
        return accessToken;
    }
}
