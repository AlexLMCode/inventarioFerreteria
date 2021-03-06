package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import datos.getData;
import modelos.Cliente;
import modelos.Factura;
import modelos.Producto;
import modelos.Proveedor;

public class Aplicacion {

    public static final String FACTURAS = "facturas";
    public static final String PRODUCTOS = "productos";
    public static final String PROVEEDORES = "proveedores";
    public static final String CLIENTES = "clientes";
    public static final int SALIR = 0;
    public static final int GESTION_CLIENTES = 1;
    public static final int GESTION_PROVEEDORES = 2;
    public static final int GESTION_PRODUCTOS = 3;
    public static final int GESTION_FACTURACION = 4;

    public static final int CREAR = 1;
    public static final int BUSCAR = 2;
    public static final int ACTUALIZAR = 3;
    public static final int ELIMINAR = 4;
    public static final int LISTAR = 5;

    public static final int SI = 1;
    public static final int NO = 2;

    public static final String ARCHIVO_CSV_CLIENTES = "./clientes.csv";
    public static final String ARCHIVO_CSV_PROVEEDORES = "./proveedores.csv";
    public static final String ARCHIVO_CSV_PRODUCTOS = "./productos.csv";
    public static final String ARCHIVO_CSV_FACTURAS = "./facturas.csv";

    public static final char SEPARADOR = ';';

    public static Scanner teclado;

