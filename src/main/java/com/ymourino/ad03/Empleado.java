package com.ymourino.ad03;

/**
 * <p>Clase utilizada para almacenar los datos de los diferentes empleados de las tiendas.</p>
 * <p>No se han creado los setter para las propiedades de la clase ya que no son necesarios ahora mismo.</p>
 */
public class Empleado {
    /* Campos para crear y manejar la base de datos. */
    public static final String TABLE_NAME = "empleados";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_APELLIDOS = "apellidos";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOMBRE + " TEXT NOT NULL,"
                    + COLUMN_APELLIDOS + " TEXT NOT NULL"
                    + ")";


    private int id;
    private String nombre;
    private String apellidos;

    /**
     * <p>Constructor de la clase Empleado.</p>
     * <p>Únicamente se limita a inicializar las propiedades del objeto con los datos que recibe en los parámetros.</p>
     *
     * @param id Identificador del empleado.
     * @param nombre Nombre del empleado.
     * @param apellidos Apellidos del empleado.
     */
    public Empleado(int id, String nombre, String apellidos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    /**
     * <p>Constructor de la clase Empleado.</p>
     * <p>Se usa cuando se está creando un empleado que no viene de una base de datos, es decir, que no tiene id.</p>
     *
     * @param nombre Nombre del empleado.
     * @param apellidos Apellidos del empleado.
     */
    public Empleado(String nombre, String apellidos) {
        this(0, nombre, apellidos);
    }

    /**
     * <p>Devuelve el identificador del empleado.</p>
     *
     * @return El identificador del empleado.
     */
    public int getId() {
        return this.id;
    }

    /**
     * <p>Devuelve el nombre del empleado.</p>
     *
     * @return El nombre del empleado.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * <p>Devuelve el nombre completo (nombre + apellidos) del empleado.</p>
     *
     * @return El nombre completo (nombre + apellidos) del empleado.
     */
    public String getNombreCompleto() {
        return this.nombre + " " + this.apellidos;
    }

    /**
     * <p>Devuelve los apellidos del cliente.</p>
     *
     * @return Los apellidos del cliente.
     */
    public String getApellidos() {
        return this.apellidos;
    }
}
