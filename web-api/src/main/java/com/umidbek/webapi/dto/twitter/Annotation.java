package com.umidbek.webapi.dto.twitter;

import lombok.Data;

@Data
public class Annotation {
    public int start;
    public int end;
    public double probability;
    public String type;
    public String normalized_text;

}