    public static void main(String[] args) {
        teclado = new Scanner(System.in);

        List<Cliente> clientes = new ArrayList<>();
        List<Proveedor> proveedores = new ArrayList<>();
        List<Producto> productos = new ArrayList<>();
        List<Factura> facturas = new ArrayList<>();

        Map<String, Object> inventario = getData.cargarDatos();

        if (inventario.get(CLIENTES) != null) {
            clientes = (List<Cliente>) inventario.get(CLIENTES);
        }

        if (inventario.get(PROVEEDORES) != null) {
            proveedores = (List<Proveedor>) inventario.get(PROVEEDORES);
        }

        if (inventario.get(PRODUCTOS) != null) {
            productos = (List<Producto>) inventario.get(PRODUCTOS);
        }

        if (inventario.get(FACTURAS) != null) {
            facturas = (List<Factura>) inventario.get(FACTURAS);
        }

        long opcion;
        long opcionSubmenu;

        Cliente cliente;
        Proveedor proveedor;
        Producto producto;
        Factura factura;

        do {
            do {
                mostrarMenuPrincipal();
                opcion = capturarNumeroEntero("Digite la operaci??n a realizar");

                if (opcion < SALIR || opcion > GESTION_FACTURACION) {
                    mostrarMensaje("MENSAJE: Debe digitar un valor entre 0 y 5.");
                }
            } while (opcion < SALIR || opcion > GESTION_FACTURACION);

            if (opcion == SALIR) {
                break;
            }

            System.out.println();

            switch ((int) opcion) {
                case GESTION_CLIENTES:
                    do {
                        do {
                            mostrarSubmenu("Clientes");
                            opcionSubmenu = capturarNumeroEntero("Digite la operaci??n a realizar");

                            if (opcionSubmenu < SALIR || opcionSubmenu > LISTAR) {
                                mostrarMensaje("MENSAJE: Debe digitar un valor entre 0 y 5.");
                                continuar();
                            }
                        } while (opcionSubmenu < SALIR || opcionSubmenu > LISTAR);

                        if (opcionSubmenu == SALIR) {
                            break;
                        }

                        switch ((int) opcionSubmenu) {
                            case CREAR:
                                cliente = Cliente.crearCliente(clientes);

                                clientes.add(cliente);
                                getData.guardarDatosInventario(clientes, proveedores, productos, facturas);

                                break;
                            case BUSCAR:
                                if (!clientes.isEmpty()) {
                                    cliente = Cliente.buscarCliente(clientes);

                                    if (cliente != null) {
                                        Cliente.mostrarDatosCliente(cliente);
                                    } else {
                                        mostrarMensaje(
                                                "No se encontr?? un cliente con el n??mero de c??dula especificado.");
                                    }
                                } else {
                                    mostrarMensaje(
                                            "MENSAJE: A??n no se ha creado un cliente. La b??squeda no se puede efectuar.");
                                }

                                break;
                            case ACTUALIZAR:
                                if (!clientes.isEmpty()) {
                                    cliente = Cliente.buscarCliente(clientes);

                                    if (cliente != null) {
                                        Cliente.actualizarCliente(cliente);
                                        Cliente.mostrarDatosCliente(cliente);
                                        getData.guardarDatosInventario(clientes, proveedores, productos, facturas);
                                    } else {
                                        mostrarMensaje(
                                                "No se encontr?? un cliente con el n??mero de c??dula especificado.");
                                    }
                                } else {
                                    mostrarMensaje(
                                            "MENSAJE: A??n no se ha creado un cliente. La actualizaci??n no se puede efectuar.");
                                }
                                break;
                            case ELIMINAR:
                                if (!clientes.isEmpty()) {
                                    Cliente.eliminarCliente(clientes, facturas);
                                    getData.guardarDatosInventario(clientes, proveedores, productos, facturas);
                                } else {
                                    mostrarMensaje(
                                            "MENSAJE: A??n no se ha creado un cliente. La eliminaci??n no se puede efectuar.");
                                }
                                break;
                            case LISTAR:
                                Cliente.listarClientes(clientes);
                                break;
                        }

                        continuar();
                    } while (opcionSubmenu != SALIR);

                    break;
                case GESTION_PROVEEDORES:
                    do {
                        do {
                            mostrarSubmenu("Proveedores");
                            opcionSubmenu = capturarNumeroEntero("Digite la operaci??n a realizar");

                            if (opcionSubmenu < SALIR || opcionSubmenu > LISTAR) {
                                mostrarMensaje("MENSAJE: Debe digitar un valor entre 0 y 5.");
                                continuar();
                            }
                        } while (opcionSubmenu < SALIR || opcionSubmenu > LISTAR);

                        if (opcionSubmenu == SALIR) {
                            break;
                        }

                        switch ((int) opcionSubmenu) {
                            case CREAR:
                                proveedor = Proveedor.crearProveedor(proveedores);

                                proveedores.add(proveedor);
                                getData.guardarDatosInventario(clientes, proveedores, productos, facturas);

                                break;
                            case BUSCAR:
                                if (!proveedores.isEmpty()) {
                                    proveedor = Proveedor.buscarProveedor(proveedores);

                                    if (proveedor != null) {
                                        Proveedor.mostrarDatosProveedor(proveedor);
                                    } else {
                                        mostrarMensaje("No se encontr?? un proveedor con el ID especificado.");
                                    }
                                } else {
                                    mostrarMensaje(
                                            "MENSAJE: A??n no se ha creado un proveedor. La b??squeda no se puede efectuar.");
                                }

                                break;
                            case ACTUALIZAR:
                                if (!proveedores.isEmpty()) {
                                    proveedor = Proveedor.buscarProveedor(proveedores);

                                    if (proveedor != null) {
                                        Proveedor.actualizarProveedor(proveedor);
                                        Proveedor.mostrarDatosProveedor(proveedor);
                                        getData.guardarDatosInventario(clientes, proveedores, productos, facturas);
                                    } else {
                                        mostrarMensaje("MENSAJE: No existe un proveedor con el ID suministrado.");
                                    }
                                } else {
                                    mostrarMensaje(
                                            "MENSAJE: A??n no se ha creado un proveedor. La actualizaci??n no se puede efectuar.");
                                }
                                break;
                            case ELIMINAR:
                                if (!proveedores.isEmpty()) {
                                    Proveedor.eliminarProveedor(proveedores, productos);
                                    getData.guardarDatosInventario(clientes, proveedores, productos, facturas);
                                } else {
                                    mostrarMensaje(
                                            "MENSAJE: A??n no se ha creado un proveedor. La eliminaci??n no se puede efectuar.");
                                }
                                break;
                            case LISTAR:
                                Proveedor.listarProveedores(proveedores);
                                break;
                        }

                        continuar();
                    } while (opcionSubmenu != SALIR);
                    break;
                case GESTION_PRODUCTOS:
                    do {
                        do {
                            mostrarSubmenu("Productos");
                            opcionSubmenu = capturarNumeroEntero("Digite la operaci??n a realizar");

                            if (opcionSubmenu < SALIR || opcionSubmenu > LISTAR) {
                                mostrarMensaje("MENSAJE: Debe digitar un valor entre 0 y 5.");
                                continuar();
                            }
                        } while (opcionSubmenu < SALIR || opcionSubmenu > LISTAR);

                        if (opcionSubmenu == SALIR) {
                            break;
                        }

                        switch ((int) opcionSubmenu) {
                            case CREAR:
                                if (!proveedores.isEmpty()) {
                                    producto = Producto.crearProducto(productos, proveedores);

                                    productos.add(producto);
                                    getData.guardarDatosInventario(clientes, proveedores, productos, facturas);

                                    mostrarMensaje("MENSAJE: Se ha creado el nuevo producto.");
                                } else {
                                    mostrarMensaje("MENSAJE: Antes de crear un producto, debe crear un proveedor.");
                                }

                                break;
                            case BUSCAR:
                                if (!productos.isEmpty()) {
                                    producto = Producto.buscarProducto(productos);

                                    if (producto != null) {
                                        Producto.mostrarDatosProducto(producto);
                                    } else {
                                        mostrarMensaje("MENSAJE: No se encontr?? un producto con el ID especificado.");
                                    }
                                } else {
                                    mostrarMensaje(
                                            "MENSAJE: A??n no se ha creado un producto. La b??squeda no se puede efectuar.");
                                }

                                break;
                            case ACTUALIZAR:
                                if (!productos.isEmpty()) {
                                    producto = Producto.buscarProducto(productos);

                                    if (producto != null) {
                                        Producto.actualizarProducto(producto, proveedores);
                                        Producto.mostrarDatosProducto(producto);
                                        getData.guardarDatosInventario(clientes, proveedores, productos, facturas);
                                    } else {
                                        mostrarMensaje("MENSAJE: No existe un proveedor con el ID especificado.");
                                    }
                                } else {
                                    mostrarMensaje(
                                            "MENSAJE: A??n no se ha creado un producto. La actualizaci??n no se puede efectuar.");
                                }
                                break;
                            case ELIMINAR:
                                if (!productos.isEmpty()) {
                                    Producto.eliminarProducto(productos, facturas);
                                    getData.guardarDatosInventario(clientes, proveedores, productos, facturas);
                                } else {
                                    mostrarMensaje(
                                            "MENSAJE: A??n no se ha creado un producto. La eliminaci??n no se puede efectuar.");
                                }
                                break;
                            case LISTAR:
                                Producto.listarProductos(productos);
                                break;
                        }

                        continuar();
                    } while (opcionSubmenu != SALIR);
                    break;
                case GESTION_FACTURACION:
                    do {
                        do {
                            mostrarSubmenuFacturacion();
                            opcionSubmenu = capturarNumeroEntero("Digite la operaci??n a realizar");

                            if (opcionSubmenu < SALIR || opcionSubmenu > ELIMINAR) {
                                mostrarMensaje("MENSAJE: Debe digitar un valor entre 0 y 2.");
                                continuar();
                            }
                        } while (opcionSubmenu < SALIR || opcionSubmenu > BUSCAR);

                        if (opcionSubmenu == SALIR) {
                            break;
                        }

                        switch ((int) opcionSubmenu) {
                            case CREAR:
                                if (!clientes.isEmpty()) {
                                    if (!productos.isEmpty()) {
                                        factura = Factura.crearFactura(clientes, productos, facturas);

                                        facturas.add(factura);

                                        Factura.mostrarDatosFactura(factura, clientes, productos);
                                    } else {
                                        mostrarMensaje(
                                                "MENSAJE: No se puede crear una factura mientras que no hallan productos.");
                                    }
                                } else {
                                    mostrarMensaje(
                                            "MENSAJE: No se puede crear una factura mientras que no hallan clientes.");
                                }

                                break;
                            case BUSCAR:
                                if (!facturas.isEmpty()) {
                                    factura = Factura.buscarFactura(facturas);

                                    if (factura != null) {
                                        Factura.mostrarDatosFactura(factura, clientes, productos);
                                    } else {
                                        mostrarMensaje("MENSAJE: No se encontr?? una factura con el ID especificado.");
                                    }
                                } else {
                                    mostrarMensaje("MENSAJE: A??n no se han creado facturas.");
                                }

                                break;
                        }

                        continuar();
                    } while (opcionSubmenu != SALIR);
                    break;
            }

            continuar();

        } while (opcion != SALIR);

        System.out.println();

        mostrarMensaje("El programa ha terminado.");

        continuar();

        if (!clientes.isEmpty()) {
            do {
                System.out.println("Guardar datos");
                System.out.println("1. S??");
                System.out.println("2. No");
                opcion = capturarNumeroEntero("??Desea guardar los datos del inventario?: ");

                if (opcion == SI || opcion == NO) {
                    break;
                } else {
                    mostrarMensaje("MENSAJE: Debe responder S?? (1) o No (2).");
                    continuar();
                }
            } while (true);

            if (opcion == SI) {
                getData.guardarDatosInventario(clientes, proveedores, productos, facturas);
                System.out.println();
                System.out.println("Se han guardado todos los datos del inventario.");
                System.out.println();
            }
        }
    }

