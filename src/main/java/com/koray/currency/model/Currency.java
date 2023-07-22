package com.koray.currency.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Map;
@Data
@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "base_code")
    private String baseCode;
    @Column(name = "currency_code")
    private String currencyCode;
    @Column(name = "currency_value")
    private float currencyValue;
    @Column(name = "time_last_update_utc")
    private Date timeLastUpdateUtc;
    @Column(name = "time_next_update_utc")
    private Date timeNextUpdateUtc;

}
