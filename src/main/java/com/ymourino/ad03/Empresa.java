package com.ymourino.ad03;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.ymourino.ad03.db.DbHelper;

import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

/**
 * <p>Clase utilizada para gestionar las tiendas y clientes de la empresa.</p>
 */
public class Empresa {
    public static final String DB_FILE = "empresa.sqlite";
    private DbHelper db;

    /**
     * <p>Constructor de la clase Empresa.</p>
     * <p>Únicamente inicializa las propiedades de la clase con unos valores iniciales conocidos.</p>
     *
     * @throws SQLException Se lanza una excepción si hay problemas con la base de datos.
     */
    public Empresa() throws SQLException {
        db = new DbHelper("jdbc:sqlite:" + DB_FILE, DB_FILE);

        if (db.esNueva()) {
            Gson gson = new Gson();

            try {
                //JsonReader jr = new JsonReader(new FileReader("provincias.json"));
                JsonReader jr = new JsonReader(new InputStreamReader(this.getClass().getResourceAsStream("/provincias.json")));
                Provincias provincias = gson.fromJson(jr, Provincias.class);
                List<Provincia> provinciasList = provincias.getProvincias();
                for (Provincia provincia : provinciasList) {
                    db.addProvincia(provincia);
                }
            } catch (JsonSyntaxException e) {
                db.close();
                System.err.println("No se han podido almacenar las provincias en la base de datos.");
                System.exit(3);
            }
        }
    }

    /**
     * <p>Cierra la conexión con la base de datos.</p>
     *
     * @throws SQLException Se lanza una excepción si el cierre no ha sido correcto.
     */
    public void closeDb() throws SQLException {
        db.close();
    }


    /* ALTAS */

    /**
     * <p>Añade una nueva tienda a la estructura correspondiente.</p>
     *
     * @param nombre Nombre de la nueva tienda.
     * @param ciudad Nombre de la ciudad donde se ubica la nueva tienda.
     */
    public void addTienda(String nombre, String ciudad, int provincia) {
        Tienda tienda = new Tienda(nombre, ciudad, provincia);

        if (!db.addTienda(tienda)) {
            System.err.println("No se ha podido crear la nueva tienda. Por favor, compruebe los datos.");
        }
    }

    public void addProducto(String nombre, String descripcion, float precio) {
        Producto producto = new Producto(nombre, descripcion, precio);

        if (!db.addProducto(producto)) {
            System.err.println("No se ha podido crear el nuevo producto. Por favor, compruebe los datos.");
        }
    }

    /**
     * <p>Añade un nuevo cliente a la estructura correspondiente.</p>
     *
     * @param nombre Nombre del nuevo cliente.
     * @param apellidos Apellidos del nuevo cliente.
     * @param email Correo electrónico del nuevo cliente.
     */
    public void addCliente(String nombre, String apellidos, String email) {
        Cliente cliente = new Cliente(nombre, apellidos, email);

        if (!db.addCliente(cliente)) {
            System.err.println("No se ha podido añadir el cliente");
        }
    }

    public void addEmpleado(String nombre, String apellidos) {
        Empleado empleado = new Empleado(nombre, apellidos);

        if (!db.addEmpleado(empleado)) {
            System.err.println("No se ha podido añadir el empleado");
        }
    }

    public void addProductoTienda(int idProducto, int idTienda, int stock) {
        if (!db.addProductoTienda(idProducto, idTienda, stock)) {
            System.err.println("No se ha podido añadir el producto a la tienda.");
        }
    }

    public void addEmpleadoTienda(int idEmpleado, int idTienda, float horas) {
        if (!db.addEmpleadoTienda(idEmpleado, idTienda, horas)) {
            System.err.println("No se ha podido añadir el empleado a la tienda.");
        }
    }


    /* BAJAS */

    /**
     * <p>Dada una clave previamente validada, se elimina la tienda correspondiente.</p>
     * <p>La clave también debería validarse en este método, pero en esta ocasión queda como tarea pendiente.</p>
     *
     * @param id Clave de la tienda a eliminar.
     */
    public void delTienda(int id) {
        if (!db.delTiendaByID(id)) {
            System.out.println("No se ha podido eliminar la tienda seleccionada.");
        }
    }

