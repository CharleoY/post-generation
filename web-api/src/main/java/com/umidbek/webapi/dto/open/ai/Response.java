package com.umidbek.webapi.dto.open.ai;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Response {

    public String id;
    public String object;
    public int created;
    public String model;
    public List<Choice> choices;
}
