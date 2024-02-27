package com.mindata.superHero.rest.utils;

import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class ResponseUtil {

    public static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> result) {
        if (result.isPresent()) {
            return ResponseEntity.ok().body(result.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}