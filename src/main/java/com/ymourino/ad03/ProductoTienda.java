package com.ymourino.ad03;

/**
 * <p>Clase utilizada para almacenar los campos que se usan para el manejo de la base
 * de datos.</p>
 */
public class ProductoTienda {
    public static final String TABLE_NAME = "productos_tiendas";
    public static final String COLUMN_PRODUCTO_ID = "producto_id";
    public static final String COLUMN_TIENDA_ID = "tienda_id";
    public static final String COLUMN_STOCK = "stock";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_PRODUCTO_ID + " INTEGER NOT NULL,"
                    + COLUMN_TIENDA_ID + " INTEGER NOT NULL,"
                    + COLUMN_STOCK + " INTEGER NOT NULL DEFAULT 0,"
                    + "PRIMARY KEY (" + COLUMN_PRODUCTO_ID + "," + COLUMN_TIENDA_ID + "),"
                    + "FOREIGN KEY (" + COLUMN_PRODUCTO_ID + ") REFERENCES " + Producto.TABLE_NAME + "(" + Producto.COLUMN_ID + ") ON DELETE CASCADE ON UPDATE CASCADE,"
                    + "FOREIGN KEY (" + COLUMN_TIENDA_ID + ") REFERENCES " + Tienda.TABLE_NAME + "(" + Tienda.COLUMN_ID + ") ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")";
}
