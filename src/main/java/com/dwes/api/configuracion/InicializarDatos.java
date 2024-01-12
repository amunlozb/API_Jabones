package com.dwes.api.configuracion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dwes.api.entidades.Categoria;
import com.dwes.api.repositorios.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dwes.api.entidades.Ingrediente;
import com.dwes.api.entidades.Jabon;
import com.dwes.api.entidades.Producto;
import com.dwes.api.entidades.enumerados.TipoDePiel;
import com.dwes.api.repositorios.JabonRepository;
import com.dwes.api.repositorios.ProductoRepository;
import com.github.javafaker.Faker;

@Component
public class InicializarDatos implements CommandLineRunner {


    @Autowired
    private JabonRepository jabonRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
    
    Faker faker = new Faker();
    
    
	@Override
	public void run(String... args) throws Exception {

        for (int i = 0; i < 100; i++) {

            Jabon jabon = new Jabon();
            jabon.setNombre(faker.commerce().productName());
            jabon.setPrecio(Double.parseDouble(faker.commerce().price().replaceAll("[^\\d.]+", "")));
            jabon.setDescripcion(faker.lorem().sentence());
            jabon.setStock(faker.number().numberBetween(0, 100));
            jabon.setImagenUrl(generarUrlImagenAleatoria());
            jabon.setAroma(faker.lorem().word());
            jabon.setTipoDePiel(TipoDePiel.values()[faker.random().nextInt(TipoDePiel.values().length)]);

			List<Ingrediente> ingredientes = new ArrayList<>();
			List<Categoria> categorias = new ArrayList<>();

            for (int j = 0; j < faker.number().numberBetween(1, 5); j++) {
                ingredientes.add(generarIngredienteFicticio(faker));
				categorias.add(generarCategoriaFicticia(faker));
            }
            jabon.setIngredientes(ingredientes);
			jabon.setCategorias(categorias);

            jabonRepository.save(jabon);
        }
	}

	private Categoria generarCategoriaFicticia(Faker faker) {
		Categoria categoria = new Categoria();
		String[] nombres = {"jabones", "geles", "aceites", "perfumes", "colonias", "comida"};
		String[] descripciones = {"descripcion 1", "descripcion 2", "descripcion 3", "descripcion 4"};

		List<Producto> productos = new ArrayList<>();

		// Elegir aleatoriamente un nombre y una descripcion
		String nombre = nombres[faker.random().nextInt(nombres.length)];
		String descripcion = descripciones[faker.random().nextInt(descripciones.length)];

		categoria.setNombre(nombre);
		categoria.setDescripcion(descripcion);
		categoria.setProductos(new ArrayList<Producto>());

		categoriaRepository.save(categoria);

		System.out.println(categoria.toString());

		return categoria;
	}

	private Ingrediente generarIngredienteFicticio(Faker faker) {
	    Ingrediente ingrediente = new Ingrediente();
	    String[] elementos = {"jabón de glicerina", "gel aloe vera", "miel", "aceite de oliva", "ralladura de limón", "aceite esencial"};
	    String[] cantidades = {"2 pastillas", "1 taza", "4 cucharadas", "5 cucharadas", "1 cucharada"};

	    // Elegir aleatoriamente un elemento y una cantidad
	    String elemento = elementos[faker.random().nextInt(elementos.length)];
	    String cantidad = cantidades[faker.random().nextInt(cantidades.length)];

	    // Si el elemento es "gel aloe vera" o "aceite de oliva", añadir la medida
	    if (elemento.equals("gel aloe vera")) {
	        cantidad += " (200 g)";
	    } else if (elemento.equals("aceite de oliva")) {
	        cantidad += " (100 ml)";
	    }

	    ingrediente.setElemento(elemento);
	    ingrediente.setCantidad(cantidad);

	    return ingrediente;
	}
	
	 private String generarUrlImagenAleatoria() {
	        return "https://e00-telva.uecdn.es/assets/multimedia/imagenes/2019/11/08/15732087888279.jpg";
	    }
}