    /**
     * <p>Dado un identificador de producto, elimina dicho producto de la base de datos.</p>
     *
     * @param id Identificador del producto a eliminar.
     */
    public void delProducto(int id) {
        if (!db.delProductoByID(id)) {
            System.out.println("No se ha podido eliminar el producto.");
        }
    }

    /**
     * <p>Dada una clave previamente validada, se elimina el cliente correspondiente.</p>
     * <p>La clave también debería validarse en este método, pero en esta ocasión queda como tarea pendiente.</p>
     *
     * @param id Clave del cliente a eliminar.
     */
    public void delCliente(int id) {
        if (!db.delClienteByID(id)) {
            System.out.println("No se ha podido eliminar el cliente.");
        }
    }

    /**
     * <p>Dada una clave previamente validada, se elimina el empleado correspondiente.</p>
     * <p>La clave también debería validarse en este método, pero en esta ocasión queda como tarea pendiente.</p>
     *
     * @param id Clave del empleado a eliminar.
     */
    public void delEmpleado(int id) {
        if (!db.delEmpleadoByID(id)) {
            System.out.println("No se ha podido eliminar el empleado.");
        }
    }

    /**
     * <p>Dadas unas claves previamente validadas de producto y tienda, elimina el producto de la tienda.</p>
     *
     * @param idProducto Identificador del producto.
     * @param idTienda Identificador de la tienda.
     */
    public void delProductoTienda(int idProducto, int idTienda) {
        if (!db.delProductoTiendaByID(idProducto, idTienda)) {
            System.out.println("No se ha podido eliminar el producto de la tienda.");
        }
    }

    /**
     * <p>Dadas unas claves previamente validadas de empleado y tienda, elimina el empleado de la tienda.</p>
     *
     * @param idEmpleado Identificador del empleado.
     * @param idTienda Identificador de la tienda.
     */
    public void delEmpleadoTienda(int idEmpleado, int idTienda) {
        if (!db.delEmpleadoTiendaByID(idEmpleado, idTienda)) {
            System.out.println("No se ha podido eliminar el empleado de la tienda.");
        }
    }


    /* MODIFICACIONES */

    /**
     * <p>Actualiza el stock de un producto dado en una tienda determinada.</p>
     *
     * @param idTienda Identificador de la tienda
     * @param idProducto Identificador del producto.
     * @param stock Nuevo stock del producto en la tienda indicada.
     */
    public void updateStock(int idTienda, int idProducto, int stock) {
        if (!db.updateStock(idProducto, idTienda, stock)) {
            System.out.println("No se ha podido actualizar el stock del producto.");
        }
    }

    /**
     * <p>Actualiza la jornada semanal de un empleado dado en una tienda determinada.</p>
     *
     * @param idTienda Identificador de la tienda.
     * @param idEmpleado Identificador del empleado.
     * @param horas Nueva jornada semanal del empleado.
     */
    public void updateHoras(int idTienda, int idEmpleado, float horas) {
        if (!db.updateHoras(idEmpleado, idTienda, horas)) {
            System.out.println("No se ha podido actualizar la jornada semanal del empleado.");
        }
    }


    /* CONSULTAS */

    /**
     * <p>Dada una clave, devuelve la tienda correspondiente, o null si la clave no se corresponde con ninguna tienda.</p>
     *
     * @param key Clave de la tienda a recuperar.
     * @return Tienda correspondiente a la clave, o null si la clave no tiene correspondencia.
     */
    public Tienda getTienda(Integer key) {
        return db.getTiendaByID(key);
    }

    /**
     * <p>Dado un identificador, devuelve el producto correspondiente, o null si la clave no se corresponde con ningún producto.</p>
     *
     * @param id Identificador del producto a recuperar.
     * @return Producto correspondiente a la clave, o null si la clave no tiene correspondencia.
     */
    public Producto getProducto(int id) {
        return db.getProductoByID(id);
    }

