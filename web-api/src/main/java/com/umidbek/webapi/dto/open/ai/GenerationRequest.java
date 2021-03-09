package com.umidbek.webapi.dto.open.ai;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class GenerationRequest {

    private Property property;
    private List<String> texts;
}
