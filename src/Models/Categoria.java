package Models;

public enum Categoria {
    CELULARES(1, "Celulares"),
    COMPUTACION(2, "Computacion"),
    TELEVISORES(3, "Televisores"),
    AUDIO(4, "Audio"),
    LINEA_BLANCA(5, "Linea Blanca"),
    PEQUEÑOS_ELECTRODOMESTICOS(6, "Pequenos Electrodomesticos"),
    CLIMATIZACION(7, "Climatizacion"),
    CUIDADO_PERSONAL(8, "Cuidado Personal"),
    CONSOLAS(9, "Consolas"),
    HERRAMIENTAS_JARDINERIA(10, "Herramientas y jardineria");

    private final int id;
    private final String nombre;

    private Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public static Categoria buscarPorId(int id) {
        for (Categoria cat : values()) {
            if (cat.id == id) {
                return cat;
            }
        }
        throw new IllegalArgumentException("ID de categoria no valido: " + id);
    }
    
        public static String getCategoria(int id) {
        for (Categoria cat : values()) {
            if (cat.id == id) {
                return cat.getNombre();
            }
        }
        throw new IllegalArgumentException("ID de categoria no valido: " + id);
    }

    public static void mostrarOpciones() {
        System.out.println("Categorias disponibles:");
        for (Categoria cat : values()) {
            System.out.println(cat.id + ". " + cat.nombre);
        }
    }
}
