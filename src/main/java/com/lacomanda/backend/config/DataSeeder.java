package com.lacomanda.backend.config;

import com.lacomanda.backend.entity.*;
import com.lacomanda.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private static final String BASE_IMG = "http://localhost:8090/imagenes/";

    private final CategoriaRepository categoriaRepository;
    private final AlergenoRepository alergenoRepository;
    private final ProductoRepository productoRepository;
    private final MesaRepository mesaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (categoriaRepository.count() > 0) {
            return;
        }

        // ===================== CATEGORÍAS (10) =====================
        Categoria hamburguesas = crearCategoria("Hamburguesas", "Hamburgueses", "Burgers", 1, "categorias/hamburguesas.jpg");
        Categoria italiana = crearCategoria("Italiana", "Italiana", "Italian", 2, "categorias/italiana.jpg");
        Categoria japonesa = crearCategoria("Japonesa", "Japonesa", "Japanese", 3, "categorias/japonesa.jpg");
        Categoria mexicana = crearCategoria("Mexicana", "Mexicana", "Mexican", 4, "categorias/mexicana.jpg");
        Categoria pizzas = crearCategoria("Pizzas", "Pizzes", "Pizzas", 5, "categorias/pizzas.jpg");
        Categoria entrantes = crearCategoria("Entrantes", "Entrants", "Starters", 6, "categorias/entrantes.jpg");
        Categoria ensaladas = crearCategoria("Ensaladas", "Amanides", "Salads", 7, "categorias/ensaladas.jpg");
        Categoria postres = crearCategoria("Postres", "Postres", "Desserts", 8, "categorias/postres.jpg");
        Categoria bebidas = crearCategoria("Bebidas", "Begudes", "Drinks", 9, "categorias/bebidas.jpg");
        Categoria vinos = crearCategoria("Vinos", "Vins", "Wines", 10, "categorias/vinos.jpg");

        // ===================== ALÉRGENOS (14, oficiales UE completos) =====================
        Alergeno gluten = crearAlergeno("Gluten", "Gluten", "Gluten", "gluten.svg");
        Alergeno lacteos = crearAlergeno("Lácteos", "Lactis", "Dairy", "lacteos.svg");
        Alergeno huevo = crearAlergeno("Huevo", "Ou", "Egg", "huevo.svg");
        Alergeno pescado = crearAlergeno("Pescado", "Peix", "Fish", "pescado.svg");
        Alergeno crustaceos = crearAlergeno("Crustáceos", "Crustacis", "Crustaceans", "crustaceos.svg");
        Alergeno frutosSecos = crearAlergeno("Frutos secos", "Fruits secs", "Nuts", "frutos-secos.svg");
        Alergeno soja = crearAlergeno("Soja", "Soja", "Soy", "soja.svg");
        Alergeno mostaza = crearAlergeno("Mostaza", "Mostassa", "Mustard", "mostaza.svg");
        Alergeno apio = crearAlergeno("Apio", "Api", "Celery", "apio.svg");
        Alergeno sulfitos = crearAlergeno("Sulfitos", "Sulfits", "Sulphites", "sulfitos.svg");
        Alergeno sesamo = crearAlergeno("Sésamo", "Sèsam", "Sesame", "sesamo.svg");
        Alergeno cacahuetes = crearAlergeno("Cacahuetes", "Cacauets", "Peanuts", "cacahuetes.svg");
        Alergeno altramuces = crearAlergeno("Altramuces", "Tramussos", "Lupin", "altramuces.svg");
        Alergeno moluscos = crearAlergeno("Moluscos", "Mol·luscos", "Molluscs", "moluscos.svg");

        // ===================== PRODUCTOS =====================

        // --- Hamburguesas (con extras de prueba) ---
        Producto hamburguesaClasica = crearProducto(hamburguesas, "Hamburguesa clásica", "Hamburguesa clàssica", "Classic burger",
                "Carne de vacuno, queso cheddar, lechuga y tomate", "Carn de vacum, formatge cheddar, enciam i tomàquet",
                "Beef patty, cheddar cheese, lettuce and tomato", "9.50", "productos/hamburguesa-clasica.jpg",
                Set.of(gluten, lacteos), List.of("Pan de brioche", "Carne de vacuno", "Queso cheddar", "Lechuga", "Tomate"));
        agregarExtras(hamburguesaClasica, List.of(
                new Object[]{"Bacon extra", "1.50"},
                new Object[]{"Queso extra", "1.00"},
                new Object[]{"Huevo frito", "1.20"}
        ));

        Producto hamburguesaBBQ = crearProducto(hamburguesas, "Hamburguesa BBQ", "Hamburguesa BBQ", "BBQ burger",
                "Con bacon, cebolla crujiente y salsa barbacoa", "Amb bacon, ceba cruixent i salsa barbacoa",
                "With bacon, crispy onion and BBQ sauce", "11.00", "productos/hamburguesa-bbq.jpg",
                Set.of(gluten, lacteos), List.of("Pan de brioche", "Carne de vacuno", "Bacon", "Cebolla crujiente", "Salsa barbacoa"));
        agregarExtras(hamburguesaBBQ, List.of(
                new Object[]{"Queso extra", "1.00"},
                new Object[]{"Aguacate", "1.80"}
        ));

        Producto hamburguesaVegetal = crearProducto(hamburguesas, "Hamburguesa vegetal", "Hamburguesa vegetal", "Veggie burger",
                "Base de garbanzos y verduras asadas", "Base de cigrons i verdures rostides",
                "Chickpea and roasted vegetable patty", "9.00", "productos/hamburguesa-vegetal.jpg",
                Set.of(gluten), List.of("Pan integral", "Garbanzos", "Pimiento asado", "Calabacín", "Lechuga"));
        agregarExtras(hamburguesaVegetal, List.of(
                new Object[]{"Queso vegano", "1.30"},
                new Object[]{"Aguacate", "1.80"}
        ));

        // --- Italiana ---
        crearProducto(italiana, "Spaghetti carbonara", "Espaguetis carbonara", "Spaghetti carbonara",
                "Con huevo, panceta y queso parmesano", "Amb ou, panxeta i formatge parmesà",
                "With egg, pancetta and parmesan cheese", "10.50", "productos/carbonara.jpg",
                Set.of(gluten, huevo, lacteos), List.of("Espaguetis", "Huevo", "Panceta", "Queso parmesano", "Pimienta negra"));

        crearProducto(italiana, "Risotto de setas", "Risotto de bolets", "Mushroom risotto",
                "Arroz cremoso con setas variadas", "Arròs cremós amb bolets variats",
                "Creamy rice with assorted mushrooms", "11.50", "productos/risotto-setas.jpg",
                Set.of(lacteos), List.of("Arroz arborio", "Setas variadas", "Caldo de verduras", "Queso parmesano", "Mantequilla"));

        crearProducto(italiana, "Lasaña boloñesa", "Lasanya bolonyesa", "Beef lasagna",
                "Capas de pasta, carne y bechamel gratinada", "Capes de pasta, carn i beixamel gratinada",
                "Layers of pasta, beef and gratinated bechamel", "11.00", "productos/lasagna.jpg",
                Set.of(gluten, lacteos), List.of("Pasta", "Carne picada", "Tomate", "Bechamel", "Queso rallado"));

        // --- Japonesa ---
        crearProducto(japonesa, "Selección de nigiris", "Selecció de nigiris", "Nigiri selection",
                "8 piezas variadas de salmón, atún y langostino", "8 peces variades de salmó, tonyina i llagostí",
                "8 assorted pieces of salmon, tuna and shrimp", "13.50", "productos/nigiris.jpg",
                Set.of(pescado, crustaceos), List.of("Arroz de sushi", "Salmón", "Atún", "Langostino", "Alga nori"));

        crearProducto(japonesa, "Ramen tonkotsu", "Ramen tonkotsu", "Tonkotsu ramen",
                "Caldo cremoso de cerdo con fideos y huevo marinado", "Brou cremós de porc amb fideus i ou marinat",
                "Creamy pork broth with noodles and marinated egg", "12.50", "productos/ramen.jpg",
                Set.of(gluten, huevo, soja), List.of("Fideos ramen", "Caldo de cerdo", "Huevo marinado", "Cebolleta", "Alga nori"));

        crearProducto(japonesa, "Gyozas de cerdo", "Gyozas de porc", "Pork gyozas",
                "6 unidades a la plancha con salsa de soja", "6 unitats a la planxa amb salsa de soja",
                "6 pan-fried pieces with soy sauce", "7.50", "productos/gyozas.jpg",
                Set.of(gluten, soja), List.of("Masa de gyoza", "Carne de cerdo", "Col", "Jengibre", "Salsa de soja"));

        // --- Mexicana ---
        crearProducto(mexicana, "Tacos al pastor", "Tacos al pastor", "Al pastor tacos",
                "3 tacos de cerdo marinado con piña", "3 tacos de porc marinat amb pinya",
                "3 marinated pork tacos with pineapple", "9.50", "productos/tacos-pastor.jpg",
                Set.of(), List.of("Tortilla de maíz", "Cerdo marinado", "Piña", "Cilantro", "Cebolla"));

        crearProducto(mexicana, "Guacamole con nachos", "Guacamole amb nachos", "Guacamole with nachos",
                "Aguacate fresco con totopos crujientes", "Alvocat fresc amb totopos cruixents",
                "Fresh avocado with crispy corn chips", "7.00", "productos/guacamole.jpg",
                Set.of(), List.of("Aguacate", "Tomate", "Cebolla", "Lima", "Totopos de maíz"));

        // --- Pizzas ---
        crearProducto(pizzas, "Pizza margarita", "Pizza margarita", "Margherita pizza",
                "Tomate, mozzarella fresca y albahaca", "Tomàquet, mozzarella fresca i alfàbrega",
                "Tomato, fresh mozzarella and basil", "9.00", "productos/pizza-margarita.jpg",
                Set.of(gluten, lacteos), List.of("Masa de pizza", "Tomate", "Mozzarella", "Albahaca", "Aceite de oliva"));

        crearProducto(pizzas, "Pizza cuatro quesos", "Pizza quatre formatges", "Four cheese pizza",
                "Mezcla de mozzarella, gorgonzola, parmesano y emmental", "Barreja de mozzarella, gorgonzola, parmesà i emmental",
                "Blend of mozzarella, gorgonzola, parmesan and emmental", "11.00", "productos/pizza-4quesos.jpg",
                Set.of(gluten, lacteos), List.of("Masa de pizza", "Mozzarella", "Gorgonzola", "Parmesano", "Emmental"));

        // --- Entrantes ---
        crearProducto(entrantes, "Ensaladilla rusa", "Ensaladilla russa", "Russian salad",
                "Con mahonesa casera", "Amb maonesa casolana", "With homemade mayonnaise",
                "6.50", "productos/ensaladilla-rusa.jpg", Set.of(huevo, lacteos), List.of("Patata", "Zanahoria", "Atún", "Huevo", "Mahonesa"));

        crearProducto(entrantes, "Croquetas de jamón", "Croquetes de pernil", "Ham croquettes",
                "8 unidades caseras", "8 unitats casolanes", "8 homemade pieces",
                "7.00", "productos/croquetas.jpg", Set.of(gluten, lacteos), List.of("Jamón serrano", "Bechamel", "Pan rallado", "Huevo", "Harina"));

        crearProducto(entrantes, "Patatas bravas", "Patates braves", "Bravas potatoes",
                "Con salsa brava y alioli", "Amb salsa brava i allioli", "With brava sauce and alioli",
                "6.00", "productos/patatas-bravas.jpg", Set.of(huevo), List.of("Patata", "Salsa brava", "Alioli", "Pimentón"));

        // --- Ensaladas ---
        crearProducto(ensaladas, "Ensalada César", "Amanida Cèsar", "Caesar salad",
                "Con pollo, parmesano y picatostes", "Amb pollastre, parmesà i torradetes",
                "With chicken, parmesan and croutons", "8.50", "productos/cesar.jpg",
                Set.of(gluten, lacteos, huevo), List.of("Lechuga romana", "Pollo", "Parmesano", "Picatostes", "Salsa César"));

        crearProducto(ensaladas, "Ensalada griega", "Amanida grega", "Greek salad",
                "Con queso feta, aceitunas y pepino", "Amb formatge feta, olives i cogombre",
                "With feta cheese, olives and cucumber", "8.00", "productos/griega.jpg",
                Set.of(lacteos), List.of("Tomate", "Pepino", "Queso feta", "Aceitunas negras", "Cebolla roja"));

        // --- Postres ---
        crearProducto(postres, "Tarta de queso", "Tarta de formatge", "Cheesecake",
                "Con coulis de frutos rojos", "Amb coulis de fruits vermells", "With red berry coulis",
                "5.00", "productos/tarta-queso.jpg", Set.of(gluten, lacteos), List.of("Queso crema", "Galleta", "Mantequilla", "Frutos rojos"));

        crearProducto(postres, "Tiramisú", "Tiramisú", "Tiramisu",
                "Receta tradicional italiana", "Recepta tradicional italiana", "Traditional Italian recipe",
                "5.50", "productos/tiramisu.jpg", Set.of(gluten, lacteos, huevo), List.of("Bizcochos de soletilla", "Café", "Mascarpone", "Cacao", "Huevo"));

        crearProducto(postres, "Helado artesano", "Gelat artesà", "Artisan ice cream",
                "2 bolas a elegir sabor", "2 boles a triar sabor", "2 scoops, choice of flavor",
                "4.50", "productos/helado.jpg", Set.of(lacteos), List.of("Leche", "Nata", "Azúcar", "Saborizante natural"));

        // --- Bebidas ---
        crearProducto(bebidas, "Agua mineral", "Aigua mineral", "Mineral water",
                "50cl", "50cl", "50cl", "2.00", "productos/agua.jpg", Set.of(), List.of("Agua mineral"));

        crearProducto(bebidas, "Refresco de cola", "Refresc de cola", "Cola soft drink",
                "33cl", "33cl", "33cl", "2.50", "productos/cola.jpg", Set.of(), List.of("Agua carbonatada", "Azúcar", "Cafeína"));

        crearProducto(bebidas, "Cerveza artesana", "Cervesa artesana", "Craft beer",
                "33cl, elaboración local", "33cl, elaboració local", "33cl, locally brewed",
                "3.50", "productos/cerveza.jpg", Set.of(gluten), List.of("Malta de cebada", "Lúpulo", "Levadura", "Agua"));

        // --- Vinos ---
        crearProducto(vinos, "Vino tinto crianza", "Vi negre criança", "Aged red wine",
                "Copa, D.O. Rioja", "Copa, D.O. Rioja", "Glass, D.O. Rioja",
                "4.00", "productos/vino-tinto.jpg", Set.of(sulfitos), List.of("Uva tempranillo"));

        crearProducto(vinos, "Vino blanco Albariño", "Vi blanc Albariño", "Albariño white wine",
                "Copa, D.O. Rías Baixas", "Copa, D.O. Rías Baixas", "Glass, D.O. Rías Baixas",
                "4.50", "productos/vino-blanco.jpg", Set.of(sulfitos), List.of("Uva albariño"));

        // ===================== MESAS (10) =====================
        int[] capacidades = {2, 2, 2, 4, 4, 4, 6, 6, 10, 10};
        for (int i = 0; i < capacidades.length; i++) {
            Mesa mesa = new Mesa();
            mesa.setNumero(i + 1);
            mesa.setCapacidad(capacidades[i]);
            mesa.setQrCode(java.util.UUID.randomUUID().toString());
            mesaRepository.save(mesa);
        }

        // ===================== USUARIOS (10) =====================
        crearUsuario("Administrador Principal", "admin", "admin123", Rol.ADMIN);
        crearUsuario("Laura Gómez", "gerente", "gerente123", Rol.ADMIN);
        crearUsuario("Marcos Ruiz", "camarero1", "camarero1123", Rol.CAMARERO);
        crearUsuario("Ana Torres", "camarero2", "camarero2123", Rol.CAMARERO);
        crearUsuario("Javier López", "camarero3", "camarero3123", Rol.CAMARERO);
        crearUsuario("Sara Díaz", "camarero4", "camarero4123", Rol.CAMARERO);
        crearUsuario("Pablo Martín", "camarero5", "camarero5123", Rol.CAMARERO);
        crearUsuario("Lucía Fernández", "camarero6", "camarero6123", Rol.CAMARERO);
        crearUsuario("Diego Sánchez", "camarero7", "camarero7123", Rol.CAMARERO);
        crearUsuario("Elena Morales", "camarero8", "camarero8123", Rol.CAMARERO);

        System.out.println(">>> Datos de prueba insertados correctamente <<<");
    }

    private Categoria crearCategoria(String es, String val, String en, int orden, String rutaFoto) {
        Categoria c = new Categoria();
        c.setNombreEs(es);
        c.setNombreVal(val);
        c.setNombreEn(en);
        c.setOrden(orden);
        c.setFoto(BASE_IMG + rutaFoto);
        return categoriaRepository.save(c);
    }

    private Alergeno crearAlergeno(String es, String val, String en, String icono) {
        Alergeno a = new Alergeno();
        a.setNombreEs(es);
        a.setNombreVal(val);
        a.setNombreEn(en);
        a.setIcono(BASE_IMG + "alergenos/" + icono);
        return alergenoRepository.save(a);
    }

    private Producto crearProducto(Categoria categoria, String nombreEs, String nombreVal, String nombreEn,
                                   String descEs, String descVal, String descEn,
                                   String precio, String rutaFoto, Set<Alergeno> alergenos, List<String> nombresIngredientes) {
        Producto p = new Producto();
        p.setCategoria(categoria);
        p.setNombreEs(nombreEs);
        p.setNombreVal(nombreVal);
        p.setNombreEn(nombreEn);
        p.setDescripcionEs(descEs);
        p.setDescripcionVal(descVal);
        p.setDescripcionEn(descEn);
        p.setPrecio(new BigDecimal(precio));
        p.setFoto(BASE_IMG + rutaFoto);
        p.setDisponible(true);
        p.setAlergenos(alergenos);

        List<Ingrediente> ingredientes = nombresIngredientes.stream().map(nombre -> {
            Ingrediente ing = new Ingrediente();
            ing.setNombre(nombre);
            ing.setProducto(p);
            return ing;
        }).toList();
        p.setIngredientes(ingredientes);

        return productoRepository.save(p);
    }

    private void agregarExtras(Producto producto, List<Object[]> extrasData) {
        for (Object[] datos : extrasData) {
            Extra extra = new Extra();
            extra.setProducto(producto);
            extra.setNombre((String) datos[0]);
            extra.setPrecio(new BigDecimal((String) datos[1]));
            producto.getExtras().add(extra);
        }
        productoRepository.save(producto);
    }

    private void crearUsuario(String nombre, String username, String passwordPlano, Rol rol) {
        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(passwordPlano));
        u.setRol(rol);
        usuarioRepository.save(u);
    }
}