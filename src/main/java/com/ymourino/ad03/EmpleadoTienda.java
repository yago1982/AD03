package com.ymourino.ad03;

/**
 * <p>Clase utilizada para almacenar los campos que se usan para el manejo de la base
 * de datos.</p>
 */
public class EmpleadoTienda {
    public static final String TABLE_NAME = "empleados_tiendas";
    public static final String COLUMN_EMPLEADO_ID = "empleado_id";
    public static final String COLUMN_TIENDA_ID = "tienda_id";
    public static final String COLUMN_HORAS = "horas";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_EMPLEADO_ID + " INTEGER NOT NULL,"
                    + COLUMN_TIENDA_ID + " INTEGER NOT NULL,"
                    + COLUMN_HORAS + " REAL NOT NULL,"
                    + "PRIMARY KEY (" + COLUMN_EMPLEADO_ID + "," + COLUMN_TIENDA_ID + "),"
                    + "FOREIGN KEY (" + COLUMN_EMPLEADO_ID + ") REFERENCES " + Empleado.TABLE_NAME + "(" + Empleado.COLUMN_ID + ") ON DELETE CASCADE ON UPDATE CASCADE,"
                    + "FOREIGN KEY (" + COLUMN_TIENDA_ID + ") REFERENCES " + Tienda.TABLE_NAME + "(" + Tienda.COLUMN_ID + ") ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")";
}
