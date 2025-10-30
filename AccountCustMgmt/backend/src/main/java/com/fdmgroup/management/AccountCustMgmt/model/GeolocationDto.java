package com.fdmgroup.management.AccountCustMgmt.model;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.NotBlank;

@Builder
public record GeolocationDto (
        @NotBlank String name,
        @Pattern(regexp = "^[A-Z]\\d[A-Z] ?\\d[A-Z]\\d$", message="Postal code invalid")
        @NotBlank String postalCode,
        @NotBlank String streetName,
        @NotBlank String streetNo,
        @NotBlank String customerType
) {}
