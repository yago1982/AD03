package com.ymourino.ad03;

/**
 * <p>Clase utilizada para almacenar los datos de los diferentes clientes de la empresa.</p>
 * <p>No se han creado los setter para las propiedades de la clase ya que no son necesarios ahora mismo.</p>
 */
public class Cliente {
    /* Campos para crear y manejar la base de datos. */
    public static final String TABLE_NAME = "clientes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_APELLIDOS = "apellidos";
    public static final String COLUMN_MAIL = "mail";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOMBRE + " TEXT NOT NULL,"
                    + COLUMN_APELLIDOS + " TEXT NOT NULL,"
                    + COLUMN_MAIL + " TEXT NOT NULL UNIQUE"
                    + ")";


    private int id;
    private String nombre;
    private String apellidos;
    private String email;

    /**
     * <p>Constructor de la clase Cliente.</p>
     * <p>Únicamente se limita a inicializar las propiedades del objeto con los datos que recibe en los parámetros.</p>
     *
     * @param id Identificador del cliente.
     * @param nombre Nombre del cliente.
     * @param apellidos Apellidos del cliente.
     * @param email Correo electrónico del cliente.
     */
    public Cliente(int id, String nombre, String apellidos, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
    }

    /**
     * <p>Constructor de la clase Cliente.</p>
     * <p>Se usa cuando se está creando un cliente que no viene de una base de datos, es decir, que no tiene id.</p>
     *
     * @param nombre Nombre del cliente.
     * @param apellidos Apellidos del cliente.
     * @param email Correo electrónico del cliente.
     */
    public Cliente(String nombre, String apellidos, String email) {
        this(0, nombre, apellidos, email);
    }

    /**
     * <p>Devuelve el identificador del cliente.</p>
     *
     * @return El identificador del cliente.
     */
    public int getId() {
        return this.id;
    }

    /**
     * <p>Devuelve el nombre del cliente.</p>
     *
     * @return El nombre del cliente.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * <p>Devuelve el nombre completo (nombre + apellidos) del cliente.</p>
     *
     * @return El nombre completo (nombre + apellidos) del cliente.
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

    /**
     * <p>Devuelve el correo electrónico del cliente.</p>
     *
     * @return El correo electrónico del cliente.
     */
    public String getEmail() {
        return this.email;
    }
}
