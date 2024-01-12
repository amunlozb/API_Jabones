package com.dwes.api.entidades;

import java.util.ArrayList;
import java.util.List;

import com.dwes.api.entidades.enumerados.TipoDePiel;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "jabones")
public class Jabon extends Producto {
    private String aroma;

    @Enumerated(EnumType.STRING)
    private TipoDePiel tipoDePiel;

    @ElementCollection
    @CollectionTable(name = "jabon_ingredientes", joinColumns = @JoinColumn(name = "jabon_id"))
    private List<Ingrediente> ingredientes = new ArrayList<>();

	public void setAroma(String aroma) {
		this.aroma = aroma;
	}

	public void setTipoDePiel(TipoDePiel tipoDePiel) {
		this.tipoDePiel = tipoDePiel;
	}

	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}
	
	
    
    
}
