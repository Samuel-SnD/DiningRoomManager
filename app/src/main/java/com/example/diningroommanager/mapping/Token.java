package com.example.diningroommanager.mapping;

import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("access_token")
    String accessToken;

    @SerializedName("token_type")
    String tokenType;
}
