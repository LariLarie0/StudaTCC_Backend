package com.StudaTCC.demo.material;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ref {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    public Ref(Material material, String url) {
        this.url = url;
        this.material = material;
    }

    public Ref(String url){
        this.url = url;
    }
}
