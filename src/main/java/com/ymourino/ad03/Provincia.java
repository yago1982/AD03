package com.ymourino.ad03;

/**
 * <p>Clase para las provincias.</p>
 * <p>No se han creado los setter para las propiedades de la clase ya que no son necesarios ahora mismo.</p>
 */
public class Provincia {
    /* Campos para crear y manejar la base de datos. */
    public static final String TABLE_NAME = "provincias";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOMBRE + " TEXT NOT NULL UNIQUE"
                    + ")";


    private int id;
    private String nome;

    /**
     * <p>Constructor de la clase Provincia.</p>
     * <p>Únicamente se limita a inicializar las propiedades del objeto con los datos que recibe en los parámetros.</p>
     *
     * @param nome Nombre de la provincia.
     */
    public Provincia(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    /**
     * <p>Devuelve el identificador de la provincia.</p>
     *
     * @return El identificador de la provincia.
     */
    public int getId() {
        return this.id;
    }

    /**
     * <p>Devuelve el nombre de la provincia.</p>
     *
     * @return El nombre de la provincia.
     */
    public String getNome() {
        return this.nome;
    }
}
