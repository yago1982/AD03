package com.ymourino.ad03;

/**
 * <p>Clase utilizada para almacenar los datos de los diferentes productos de las tiendas.</p>
 * <p>No se han creado los setter para las propiedades de la clase ya que no son necesarios ahora mismo.</p>
 */
public class Producto {
    /* Campos para crear y manejar la base de datos. */
    public static final String TABLE_NAME = "productos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_DESCRIPCION = "descripcion";
    public static final String COLUMN_PRECIO = "precio";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOMBRE + " TEXT NOT NULL,"
                    + COLUMN_DESCRIPCION + " TEXT NOT NULL,"
                    + COLUMN_PRECIO + " REAL NOT NULL"
                    + ")";


    private int id;
    private String nombre;
    private String descripcion;
    private float precio;

    /**
     * <p>Constructor de la clase Producto.</p>
     * <p>Únicamente se limita a inicializar las propiedades del objeto con los datos que recibe en los parámetros.</p>
     *
     * @param identificador Identificador del producto.
     * @param descripcion Descripción del producto.
     * @param precio Precio unitario del producto.
     */
    public Producto(int identificador, String nombre, String descripcion, float precio) {
        this.id = identificador;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    /**
     * <p>Constructor de la clase Producto.</p>
     * <p>Se usa cuando se está creando un producto que no viene de una base de datos, es decir, que no tiene id.</p>
     *
     * @param nombre Nombre del producto.
     * @param descripcion Descripción del producto.
     * @param precio Precio unitario del producto.
     */
    public Producto(String nombre, String descripcion, float precio) {
        this(0, nombre, descripcion, precio);
    }

    /**
     * <p>Devuelve el identificador del producto.</p>
     *
     * @return El identificador del producto.
     */
    public int getId() {
        return id;
    }

    /**
     * <p>Devuelve el nombre del producto.</p>
     *
     * @return El nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * <p>Devuelve la descripción del producto.</p>
     *
     * @return La descripción del producto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * <p>Devuelve el precio del producto.</p>
     *
     * @return El precio del producto.
     */
    public float getPrecio() {
        return precio;
    }
}
