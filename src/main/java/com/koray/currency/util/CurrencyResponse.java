package com.koray.currency.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyResponse {

    private String base_code;

    private Date time_last_update_utc;

    private Date time_next_update_utc;

    private Map<String,Float> conversion_rates;
}
