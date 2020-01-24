package com.ymourino.ad03;

import com.ymourino.ad03.utils.Command;
import com.ymourino.ad03.utils.CommandPrompt;
import com.ymourino.ad03.utils.KeyboardReader;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * <p>Clase principal de la aplicación.</p>
 */
public class Main {
    // La clase Empresa engloba todos los elementos de la aplicación, incluido el código
    // necesario para almacenar y recuperar los datos desde la base de datos.
    private static Empresa empresa;

    /**
     * <p>Punto de entrada de la aplicación.</p>
     * <p>Crea una nueva línea de comandos, añadiéndole todos los posibles comandos y asociándolos con los métodos
     * correspondientes.</p>
     *
     * @param args Parámetros de la línea de comandos (no se usan ahora).
     */
    public static void main(String[] args) {
        try {
            empresa = new Empresa();

            CommandPrompt.withCommands(
                    Command.withName("1").withDescription("Añadir una tienda").withMethod(Main::addTienda),
                    Command.withName("2").withDescription("Eliminar una tienda").withMethod(Main::delTienda),
                    Command.withName("3").withDescription("Listar tiendas").withMethod(Main::listTiendas),

                    Command.withName("4").withDescription("Añadir un producto").withMethod(Main::addProducto),
                    Command.withName("5").withDescription("Añadir un producto a una tienda").withMethod(Main::addProductoTienda),
                    Command.withName("6").withDescription("Eliminar un producto").withMethod(Main::delProducto),
                    Command.withName("7").withDescription("Eliminar un producto de una tienda").withMethod(Main::delProductoTienda),
                    Command.withName("8").withDescription("Actualizar stock de un producto en una tienda").withMethod(Main::updateStock),
                    Command.withName("9").withDescription("Listar productos").withMethod(Main::listProductos),
                    Command.withName("10").withDescription("Listar productos de una tienda").withMethod(Main::listProductosTienda),
                    Command.withName("11").withDescription("Mostrar stock de un producto en una tienda").withMethod(Main::showStock),

                    Command.withName("12").withDescription("Añadir un cliente").withMethod(Main::addCliente),
                    Command.withName("13").withDescription("Eliminar un cliente").withMethod(Main::delCliente),
                    Command.withName("14").withDescription("Listar clientes").withMethod(Main::listClientes),

                    Command.withName("15").withDescription("Añadir un empleado").withMethod(Main::addEmpleado),
                    Command.withName("16").withDescription("Añadir un empleado a una tienda").withMethod(Main::addEmpleadoTienda),
                    Command.withName("17").withDescription("Eliminar un empleado").withMethod(Main::delEmpleado),
                    Command.withName("18").withDescription("Eliminar un empleado de una tienda").withMethod(Main::delEmpleadoTienda),
                    Command.withName("19").withDescription("Actualizar horas de un empleado en una tienda").withMethod(Main::updateHoras),
                    Command.withName("20").withDescription("Listar empleados").withMethod(Main::listEmpleados),
                    Command.withName("21").withDescription("Listar empleados de una tienda").withMethod(Main::listEmpleadosTienda),
                    Command.withName("22").withDescription("Mostrar la jornada semanal de un empleado en una tienda").withMethod(Main::showJornada),

                    Command.withName("23").withDescription("Leer los titulares de El País").withMethod(Main::titulares),

                    Command.withName("24").withDescription("Salir del programa").withMethod(() -> {
                        try {
                            empresa.closeDb();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            System.out.println();
                            System.exit(0);
                        }
                    })
            )
                    .withPrompt("[AD03] >> ")
                    .run();
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
    }


    /* TIENDAS */

    /**
     * <p>Solicita los datos necesarios para crear una nueva tienda.</p>
     */
    private static void addTienda() {
        System.out.println();
        String nombre = KeyboardReader.readString("Introduzca el nombre de la tienda: ",
                "Error con el nombre introducido");
        String ciudad = KeyboardReader.readString("Introduzca la ciudad de la tienda: ",
                "Error con la ciudad introducida");
        Integer provincia = seleccionarProvincia();

        if (provincia != null) {
            empresa.addTienda(nombre, ciudad, provincia);
        }
    }

    /**
     * <p>Elimina una tienda tras solicitar su código al usuario.</p>
     */
    private static void delTienda() {
        Integer idTienda = seleccionarTienda();

        if (idTienda != null && confirmarSiNo("¿Realmente quiere eliminar la tienda? (S/N): ",
                "Eliminación cancelada")) {
            empresa.delTienda(idTienda);
        }
    }

    /**
     * <p>Muestra un listado de todas las tiendas.</p>
     */
    private static void listTiendas() {
        empresa.listTiendas();
    }


    /* PRODUCTOS */

    /**
     * <p>Solicita los datos necesarios para crear un nuevo producto.</p>
     */
    private static void addProducto() {
        System.out.println();
        String nombre = KeyboardReader.readString("Introduzca el nombre del producto: ",
                "Error con el nombre introducido");
        String descripcion = KeyboardReader.readString("Introduzca la descripción del producto: ",
                "Error con la descripción introducida");
        float precio = KeyboardReader.readFloat("Introduzca el precio del producto: ",
                "Error con el precio introducido");

        empresa.addProducto(nombre, descripcion, precio);
    }

    /**
     * <p>Permite asociar un producto a una tienda con un stock inicial.</p>
     */
    private static void addProductoTienda() {
        Integer idProducto = seleccionarProducto();

        if (idProducto != null) {
            Integer idTienda = seleccionarTienda();

            if (idTienda != null) {
                int stock = KeyboardReader.readInt("Introduzca el stock inicial: ",
                        "Error con la cantidad introducida");

                empresa.addProductoTienda(idProducto, idTienda, stock);
            }
        }
    }

    /**
     * <p>Elimina un producto tras solicitar su código al usuario.</p>
     */
    private static void delProducto() {
        Integer idProducto = seleccionarProducto();

        if (idProducto != null) {
            if (confirmarSiNo("¿Realmente quiere eliminar el producto? (S/N): ",
                    "Eliminación cancelada")) {
                empresa.delProducto(idProducto);
            }
        }
    }

    /**
     * <p>Elimina un producto de una tienda tras solicitar tienda y producto al usuario.</p>
     */
    private static void delProductoTienda() {
        Integer idTienda = seleccionarTienda();

        if (idTienda != null) {
            Integer idProducto = seleccionarProductoTienda(idTienda, true);

            if (idProducto != null) {
                if (confirmarSiNo("¿Realmente quiere eliminar el producto de la tienda? (S/N): ",
                        "Eliminación cancelada")) {
                    empresa.delProductoTienda(idProducto, idTienda);
                }
            }
        }
    }

    /**
     * <p>Elimina un empleado de una tienda tras solicitar tienda y empleado al usuario.</p>
     */
    private static void delEmpleadoTienda() {
        Integer idTienda = seleccionarTienda();

        if (idTienda != null) {
            Integer idEmpleado = seleccionarEmpleadoTienda(idTienda, true);

            if (idEmpleado != null) {
                if (confirmarSiNo("¿Realmente quiere eliminar el empleado de la tienda? (S/N): ",
                        "Eliminación cancelada")) {
                    empresa.delEmpleadoTienda(idEmpleado, idTienda);
                }
            }
        }
    }

    /**
     * <p>Actualiza el número de horas que un empleado trabaja en una tienda tras solicitar los datos al usuario.</p>
     */
    private static void updateHoras() {
        Integer idTienda = seleccionarTienda();

        if (idTienda != null) {
            Integer idProducto = seleccionarEmpleadoTienda(idTienda, true);

            if (idProducto != null) {
                float nuevaJornada = KeyboardReader.readFloat("Introduzca la nueva jornada semanal del empleado: ",
                        "Error con el stock introducido");

                empresa.updateHoras(idTienda, idProducto, nuevaJornada);
            }
        }
    }

    /**
     * <p>Permite actualizar el stock de un producto en una tienda tras solicitar tienda y producto al usuario.</p>
     */
    private static void updateStock() {
        Integer idTienda = seleccionarTienda();

        if (idTienda != null) {
            Integer idProducto = seleccionarProductoTienda(idTienda, true);

            if (idProducto != null) {
                int nuevoStock = KeyboardReader.readInt("Introduzca el nuevo stock del producto: ",
                        "Error con el stock introducido");

                empresa.updateStock(idTienda, idProducto, nuevoStock);
            }
        }
    }

    /**
     * <p>Muestra un listado de todos los productos.</p>
     */
    private static void listProductos() {
        empresa.listProductos();
    }

    /**
     * <p>Permite seleccionar una tienda de la que se mostrarán todos sus productos.</p>
     */
    private static void listProductosTienda() {
        Integer idTienda = seleccionarTienda();

        if (idTienda != null) {
            empresa.listProductosTienda(idTienda, true);
        }
    }

    /**
     * <p>Permite seleccionar un producto de una tienda determinada y muestra su stock.</p>
     */
    private static void showStock() {
        Integer idTienda = seleccionarTienda();

        if (idTienda != null) {
            Integer idProducto = seleccionarProductoTienda(idTienda, false);

            if (idProducto != null) {
                System.out.println("Stock: " + empresa.getStock(idTienda, idProducto));
            }
        }
    }


    /* CLIENTES */

    /**
     * <p>Solicita los datos necesarios para crear un nuevo cliente y almacenarlo.</p>
     */
    private static void addCliente() {
        System.out.println();
        String nombre = KeyboardReader.readString("Introduzca el nombre del cliente: ",
                "Error con el nombre introducido");
        String apellidos = KeyboardReader.readString("Introduzca los apellidos del cliente: ",
                "Error con los apellidos introducidos");
        String email = KeyboardReader.readString("Introduzca el e-mail del cliente: ",
                "Error con el e-mail introducido");

        empresa.addCliente(nombre, apellidos, email);
    }

    /**
     * <p>Tras mostrar los clientes de la empresa, permite seleccionar el que queramos eliminar.</p>
     */
    private static void delCliente() {
        Integer idCliente = seleccionarCliente();

        if (idCliente != null && confirmarSiNo("¿Realmente quiere eliminar el cliente? (S/N): ",
                "Eliminación cancelada")) {
            empresa.delCliente(idCliente);
        }
    }

    /**
     * <p>Muestra un listado de todos los clientes de la empresa.</p>
     */
    private static void listClientes() {
        empresa.listClientes();
    }


    /* EMPLEADOS */

    /**
     * <p>Solicita los datos necesarios para crear un nuevo empleado.</p>
     */
    private static void addEmpleado() {
        System.out.println();
        String nombre = KeyboardReader.readString("Introduzca el nombre del empleado: ",
                "Error con el nombre introducido");
        String apellidos = KeyboardReader.readString("Introduzca los apellidos del empleado: ",
                "Error con los apellidos introducidos");

        empresa.addEmpleado(nombre, apellidos);
    }

    /**
     * <p>Permite asociar un empleado a una tienda con una jornada semanal.</p>
     */
    private static void addEmpleadoTienda() {
        Integer idEmpleado = seleccionarEmpleado();

        if (idEmpleado != null) {
            Integer idTienda = seleccionarTienda();

            if (idTienda != null) {
                float horas = KeyboardReader.readFloat("Introduzca las horas semanales del empleado: ",
                        "Error con las horas introducidas");

                empresa.addEmpleadoTienda(idEmpleado, idTienda, horas);
            }
        }
    }

    /**
     * <p>Permite seleccionar un usuario para eliminarlo.</p>
     */
    private static void delEmpleado() {
        Integer idProducto = seleccionarEmpleado();

        if (idProducto != null) {
            if (confirmarSiNo("¿Realmente quiere eliminar el empleado? (S/N): ",
                    "Eliminación cancelada")) {
                empresa.delEmpleado(idProducto);
            }
        }
    }

    /**
     * <p>Muestra un listado con todos los empleados.</p>
     */
    private static void listEmpleados() {
        empresa.listEmpleados();
    }

    /**
     * <p>Tras solicitar el identificador de una tienda, muestra un listado con todos sus empleados.</p>
     */
    private static void listEmpleadosTienda() {
        Integer idTienda = seleccionarTienda();

        if (idTienda != null) {
            empresa.listEmpleadosTienda(idTienda, true);
        }
    }

    /**
     * <p>Tras solicitar el identificador de una tienda, y el identificador de alguno de sus empleados,
     * muestra su jornada laboral.</p>
     */
    private static void showJornada() {
        Integer idTienda = seleccionarTienda();

        if (idTienda != null) {
            Integer idEmpleado = seleccionarEmpleadoTienda(idTienda, false);

            if (idEmpleado != null) {
                System.out.println("Horas: " + empresa.getJornada(idTienda, idEmpleado));
            }
        }
    }


    /**
     * <p>Descarga el contenido del feed RSS de El País a una cadena de texto. Usando la librería Sax, se analiza dicha
     * cadena y se muestran los titulares de las noticias.</p>
     */
    private static void titulares() {
        // En primer lugar se descarga el contenido del feed RSS.
        try (Scanner scanner = new Scanner(new URL("http://ep00.epimg.net/rss/elpais/portada.xml").openStream(),
                StandardCharsets.UTF_8.toString())) {
            scanner.useDelimiter("\\A");
            String noticiasXML = scanner.hasNext() ? scanner.next() : "";

            // Si se ha obtenido una cadena no vacía...
            if (!noticiasXML.equals("")) {
                SAXParserFactory factory = SAXParserFactory.newInstance();

                try {
                    SAXParser parser = factory.newSAXParser();

                    // El handler se encargará de buscar todos los elementos de tipo "title" que se encuentren.
                    DefaultHandler handler = new DefaultHandler() {
                        boolean title = false;

                        public void startElement(String uri, String localName, String qName, Attributes attributes) {
                            if (qName.equalsIgnoreCase("title")) {
                                title = true;
                            }
                        }

                        public void characters(char[] ch, int start, int length) {
                            if (title) {
                                String title = new String(ch, start, length);
                                System.out.println(title);
                                this.title = false;
                            }
                        }
                    };

                    // Parseamos la cadena del feed y el handler se encargará de mostrar el contenido de los titulares.
                    parser.parse(new InputSource(new StringReader(noticiasXML)), handler);
                } catch (SAXException | ParserConfigurationException e) {
                    System.err.println(e.toString());
                }
            } else {
                System.out.println();
                System.out.println("No se han podido obtener los titulares");
            }
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }



    /* MÉTODOS AUXILIARES */

    /**
     * <p>Muestra un listado con las tiendas existentes y permite seleccionar una de ellas. El método se asegura de que
     * no se pueda seleccionar una tienda inexistente.</p>
     *
     * @return La clave de la tienda seleccionada, o null en caso de haber salido sin seleccionar nada.
     */
    private static Integer seleccionarTienda() {
        if (empresa.listTiendas()) {
            int idTienda = KeyboardReader.readInt("Introduzca el número de la tienda (0 para salir): ",
                    "Error con el número introducido");

            if (idTienda == 0) {
                return null;
            } else {
                while (empresa.getTienda(idTienda) == null) { // Solo se permiten tiendas que existan en la empresa.
                    System.out.println("El código no corresponde a ninguna tienda");
                    idTienda = KeyboardReader.readInt("Introduzca el número de la tienda (0 para salir): ",
                            "Error con el número introducido");

                    if (idTienda == 0) {
                        return null;
                    }
                }

                return idTienda;
            }
        } else {
            return null;
        }
    }

    /**
     * <p>Muestra un listado con los productos existentes y permite seleccionar uno de ellos. El método se asegura de que
     * no se pueda seleccionar un producto inexistente.</p>
     *
     * @return La clave del producto seleccionado, o null en caso de haber salido sin seleccionar nada.
     */
    private static Integer seleccionarProducto() {
        if (empresa.listProductos()) {
            int idProducto = KeyboardReader.readInt("Introduzca el identificador del producto (0 para salir): ",
                    "Error con el identificador introducido");

            if (idProducto == 0) {
                return null;
            } else {
                while (empresa.getProducto(idProducto) == null) {
                    System.out.println("El código no corresponde a ningún producto");
                    idProducto = KeyboardReader.readInt("Introduzca el identificador del producto (0 para salir): ",
                            "Error con el identificador introducido");

                    if (idProducto == 0) {
                        return null;
                    }
                }

                return idProducto;
            }
        } else {
            return null;
        }
    }

    /**
     * <p>Dada una tienda, muestra un listado con todos sus productos y permite seleccionar uno de ellos.</p>
     *
     * @param idTienda Tienda en la que hacer la selección de producto.
     * @param showStock Determina si de debe mostrar el stock de los productos en el listado.
     * @return Identificador del producto seleccionado.
     */
    private static Integer seleccionarProductoTienda(int idTienda, boolean showStock) {
        if (empresa.listProductosTienda(idTienda, showStock)) {
            int idProducto = KeyboardReader.readInt("Introduzca el identificador del producto (0 para salir): ",
                    "Error con el identificador introducido");

            if (idProducto == 0) {
                return null;
            } else {
                while (!empresa.checkProductoTienda(idProducto, idTienda)) {
                    System.out.println("El código no corresponde a ningún producto de la tienda");
                    idProducto = KeyboardReader.readInt("Introduzca el identificador del producto (0 para salir): ",
                            "Error con el identificador introducido");

                    if (idProducto == 0) {
                        return null;
                    }
                }

                return idProducto;
            }
        } else {
            return null;
        }
    }

    /**
     * <p>Dada una tienda, muestra un listado con todos sus empleados y permite seleccionar uno de ellos.</p>
     *
     * @param idTienda Tienda en la que hacer la selección del empleado.
     * @param showJornada
     * @return Identificador del empleado seleccionado.
     */
    private static Integer seleccionarEmpleadoTienda(int idTienda, boolean showJornada) {
        if (empresa.listEmpleadosTienda(idTienda, showJornada)) {
            int idEmpleado = KeyboardReader.readInt("Introduzca el identificador del empleado (0 para salir): ",
                    "Error con el identificador introducido");

            if (idEmpleado == 0) {
                return null;
            } else {
                while (!empresa.checkEmpleadoTienda(idEmpleado, idTienda)) {
                    System.out.println("El identificador no se corresponde a ningún empleado de la tienda");
                    idEmpleado = KeyboardReader.readInt("Introduzca el identificador del empleado (0 para salir): ",
                            "Error con el identificador introducido");

                    if (idEmpleado == 0) {
                        return null;
                    }
                }

                return idEmpleado;
            }
        } else {
            return null;
        }
    }

    /**
     * <p>Muestra un listado con los clientes existentes y permite seleccionar uno de ellos. El método se asegura de que
     * no se pueda seleccionar un cliente inexistente.</p>
     *
     * @return La clave del cliente seleccionado, o null en caso de haber salido sin seleccionar nada.
     */
    private static Integer seleccionarCliente() {
        if (empresa.listClientes()) {
            int idCliente = KeyboardReader.readInt("Introduzca el número del cliente (0 para salir): ",
                    "Error con el número introducido");

            if (idCliente == 0) {
                return null;
            } else {
                while (empresa.getCliente(idCliente) == null) { // Solo se permiten clientes que existan en la empresa.
                    System.out.println("El código no corresponde a ningún cliente");
                    idCliente = KeyboardReader.readInt("Introduzca el número del cliente (0 para salir): ",
                            "Error con el número introducido");

                    if (idCliente == 0) {
                        return null;
                    }
                }

                return idCliente;
            }
        } else {
            return null;
        }
    }

    /**
     * <p>Muestra un listado con los empleados existentes y permite seleccionar uno de ellos. El método se asegura de que
     * no se pueda seleccionar un empleado inexistente.</p>
     *
     * @return La clave del empleado seleccionado, o null en caso de haber salido sin seleccionar nada.
     */
    private static Integer seleccionarEmpleado() {
        if (empresa.listEmpleados()) {
            int idEmpleado = KeyboardReader.readInt("Introduzca el número del empleado (0 para salir): ",
                    "Error con el número introducido");

            if (idEmpleado == 0) {
                return null;
            } else {
                while (empresa.getEmpleado(idEmpleado) == null) {
                    System.out.println("El código no corresponde a ningún empleado");
                    idEmpleado = KeyboardReader.readInt("Introduzca el número del empleado (0 para salir): ",
                            "Error con el número introducido");

                    if (idEmpleado == 0) {
                        return null;
                    }
                }

                return idEmpleado;
            }
        } else {
            return null;
        }
    }

    /**
     * <p>Muestra un listado con las provincias existentes y permite seleccionar una de ellas. El método se asegura de que
     * no se pueda seleccionar una provincia inexistente.</p>
     *
     * @return La clave de la provincia seleccionada, o null en caso de haber salido sin seleccionar nada.
     */
    private static Integer seleccionarProvincia() {
        while (true) {
            String idProvincia = KeyboardReader.readString("Introduzca el identificador de la provincia (L para listarlas, 0 para salir): ",
                    "Error con los datos introducidos");

            if (idProvincia.equals("0")) {
                return null;
            } else if (idProvincia.equals("l") || idProvincia.equals("L")) {
                empresa.listProvincias();
            } else {
                try {
                    int provincia = Integer.parseInt(idProvincia);

                    if (empresa.getProvincia(provincia) == null) {
                        System.out.println("El código no corresponde a ninguna provincia");
                    } else {
                        return provincia;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error con los datos introducidos");
                }
            }
        }
    }

    /**
     * <p>Método para mostrar al usuario preguntas que se puedan responder con sí o no.</p>
     *
     * @param pregunta Texto que se mostrará al usuario a modo de pregunta.
     * @param noMsg Texto que se mostrará en caso de seleccionar la opción del no.
     * @return true si se responde sí, false si se responde no.
     */
    private static boolean confirmarSiNo(String pregunta, String noMsg) {
        String seguro = KeyboardReader.readPattern(pregunta, "Opción no reconocida", "[SsNn]");

        if (seguro.equals("S") || seguro.equals("s")) {
            return true;
        } else {
            System.out.println(noMsg);
            return false;
        }
    }
}
