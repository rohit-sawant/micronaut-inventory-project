package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Serdeable.Serializable
@Serdeable.Deserializable
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CoffeeMugType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;

//    @JsonIgnore
//    @OneToMany(mappedBy = "coffeeMugType", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    private List<CoffeeMug> coffeeMugs;

    public CoffeeMugType(String name) {
        this.name = name;
    }
}
