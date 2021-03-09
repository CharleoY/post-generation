package com.umidbek.webapi.dto.twitter;

import lombok.Data;

import java.util.List;

@Data
public class Root {
    public List<Datum> data;
    public Meta meta;
}
