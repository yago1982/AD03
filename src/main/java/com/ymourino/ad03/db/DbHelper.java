package com.ymourino.ad03.db;

import com.ymourino.ad03.*;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Clase utilizada para gestionar la base de datos.</p>
 */
public class DbHelper {
    private Connection db;
    private boolean nueva;

    /**
     * <p>Constructor.</p>
     * <p>Se encarga de comprobar la existencia de la base de datos y crearla en caso necesario.</p>
     *
     * @param url URL de conexión con la base de datos.
     * @param dbFile Nombre del fichero de la base de datos.
     * @throws SQLException
     */
    public DbHelper(String url, String dbFile) throws SQLException {
        File file = new File(dbFile);
        if (file.exists()) { // El fichero de base de datos existe.
            db = DriverManager.getConnection(url);
            this.nueva = false;
        } else { // La base de datos no existe y debe ser creada.
            createDB(url);
            this.nueva = true;
        }
    }

    /**
     * <p>Crea las tablas de la base de datos.</p>
     *
     * @param url URL de conexión con la base de datos.
     * @throws SQLException
     */
    private void createDB(String url) throws SQLException {
        db = DriverManager.getConnection(url);

        Statement stmt = db.createStatement();
        stmt.execute("PRAGMA foreign_keys = ON");

        stmt.execute(Cliente.CREATE_TABLE);
        stmt.execute(Provincia.CREATE_TABLE);
        stmt.execute(Tienda.CREATE_TABLE);
        stmt.execute(Empleado.CREATE_TABLE);
        stmt.execute(Producto.CREATE_TABLE);
        stmt.execute(EmpleadoTienda.CREATE_TABLE);
        stmt.execute(ProductoTienda.CREATE_TABLE);

        stmt.close();
    }

    /**
     * <p>Indica si la base de datos acaba de ser creada o ya existía.</p>
     * <p>El valor de esta propiedad debe ser establecido en el constructor.</p>
     *
     * @return True si la base de datos acaba de ser creada.
     */
    public boolean esNueva() {
        return this.nueva;
    }

    /**
     * <p>Cierra la conexión con la base de datos.</p>
     *
     * @throws SQLException
     */
    public void close() throws SQLException {
        db.close();
    }


    /*
     * A partir de este punto se encuentran todos los métodos que se encargan de
     * las altas, las bajas, las modificaciones y las consultas en la base de datos.
     * Los nombres de cada método deberían ser suficientemente claros para saber
     * qué hace cada uno.
     */

    /* ALTAS */

    public boolean addTienda(Tienda tienda) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");

