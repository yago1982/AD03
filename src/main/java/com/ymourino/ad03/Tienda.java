package com.ymourino.ad03;

/**
 * <p>Clase para gestionar los empleados y productos de una tienda.</p>
 */
public class Tienda {
    /* Campos para crear y manejar la base de datos. */
    public static final String TABLE_NAME = "tiendas";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_CIUDAD = "ciudad";
    public static final String COLUMN_PROVINCIA = "provincia";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOMBRE + " TEXT NOT NULL UNIQUE,"
                    + COLUMN_CIUDAD + " TEXT NOT NULL,"
                    + COLUMN_PROVINCIA + " INTEGER NOT NULL,"
                    + "FOREIGN KEY (" + COLUMN_PROVINCIA + ") REFERENCES " + Provincia.TABLE_NAME + "(" + Provincia.COLUMN_ID + ") ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")";


    private int id;
    private String nombre;
    private String ciudad;
    private int provincia;

    /**
     * <p>Constructor de la clase Tienda.</p>
     * <p>Únicamente inicializa las propiedades de la clase con los datos que recibe en los parámetros.</p>
     *
     * @param id Identificador de la tienda.
     * @param nombre Nombre de la tienda.
     * @param ciudad Ciudad de la tienda.
     * @param provincia Identificador de la provincia de la tienda.
     */
    public Tienda(int id, String nombre, String ciudad, int provincia) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.provincia = provincia;
    }

    /**
     * <p>Constructor de la clase Tienda.</p>
     * <p>Se usa cuando se está creando una tienda que no viene de una base de datos, es decir, que no tiene id.</p>
     *
     * @param nombre Nombre de la tienda.
     * @param ciudad Ciudad donde está ubicada la tienda.
     * @param provincia Identificador de la provincia donde está ubicada la tienda.
     */
    public Tienda(String nombre, String ciudad, int provincia) {
        this(0, nombre, ciudad, provincia);
    }

    /**
     * <p>Devuelve el identificador de la tienda.</p>
     *
     * @return El identificador de la tienda.
     */
    public int getId() {
        return this.id;
    }

    /**
     * <p>Devuelve el nombre de la tienda.</p>
     *
     * @return El nombre de la tienda.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * <p>Devuelve la ciudad de la tienda.</p>
     *
     * @return La ciudad de la tienda.
     */
    public String getCiudad() {
        return this.ciudad;
    }

    /**
     * <p>Devuelve el identificador de la provincia de la tienda.</p>
     *
     * @return El identificador de la provincia de la tienda.
     */
    public int getProvincia() {
        return this.provincia;
    }
}
