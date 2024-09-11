package com.bank.bank.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class ServicesEntity {
    @Id
    @NotBlank(message = "Code must not be blank")
    @Column(name = "afm", unique = true, nullable = false)
    private String code;
    @NotBlank
    @NotNull(message = "Firstname must not be null")
    private String name;

    public ServicesEntity() {}

    public ServicesEntity(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public @NotBlank(message = "Code must not be blank") String getCode() {
        return code;
    }

    public void setCode(@NotBlank(message = "Code must not be blank") String code) {
        this.code = code;
    }

    public @NotBlank @NotNull(message = "Firstname must not be null") String getName() {
        return name;
    }

    public void setName(@NotBlank @NotNull(message = "Firstname must not be null") String name) {
        this.name = name;
    }
}
