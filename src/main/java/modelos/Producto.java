package modelos;

import app.Aplicacion;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private double precioCompra;
    private double precioVenta;
    private int cantidad;
    private int cantidadMinimaStock;
    private int idProveedor;

    public Producto() {
    }

    public Producto(int id, String nombre, String descripcion, double precioCompra, double precioVenta, int cantidad, int cantidadMinimaStock, int idProveedor) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.cantidad = cantidad;
        this.cantidadMinimaStock = cantidadMinimaStock;
        this.idProveedor = idProveedor;
    }

    public static void listarProductos(List<Producto> productos) {
        for (Producto producto : productos) {
            System.out.println("-----------------------------------");
            System.out.println("Id: " + producto.getId());
            System.out.println("Nombre: " + producto.getNombre());
            System.out.println("descripcion: " + producto.getDescripcion());
            System.out.println("Precio Compra: " + producto.getPrecioCompra());
            System.out.println("Precio Venta: " + producto.getPrecioVenta());
            System.out.println("Cabtidad en almacen: " + producto.getCantidadMinimaStock());
            System.out.println("Id Proveedor: " + producto.getIdProveedor());
            System.out.println("-------------Siguiente--------------");
        }
    }

    public static void eliminarProducto(List<Producto> productos, List<Factura> facturas) {
        System.out.println("--- 4. Eliminar Producto ---");

        int id;
        Producto producto;

        do {
            id = Aplicacion.capturarNumeroEntero("Digite el número de ID del producto");

            if (id <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: Debe digitar un número de ID positivo.");
                Aplicacion.continuar();
                continue;
            }
        } while (id <= 0);

        producto = buscarProductoPorId(productos, id);

        if (producto != null) {

            if (!productoEnAlgunaFactura(id, facturas)) {

                productos.remove(producto);

                Aplicacion.mostrarMensaje(String.format("Se ha eliminado el producto con el ID %d.\n", id));

            } else {
                Aplicacion.mostrarMensaje("MENSAJE: No se puede eliminar el producto porque está asociado a una factura.");
                Aplicacion.continuar();
            }

        } else {
            Aplicacion.mostrarMensaje("MENSAJE: No se ha encontrado ningún producto con el ID especificado.");
            Aplicacion.continuar();
        }
    }

    public static boolean productoEnAlgunaFactura(int id, List<Factura> facturas) {
        Integer[] idsProductos;

        for (Factura factura : facturas) {
            idsProductos = factura.getIdsProductos();

            Arrays.sort(idsProductos);
            if (Arrays.binarySearch(idsProductos, id) != -1) {
                return true;
            }
        }

        return false;
    }

    public static void actualizarProducto(Producto producto, List<Proveedor> proveedores) {
        System.out.println("--- 3. Actualizar Producto ---");

        String nombre;
        String descripcion;
        double precioCompra;
        double precioVenta;
        int cantidad;
        int cantidadMinimaStock;
        int idProveedor;

        nombre = Aplicacion.capturarCadenaCaracteres("Digite el nuevo nombre para el producto");
        descripcion = Aplicacion.capturarCadenaCaracteres("Digite la nueva descripción del producto");

        do {
            precioCompra = Aplicacion.capturarNumeroReal("Digite el nuevo precio de compra para el producto");

            if (precioCompra <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: El precio de compra no puede ser menor o igual a 0.");
                Aplicacion.continuar();
            }
        } while (precioCompra <= 0);

        do {
            precioVenta = Aplicacion.capturarNumeroReal("Digite el nuevo precio de venta para el producto");

            if (precioVenta <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: El precio de venta no puede ser menor o igual a 0.");
                Aplicacion.continuar();
                continue;
            }

            if (precioVenta <= precioCompra) {
                Aplicacion.mostrarMensaje("MENSAJE: El precio de venta no puede ser menor o igual al precio de compra.");
                precioVenta = 0;
            }
        } while (precioVenta <= 0);

        do {
            cantidad = Aplicacion.capturarNumeroEntero("Digite la nueva cantidad para el producto");

            if (cantidad <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: Debe escribir una cantidad positiva.");
                Aplicacion.continuar();
            }
        } while (cantidad <= 0);

        do {
            cantidadMinimaStock = Aplicacion.capturarNumeroEntero("Digite la neva cantidad mínima de stock para el producto");

            if (cantidadMinimaStock <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: Debe escribir una cantidad mínima de stock positiva.");
                Aplicacion.continuar();
            }
        } while (cantidadMinimaStock <= 0);

        do {
            System.out.println("Listado de Proveedores Disponibles");
            for (Proveedor proveedor : proveedores) {
                System.out.printf("%d. %s\n", proveedor.getId(), proveedor.getNombre());
            }
            idProveedor = Aplicacion.capturarNumeroEntero("Digite el ID del proveedor: ");

            if (Proveedor.buscarProveedorPorId(proveedores, idProveedor) != null) {
                break;
            } else {
                Aplicacion.mostrarMensaje(String.format("MENSAJE: No existe un proveedor con el %d especificado.\n", idProveedor));

            }

        } while (true);

        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecioCompra(precioCompra);
        producto.setPrecioVenta(precioVenta);
        producto.setCantidad(cantidad);
        producto.setCantidadMinimaStock(cantidadMinimaStock);
        producto.setIdProveedor(idProveedor);
    }

    public static void mostrarDatosProducto(Producto producto) {
        System.out.println();
        System.out.println("Datos del producto");
        System.out.println("ID: " + producto.getId());
        System.out.println("Nombre: " + producto.getNombre());
        System.out.println("Descripción: " + producto.getDescripcion());
        System.out.println("Precio de compra: " + producto.getPrecioCompra());
        System.out.println("Precio de venta: " + producto.getPrecioVenta());
        System.out.println("Cantidad: " + producto.getCantidad());
        System.out.println("Cantidad mínima de stock: " + producto.getCantidadMinimaStock());
        System.out.println("ID Proveedor: " + producto.getIdProveedor());
    }

    public static Producto buscarProducto(List<Producto> productos) {
        System.out.println("--- 2. Buscar Producto ---");

        int id;

        do {
            id = Aplicacion.capturarNumeroEntero("Digite el número de ID del producto");

            if (id <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: Debe digitar un número de ID positivo.");
                Aplicacion.continuar();
                continue;
            }
        } while (id <= 0);

        return buscarProductoPorId(productos, id);
    }

    public static Producto crearProducto(List<Producto> productos, List<Proveedor> proveedores) {
        System.out.println("--- 1. Crear Producto ---");

        int id;
        String nombre;
        String descripcion;
        Producto producto;
        double precioCompra;
        double precioVenta;
        int cantidad;
        int cantidadMinimaStock;
        int idProveedor;

        do {
            id = Aplicacion.capturarNumeroEntero("Digite el número de ID del nuevo producto");

            if (id <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: Debe digitar un número de ID positivo.");
                Aplicacion.continuar();
                continue;
            }

            producto = buscarProductoPorId(productos, id);

            if (producto != null) {
                Aplicacion.mostrarMensaje("MENSAJE: Ya existe un producto con el ID especificado");
                Aplicacion.continuar();
                id = 0;
            }
        } while (id <= 0);

        nombre = Aplicacion.capturarCadenaCaracteres("Digite el nombre para el nuevo producto");
        descripcion = Aplicacion.capturarCadenaCaracteres("Digite la descripción del nuevo producto");

        do {
            precioCompra = Aplicacion.capturarNumeroReal("Digite el precio de compra para el nuevo producto");

            if (precioCompra <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: El precio de compra no puede ser menor o igual a 0.");
                Aplicacion.continuar();
            }
        } while (precioCompra <= 0);

        do {
            precioVenta = Aplicacion.capturarNumeroReal("Digite el precio de compra para el nuevo producto");

            if (precioVenta <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: El precio de compra no puede ser menor o igual a 0.");
                Aplicacion.continuar();
                continue;
            }

            if (precioVenta <= precioCompra) {
                Aplicacion.mostrarMensaje("MENSAJE: El precio de ventana no puede ser menor o igual al precio de compra.");
                Aplicacion.continuar();
                precioVenta = 0;
            }
        } while (precioVenta <= 0);

        do {
            cantidad = Aplicacion.capturarNumeroEntero("Digite la cantidad para el nuevo producto");

            if (cantidad <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: Debe escribir una cantidad positiva.");
                Aplicacion.continuar();
            }
        } while (cantidad <= 0);

        do {
            cantidadMinimaStock = Aplicacion.capturarNumeroEntero("Digite la cantidad mínima de stock para el nuevo producto");

            if (cantidadMinimaStock <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: Debe escribir una cantidad mínima de stock positiva.");
                Aplicacion.continuar();
            }
        } while (cantidadMinimaStock <= 0);

        do {
            System.out.println("Listado de Proveedores Disponibles");
            for (Proveedor proveedor : proveedores) {
                System.out.printf("%d. %s\n", proveedor.getId(), proveedor.getNombre());
            }
            idProveedor = Aplicacion.capturarNumeroEntero("Digite el ID del proveedor");

            if (idProveedor <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: El ID de producto no puede ser un número negativo o igual a cero.");
                Aplicacion.continuar();
                continue;
            }

            if (Proveedor.buscarProveedorPorId(proveedores, idProveedor) != null) {
                break;
            } else {
                Aplicacion.mostrarMensaje(
                        String.format("MENSAJE: No existe un proveedor con el ID %d especificado.\n", idProveedor));
                Aplicacion.continuar();
            }

        } while (true);

        return new Producto(id, nombre, descripcion, precioCompra, precioVenta, cantidad, cantidadMinimaStock,
                idProveedor);
    }

    public static Producto buscarProductoPorId(List<Producto> productos, int id) {
        return productos.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public static List<Producto> buscarProductosPorIdProveedor(List<Producto> productos, int idProveedor) {
        return productos.stream().filter(p -> p.getIdProveedor() == idProveedor).collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidadMinimaStock() {
        return cantidadMinimaStock;
    }

    public void setCantidadMinimaStock(int cantidadMinimaStock) {
        this.cantidadMinimaStock = cantidadMinimaStock;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }
}