    /**
     * <p>Dada una clave, devuelve el cliente correspondiente, o null si la clave no se corresponde con ningún cliente.</p>
     *
     * @param id Clave del cliente a recuperar.
     * @return Cliente correspondiente a la clave, o null si la clave no tiene correspondencia.
     */
    public Cliente getCliente(int id) {
        return db.getClienteByID(id);
    }

    /**
     * <p>Dada una clave, devuelve el empleado correspondiente, o null si la clave no se corresponde con ningún empleado.</p>
     *
     * @param id Clave del empleado a recuperar.
     * @return Empleado correspondiente a la clave, o null si la clave no tiene correspondencia.
     */
    public Empleado getEmpleado(int id) {
        return db.getEmpleadoByID(id);
    }

    /**
     * <p>Dada una clave, devuelve la provincia correspondiente, o null si la clave no se corresponde con ninguna provincia.</p>
     *
     * @param id Clave de la provincia a recuperar.
     * @return Provincia correspondiente a la clave, o null si la clave no tiene correspondencia.
     */
    public Provincia getProvincia(int id) {
        return db.getProvinciaByID(id);
    }

    /**
     * <p>Dada la clave de un producto y de una tienda, comprueba si dicho producto
     * está asociado a la tienda.</p>
     *
     * @param idProducto Clave del producto a comprobar.
     * @param idTienda Clave de la tienda a comprobar.
     * @return True si el producto está asociado a la tienda.
     */
    public boolean checkProductoTienda(int idProducto, int idTienda) {
        return db.checkProductoTienda(idProducto, idTienda);
    }

    /**
     * <p>Dada la clave de un empleado y de una tienda, comprueba si dicho empleado
     * está asociado a la tienda.</p>
     *
     * @param idEmpleado Clave del empleado a comprobar.
     * @param idTienda Clave de la tienda a comprobar.
     * @return True si el empleado está asociado a la tienda.
     */
    public boolean checkEmpleadoTienda(int idEmpleado, int idTienda) {
        return db.checkEmpleadoTienda(idEmpleado, idTienda);
    }

    /**
     * <p>Dada la clave de un producto y de una tienda, obtiene el stock de
     * dicho producto en dicha tienda.</p>
     *
     * @param idTienda Clave de la tienda.
     * @param idProducto Clave del producto.
     * @return Stock del producto en la tienda.
     */
    public Integer getStock(int idTienda, int idProducto) {
        return db.getStock(idTienda, idProducto);
    }

    /**
     * <p>Dada la clave de un empleado y de una tienda, obtiene la jornada
     * laboral de dicho empleado en dicha tienda.</p>
     *
     * @param idTienda Clave de la tienda.
     * @param idEmpleado Clave del empleado.
     * @return Jornada semanal del empleado en la tienda.
     */
    public Float getJornada(int idTienda, int idEmpleado) {
        return db.getHoras(idTienda, idEmpleado);
    }

    /**
     * <p>Muestra en pantalla un listado de las tiendas existentes.</p>
     *
     * @return true si hay tiendas y se han listado, false en caso contrario.
     */
    public boolean listTiendas() {
        System.out.println();
        List<Tienda> tiendas = db.getTiendas();

        if (tiendas != null && tiendas.size() > 0) {
            for (Tienda tienda : tiendas) {
                System.out.println(tienda.getId()
                        + ". " + tienda.getNombre()
                        + " - " + tienda.getCiudad()
                        + " - " + db.getProvinciaByID(tienda.getProvincia()).getNome());
            }

            System.out.println();
            return true;
        } else {
            System.out.println("NO EXISTEN TIENDAS EN EL SISTEMA");
            return false;
        }
    }

