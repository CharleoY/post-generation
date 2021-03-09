package com.umidbek.webapi.dto.open.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Property {

    @JsonProperty("temperature")
    private Double temperature;

    @JsonProperty("top_p")
    @SerializedName(value = "top_p")
    private int top;

    @JsonProperty("max_tokens")
    @SerializedName(value = "max_tokens")
    private int maxTokens;

    @JsonProperty("presence_penalty")
    @SerializedName(value = "presence_penalty")
    private int presencePenalty;

    @JsonProperty("frequency_penalty")
    @SerializedName(value = "frequency_penalty")
    private int frequencyPenalty;

    private String prompt;
}
