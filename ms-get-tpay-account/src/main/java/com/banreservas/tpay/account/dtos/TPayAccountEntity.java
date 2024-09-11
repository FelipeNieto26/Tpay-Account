package com.banreservas.tpay.account.dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TPayAccountEntity {

    @Id
    private Integer id;
}