    /**
     * <p>Muestra en pantalla un listado de los productos existentes.</p>
     *
     * @return true si hay productos y se han listado, false en caso contrario.
     */
    public boolean listProductos() {
        System.out.println();
        List<Producto> productos = db.getProductos();

        if (productos != null && productos.size() > 0) {
            for (Producto producto : productos) {
                System.out.println(producto.getId()
                        + ". " + producto.getNombre()
                        + " - " + producto.getDescripcion()
                        + " - " + producto.getPrecio() + " €");
            }

            System.out.println();
            return true;
        } else {
            System.out.println("NO EXISTEN PRODUCTOS EN EL SISTEMA");
            return false;
        }
    }

    /**
     * <p>Muestra en pantalla un listado de los clientes existentes.</p>
     *
     * @return true si hay clientes y se han listado, false en caso contrario.
     */
    public boolean listClientes() {
        System.out.println();
        List<Cliente> clientes = db.getClientes();

        if (clientes.size() > 0) {
            for (Cliente cliente : clientes) {
                System.out.println(cliente.getId() + ". " + cliente.getNombreCompleto() + " (" + cliente.getEmail() + ")");
            }

            System.out.println();
            return true;
        } else {
            System.out.println("NO EXISTEN CLIENTES EN EL SISTEMA");
            return false;
        }
    }

    /**
     * <p>Muestra en pantalla un listado de los empleados existentes.</p>
     *
     * @return true si hay empleados y se han listado, false en caso contrario.
     */
    public boolean listEmpleados() {
        System.out.println();
        List<Empleado> empleados = db.getEmpleados();

        if (empleados != null && empleados.size() > 0) {
            for (Empleado empleado : empleados) {
                System.out.println(empleado.getId()
                        + ". " + empleado.getNombreCompleto());
            }

            System.out.println();
            return true;
        } else {
            System.out.println("NO EXISTEN EMPLEADOS EN EL SISTEMA");
            return false;
        }
    }

    /**
     * <p>Muestra en pantalla un listado de las provincias existentes.</p>
     */
    public void listProvincias() {
        Provincias provincias = db.getProvincias();

        System.out.println();
        for (Provincia prov : provincias.getProvincias()) {
            System.out.println(prov.getId() + ": " + prov.getNome());
        }
        System.out.println();
    }

    /**
     * <p>Dada una tienda, hace un listado de los productos asociados a ella.</p>
     *
     * @param idTienda Identificador de la tienda.
     * @param showStock Determina si en el listado debe aparecer el stock.
     * @return true si hay productos asociados a la tienda y se han listado, false en caso contrario.
     */
    public boolean listProductosTienda(int idTienda, boolean showStock) {
        System.out.println();
        List<Producto> productos = db.getProductosTienda(idTienda);

        if (productos != null && productos.size() > 0) {
            for (Producto producto : productos) {
                System.out.print(producto.getId()
                        + ". " + producto.getNombre()
                        + " - " + producto.getDescripcion()
                        + " - " + producto.getPrecio() + " €");
                if (showStock) {
                    System.out.println(" - Stock: " + db.getStock(idTienda, producto.getId()));
                } else {
                    System.out.println();
                }
            }

            System.out.println();
            return true;
        } else {
            System.out.println("NO EXISTEN PRODUCTOS EN LA TIENDA");
            return false;
        }
    }

    /**
     * <p>Dada una tienda, hace un listado de los empleados asociados a ella.</p>
     *
     * @param idTienda Identificador de la tienda.
     * @param showHoras
     * @return true si hay empleados asociados a la tienda y se han listado, false en caso contrario.
     */
    public boolean listEmpleadosTienda(int idTienda, boolean showHoras) {
        System.out.println();
        List<Empleado> empleados = db.getEmpleadosTienda(idTienda);

        if (empleados != null && empleados.size() > 0) {
            for (Empleado empleado : empleados) {
                System.out.print(empleado.getId()
                        + ". " + empleado.getNombreCompleto());
                if (showHoras) {
                    System.out.println(" - " + db.getHoras(idTienda, empleado.getId()) + " horas");
                } else {
                    System.out.println();
                }
            }

            System.out.println();
            return true;
        } else {
            System.out.println("NO EXISTEN EMPLEADOS EN LA TIENDA");
            return false;
        }
    }
}
