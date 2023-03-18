package com.study.springbatch.config.part6_chunk_itemReader.v8;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class Customer_p6v8 {

    @Id
    @GeneratedValue
    private Long id;
    private String firstname;
    private String lastname;
    private int age;

    @OneToOne(mappedBy = "customer")
    private Address address;
}