    public static void mostrarMenuPrincipal() {
        System.out.println("=== MEN?? PRINCIPAL ===");
        System.out.println("1. Gesti??n Clientes");
        System.out.println("2. Gesti??n Proveedores");
        System.out.println("3. Gesti??n Productos");
        // System.out.println("4. Gesti??n Facturaci??n");
        System.out.println("0. Salir");
    }

    public static void mostrarSubmenu(String tipoMenu) {
        System.out.printf("*** Men?? Gesti??n %s***\n", tipoMenu);
        System.out.println("1. Crear");
        System.out.println("2. Buscar");
        System.out.println("3. Actualizar");
        System.out.println("4. Eliminar");
        System.out.println("5. Listar");
        System.out.println("0. Salir");
    }

    public static void mostrarSubmenuFacturacion() {
        System.out.println("*** Men?? Gesti??n Facturaci??n ***");
        System.out.println("1. Crear");
        System.out.println("2. Buscar");
        System.out.println("0. Salir");
    }

    public static String capturarCadenaCaracteres(String mensaje) {
        String resultado;

        while (true) {
            System.out.printf("%s: ", mensaje);
            resultado = teclado.nextLine();

            if (!resultado.isEmpty()) {
                return resultado;
            }

            System.out.println("\nMENSAJE: Ha escrito una cadena vac??a. Espec??fique un valor concreto.");

            continuar();
        }
    }

    public static long capturarTelefono(String mensaje) {
        while (true) {
            try {
                System.out.printf("%s: ", mensaje);
                return Long.parseLong(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nMENSAJE: Digite un valor que corresponda con un n??mero entero.");
            }

            continuar();
        }
    }

    public static int capturarNumeroEntero(String mensaje) {
        while (true) {
            try {
                System.out.printf("%s: ", mensaje);
                return Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nMENSAJE: Digite un valor que corresponda con un n??mero entero.");
            }

            continuar();
        }
    }

    public static double capturarNumeroReal(String mensaje) {
        while (true) {
            try {
                System.out.printf("%s: ", mensaje);
                return Double.parseDouble(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nMENSAJE: Digite un valor que corresponda con un n??mero real.");
            }

            continuar();
        }
    }

    public static boolean correoElectronicoValido(String correo) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return correo.matches(regex);
    }

    public static void continuar() {
        System.out.println();
        System.out.print("Presione Enter para continuar...");
        teclado.nextLine();
        System.out.println();
    }

    public static void mostrarMensaje(String mensaje) {
        System.out.println();
        System.out.print(mensaje);
        System.out.println();
    }
}
