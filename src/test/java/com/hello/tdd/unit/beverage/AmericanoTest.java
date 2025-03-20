package com.hello.tdd.unit.beverage;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AmericanoTest {

    @Test
    void getName() {
        Americano americano = new Americano();
        assertEquals("Americano", americano.getName());
        assertThat(americano.getName()).isEqualTo("Americano");
    }
}