            stmt.execute("INSERT INTO " + Tienda.TABLE_NAME + "("
                    + Tienda.COLUMN_NOMBRE + ","
                    + Tienda.COLUMN_CIUDAD + ","
                    + Tienda.COLUMN_PROVINCIA
                    + ") VALUES ("
                    + "\"" + tienda.getNombre() + "\","
                    + "\"" + tienda.getCiudad() + "\","
                    + tienda.getProvincia()
                    + ");"
            );

            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addProducto(Producto producto) {
        try {
            Statement stmt = db.createStatement();

            stmt.execute("INSERT INTO " + Producto.TABLE_NAME + "("
                    + Producto.COLUMN_NOMBRE + ","
                    + Producto.COLUMN_DESCRIPCION + ","
                    + Producto.COLUMN_PRECIO
                    + ") VALUES ("
                    + "\"" + producto.getNombre() + "\","
                    + "\"" + producto.getDescripcion() + "\","
                    + "\"" + producto.getPrecio() + "\""
                    + ");"
            );

            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addCliente(Cliente cliente) {
        try {
            Statement stmt = db.createStatement();

            stmt.execute("INSERT INTO " + Cliente.TABLE_NAME + "("
                    + Cliente.COLUMN_NOMBRE + ","
                    + Cliente.COLUMN_APELLIDOS + ","
                    + Cliente.COLUMN_MAIL
                    + ") VALUES ("
                    + "\"" + cliente.getNombre() + "\","
                    + "\"" + cliente.getApellidos() + "\","
                    + "\"" + cliente.getEmail() + "\""
                    + ");"
            );

            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addEmpleado(Empleado empleado) {
        try {
            Statement stmt = db.createStatement();

            stmt.execute("INSERT INTO " + Empleado.TABLE_NAME + "("
                    + Empleado.COLUMN_NOMBRE + ","
                    + Empleado.COLUMN_APELLIDOS
                    + ") VALUES ("
                    + "\"" + empleado.getNombre() + "\","
                    + "\"" + empleado.getApellidos() + "\""
                    + ");"
            );

            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void addProvincia(Provincia provincia) throws SQLException {
        Statement stmt = db.createStatement();

        stmt.execute("INSERT INTO " + Provincia.TABLE_NAME
                + "(" + Provincia.COLUMN_ID + "," + Provincia.COLUMN_NOMBRE + ")"
                + " VALUES (" + provincia.getId() + ",\"" + provincia.getNome() + "\");");

        stmt.close();
    }

    public boolean addProductoTienda(int idProducto, int idTienda, int stock) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");

            stmt.execute("INSERT INTO " + ProductoTienda.TABLE_NAME + "("
                    + ProductoTienda.COLUMN_PRODUCTO_ID + ","
                    + ProductoTienda.COLUMN_TIENDA_ID + ","
                    + ProductoTienda.COLUMN_STOCK
                    + ") VALUES ("
                    + idProducto + ","
                    + idTienda + ","
                    + stock
                    + ");"
            );

            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addEmpleadoTienda(int idEmpleado, int idTienda, float horas) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");

            stmt.execute("INSERT INTO " + EmpleadoTienda.TABLE_NAME + "("
                    + EmpleadoTienda.COLUMN_EMPLEADO_ID + ","
                    + EmpleadoTienda.COLUMN_TIENDA_ID + ","
                    + EmpleadoTienda.COLUMN_HORAS
                    + ") VALUES ("
                    + idEmpleado + ","
                    + idTienda + ","
                    + "\"" + horas + "\""
                    + ");"
            );

            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    /* BAJAS */

    public boolean delTiendaByID(int id) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");

            stmt.execute("DELETE FROM " + Tienda.TABLE_NAME
                    + " WHERE "
                    + Tienda.COLUMN_ID + " = " + id
            );

            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delProductoByID(int id) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");

            stmt.execute("DELETE FROM " + Producto.TABLE_NAME
                    + " WHERE "
                    + Producto.COLUMN_ID + " = " + id
            );

            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delClienteByID(int id) {
        try {
            Statement stmt = db.createStatement();

            stmt.execute("DELETE FROM " + Cliente.TABLE_NAME
                    + " WHERE "
                    + Cliente.COLUMN_ID + " = " + id
            );

            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delEmpleadoByID(int id) {
        try {
            Statement stmt = db.createStatement();

            stmt.execute("DELETE FROM " + Empleado.TABLE_NAME
                    + " WHERE "
                    + Empleado.COLUMN_ID + " = " + id
            );

            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delProductoTiendaByID(int idProducto, int idTienda) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");

            stmt.execute("DELETE FROM " + ProductoTienda.TABLE_NAME
                    + " WHERE "
                    + ProductoTienda.COLUMN_PRODUCTO_ID + " = " + idProducto
                    + " AND "
                    + ProductoTienda.COLUMN_TIENDA_ID + " = " + idTienda
            );

            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delEmpleadoTiendaByID(int idEmpleado, int idTienda) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");

            stmt.execute("DELETE FROM " + EmpleadoTienda.TABLE_NAME
                    + " WHERE "
                    + EmpleadoTienda.COLUMN_EMPLEADO_ID + " = " + idEmpleado
                    + " AND "
                    + EmpleadoTienda.COLUMN_TIENDA_ID + " = " + idTienda
            );

            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    /* MODIFICACIONES */

    public boolean updateStock(int idProducto, int idTienda, int stock) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");

            stmt.execute("UPDATE " + ProductoTienda.TABLE_NAME
                    + " SET "
                    + ProductoTienda.COLUMN_STOCK + " = " + stock
                    + " WHERE "
                    + ProductoTienda.COLUMN_PRODUCTO_ID + " = " + idProducto
                    + " AND "
                    + ProductoTienda.COLUMN_TIENDA_ID + " = " + idTienda
            );

            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateHoras(int idEmpleado, int idTienda, float horas) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");

            stmt.execute("UPDATE " + EmpleadoTienda.TABLE_NAME
                    + " SET "
                    + EmpleadoTienda.COLUMN_HORAS + " = " + horas
                    + " WHERE "
                    + EmpleadoTienda.COLUMN_EMPLEADO_ID + " = " + idEmpleado
                    + " AND "
                    + EmpleadoTienda.COLUMN_TIENDA_ID + " = " + idTienda
            );

            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    /* CONSULTAS */

    public List<Tienda> getTiendas() {
        List<Tienda> tiendas = new ArrayList<>();

        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + Tienda.TABLE_NAME);

            while (rs.next()) {
                Tienda tienda = new Tienda(rs.getInt(Tienda.COLUMN_ID),
                        rs.getString(Tienda.COLUMN_NOMBRE),
                        rs.getString(Tienda.COLUMN_CIUDAD),
                        rs.getInt(Tienda.COLUMN_PROVINCIA));
                tiendas.add(tienda);
            }

            return tiendas;
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Producto> getProductos() {
        List<Producto> productos = new ArrayList<>();

        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + Producto.TABLE_NAME);

            while (rs.next()) {
                Producto producto = new Producto(rs.getInt(Producto.COLUMN_ID),
                        rs.getString(Producto.COLUMN_NOMBRE),
                        rs.getString(Producto.COLUMN_DESCRIPCION),
                        rs.getFloat(Producto.COLUMN_PRECIO));
                productos.add(producto);
            }

            return productos;
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Cliente> getClientes() {
        List<Cliente> clientes = new ArrayList<>();

        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + Cliente.TABLE_NAME);

            while (rs.next()) {
                Cliente cliente = new Cliente(rs.getInt(Cliente.COLUMN_ID),
                        rs.getString(Cliente.COLUMN_NOMBRE),
                        rs.getString(Cliente.COLUMN_APELLIDOS),
                        rs.getString(Cliente.COLUMN_MAIL));
                clientes.add(cliente);
            }

            return clientes;
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Empleado> getEmpleados() {
        List<Empleado> empleados = new ArrayList<>();

        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + Empleado.TABLE_NAME);

            while (rs.next()) {
                Empleado empleado = new Empleado(rs.getInt(Empleado.COLUMN_ID),
                        rs.getString(Empleado.COLUMN_NOMBRE),
                        rs.getString(Empleado.COLUMN_APELLIDOS));
                empleados.add(empleado);
            }

            return empleados;
        } catch (SQLException e) {
            return null;
        }
    }

    public Provincias getProvincias() {
        Provincias provincias = new Provincias();
        List<Provincia> provinciaList = new ArrayList<>();

        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + Provincia.TABLE_NAME);

            while (rs.next()) {
                Provincia provincia = new Provincia(rs.getInt(Provincia.COLUMN_ID), rs.getString(Provincia.COLUMN_NOMBRE));
                provinciaList.add(provincia);
            }

            provincias.setProvincias(provinciaList);
            return provincias;
        } catch (SQLException e) {
            System.err.println(e.toString());
            return null;
        }
    }

    public Tienda getTiendaByID(int id) {
        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + Tienda.TABLE_NAME
                    + " WHERE "
                    + Tienda.COLUMN_ID + " = " + id
            );

            if (rs.next()) {
                return new Tienda(rs.getInt(Tienda.COLUMN_ID), rs.getString(Tienda.COLUMN_NOMBRE), rs.getString(Tienda.COLUMN_CIUDAD), rs.getInt(Tienda.COLUMN_PROVINCIA));
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public Producto getProductoByID(int id) {
        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + Producto.TABLE_NAME
                    + " WHERE "
                    + Producto.COLUMN_ID + " = " + id
            );

            if (rs.next()) {
                return new Producto(rs.getInt(Producto.COLUMN_ID),
                        rs.getString(Producto.COLUMN_NOMBRE),
                        rs.getString(Producto.COLUMN_DESCRIPCION),
                        rs.getFloat(Producto.COLUMN_PRECIO));
            } else {
                return null;
            }

        } catch (SQLException e) {
            return null;
        }
    }

    public Cliente getClienteByID(int id) {
        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + Cliente.TABLE_NAME
                    + " WHERE "
                    + Cliente.COLUMN_ID + " = " + id
            );

            if (rs.next()) {
                return new Cliente(rs.getInt(Cliente.COLUMN_ID),
                        rs.getString(Cliente.COLUMN_NOMBRE),
                        rs.getString(Cliente.COLUMN_APELLIDOS),
                        rs.getString(Cliente.COLUMN_MAIL));
            } else {
                return null;
            }

        } catch (SQLException e) {
            return null;
        }
    }

    public Empleado getEmpleadoByID(int id) {
        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + Empleado.TABLE_NAME
                    + " WHERE "
                    + Empleado.COLUMN_ID + " = " + id
            );

            if (rs.next()) {
                return new Empleado(rs.getInt(Empleado.COLUMN_ID), rs.getString(Empleado.COLUMN_NOMBRE), rs.getString(Empleado.COLUMN_APELLIDOS));
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public Provincia getProvinciaByID(int id) {
        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + Provincia.TABLE_NAME + " WHERE " + Provincia.COLUMN_ID + " = " + id);

            if (rs.next()) {
                return new Provincia(rs.getInt(Provincia.COLUMN_ID), rs.getString(Provincia.COLUMN_NOMBRE));
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Producto> getProductosTienda(int idTienda) {
        List<Producto> productos = new ArrayList<>();

        try {
            Statement stmt = db.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
            ResultSet rs = stmt.executeQuery("SELECT "
                    + Producto.TABLE_NAME + "." + Producto.COLUMN_ID + ","
                    + Producto.TABLE_NAME + "." + Producto.COLUMN_NOMBRE + ","
                    + Producto.TABLE_NAME + "." + Producto.COLUMN_DESCRIPCION + ","
                    + Producto.TABLE_NAME + "." + Producto.COLUMN_PRECIO
                    + " FROM "
                    + Producto.TABLE_NAME + ","
                    + Tienda.TABLE_NAME + ","
                    + ProductoTienda.TABLE_NAME
                    + " WHERE "
                    + Producto.TABLE_NAME + "." + Producto.COLUMN_ID + " = " + ProductoTienda.TABLE_NAME + "." + ProductoTienda.COLUMN_PRODUCTO_ID
                    + " AND "
                    + Tienda.TABLE_NAME + "." + Tienda.COLUMN_ID + " = " + ProductoTienda.TABLE_NAME + "." + ProductoTienda.COLUMN_TIENDA_ID
                    + " AND "
                    + Tienda.TABLE_NAME + "." + Tienda.COLUMN_ID + " = " + idTienda
            );

            while (rs.next()) {
                Producto producto = new Producto(rs.getInt(Producto.COLUMN_ID),
                        rs.getString(Producto.COLUMN_NOMBRE),
                        rs.getString(Producto.COLUMN_DESCRIPCION),
                        rs.getFloat(Producto.COLUMN_PRECIO));
                productos.add(producto);
            }

            return productos;
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Empleado> getEmpleadosTienda(int idTienda) {
        List<Empleado> empleados = new ArrayList<>();

        try {
            Statement stmt = db.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
            ResultSet rs = stmt.executeQuery("SELECT "
                    + Empleado.TABLE_NAME + "." + Empleado.COLUMN_ID + ","
                    + Empleado.TABLE_NAME + "." + Empleado.COLUMN_NOMBRE + ","
                    + Empleado.TABLE_NAME + "." + Empleado.COLUMN_APELLIDOS
                    + " FROM "
                    + Empleado.TABLE_NAME + ","
                    + Tienda.TABLE_NAME + ","
                    + EmpleadoTienda.TABLE_NAME
                    + " WHERE "
                    + Empleado.TABLE_NAME + "." + Empleado.COLUMN_ID + " = " + EmpleadoTienda.TABLE_NAME + "." + EmpleadoTienda.COLUMN_EMPLEADO_ID
                    + " AND "
                    + Tienda.TABLE_NAME + "." + Tienda.COLUMN_ID + " = " + EmpleadoTienda.TABLE_NAME + "." + EmpleadoTienda.COLUMN_TIENDA_ID
                    + " AND "
                    + Tienda.TABLE_NAME + "." + Tienda.COLUMN_ID + " = " + idTienda
            );

            while (rs.next()) {
                Empleado empleado = new Empleado(rs.getInt(Empleado.COLUMN_ID),
                        rs.getString(Empleado.COLUMN_NOMBRE),
                        rs.getString(Empleado.COLUMN_APELLIDOS));
                empleados.add(empleado);
            }

            return empleados;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean checkProductoTienda(int idProducto, int idTienda) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
            ResultSet rs = stmt.executeQuery("SELECT "
                    + Producto.TABLE_NAME + "." + Producto.COLUMN_ID + ","
                    + Producto.TABLE_NAME + "." + Producto.COLUMN_NOMBRE + ","
                    + Producto.TABLE_NAME + "." + Producto.COLUMN_DESCRIPCION + ","
                    + Producto.TABLE_NAME + "." + Producto.COLUMN_PRECIO
                    + " FROM "
                    + Producto.TABLE_NAME + ","
                    + Tienda.TABLE_NAME + ","
                    + ProductoTienda.TABLE_NAME
                    + " WHERE "
                    + Producto.TABLE_NAME + "." + Producto.COLUMN_ID + " = " + ProductoTienda.TABLE_NAME + "." + ProductoTienda.COLUMN_PRODUCTO_ID
                    + " AND "
                    + Tienda.TABLE_NAME + "." + Tienda.COLUMN_ID + " = " + ProductoTienda.TABLE_NAME + "." + ProductoTienda.COLUMN_TIENDA_ID
                    + " AND "
                    + Tienda.TABLE_NAME + "." + Tienda.COLUMN_ID + " = " + idTienda
                    + " AND "
                    + Producto.TABLE_NAME + "." + Producto.COLUMN_ID + " = " + idProducto
            );

            if (rs.next()) {
                return true; // El producto existe en la tienda.
            } else {
                return false; // El producto no existe en la tienda.
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean checkEmpleadoTienda(int idEmpleado, int idTienda) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
            ResultSet rs = stmt.executeQuery("SELECT "
                    + Empleado.TABLE_NAME + "." + Empleado.COLUMN_ID + ","
                    + Empleado.TABLE_NAME + "." + Empleado.COLUMN_NOMBRE + ","
                    + Empleado.TABLE_NAME + "." + Empleado.COLUMN_APELLIDOS
                    + " FROM "
                    + Empleado.TABLE_NAME + ","
                    + Tienda.TABLE_NAME + ","
                    + EmpleadoTienda.TABLE_NAME
                    + " WHERE "
                    + Empleado.TABLE_NAME + "." + Empleado.COLUMN_ID + " = " + EmpleadoTienda.TABLE_NAME + "." + EmpleadoTienda.COLUMN_EMPLEADO_ID
                    + " AND "
                    + Tienda.TABLE_NAME + "." + Tienda.COLUMN_ID + " = " + EmpleadoTienda.TABLE_NAME + "." + EmpleadoTienda.COLUMN_TIENDA_ID
                    + " AND "
                    + Tienda.TABLE_NAME + "." + Tienda.COLUMN_ID + " = " + idTienda
                    + " AND "
                    + Empleado.TABLE_NAME + "." + Empleado.COLUMN_ID + " = " + idEmpleado
            );

            if (rs.next()) {
                return true; // El empleado existe en la tienda.
            } else {
                return false; // El empleado no existe en la tienda.
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public Integer getStock(int idTienda, int idProducto) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
            ResultSet rs = stmt.executeQuery("SELECT "
                    + ProductoTienda.TABLE_NAME + "." + ProductoTienda.COLUMN_STOCK
                    + " FROM "
                    + Producto.TABLE_NAME + ","
                    + Tienda.TABLE_NAME + ","
                    + ProductoTienda.TABLE_NAME
                    + " WHERE "
                    + Producto.TABLE_NAME + "." + Producto.COLUMN_ID + " = " + ProductoTienda.TABLE_NAME + "." + ProductoTienda.COLUMN_PRODUCTO_ID
                    + " AND "
                    + Tienda.TABLE_NAME + "." + Tienda.COLUMN_ID + " = " + ProductoTienda.TABLE_NAME + "." + ProductoTienda.COLUMN_TIENDA_ID
                    + " AND "
                    + Tienda.TABLE_NAME + "." + Tienda.COLUMN_ID + " = " + idTienda
                    + " AND "
                    + Producto.TABLE_NAME + "." + Producto.COLUMN_ID + " = " + idProducto
            );

            if (rs.next()) {
                return rs.getInt(ProductoTienda.COLUMN_STOCK);
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public Float getHoras(int idTienda, int idEmpleado) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
            ResultSet rs = stmt.executeQuery("SELECT "
                    + EmpleadoTienda.TABLE_NAME + "." + EmpleadoTienda.COLUMN_HORAS
                    + " FROM "
                    + Empleado.TABLE_NAME + ","
                    + Tienda.TABLE_NAME + ","
                    + EmpleadoTienda.TABLE_NAME
                    + " WHERE "
                    + Empleado.TABLE_NAME + "." + Empleado.COLUMN_ID + " = " + EmpleadoTienda.TABLE_NAME + "." + EmpleadoTienda.COLUMN_EMPLEADO_ID
                    + " AND "
                    + Tienda.TABLE_NAME + "." + Tienda.COLUMN_ID + " = " + EmpleadoTienda.TABLE_NAME + "." + EmpleadoTienda.COLUMN_TIENDA_ID
                    + " AND "
                    + Tienda.TABLE_NAME + "." + Tienda.COLUMN_ID + " = " + idTienda
                    + " AND "
                    + Empleado.TABLE_NAME + "." + Empleado.COLUMN_ID + " = " + idEmpleado
            );

            if (rs.next()) {
                return rs.getFloat(EmpleadoTienda.COLUMN_HORAS);
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }
}
