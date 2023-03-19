package com.study.springbatch.config.part7_chunk_itemWriter.v3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private long id;
    private String firstName;
    private String lastName;
    private String birthdate;
}
