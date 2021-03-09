package com.umidbek.webapi.dto.open.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Choice {

    @JsonProperty(value = "text")
    public String text;

    @JsonProperty(value = "index")
    public int index;

    @JsonProperty(value = "logprobs")
    public Object logprobs;

    @JsonProperty(value = "finish_reasion")
    public String finishReason;
}
