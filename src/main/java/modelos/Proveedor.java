package modelos;

import app.Aplicacion;

import java.util.List;

public class Proveedor {
    private int id;
    private String nombre;
    private String telefono;
    private String direccion;

    public Proveedor() {
    }

    public static void listarProveedores(List<Proveedor> proveedores) {
        for (Proveedor proveedor : proveedores) {
            System.out.println("-----------------------------------");
            System.out.println("Id: " + proveedor.getId());
            System.out.println("Nombre: " + proveedor.getNombre());
            System.out.println("Telefono: " + proveedor.getTelefono());
            System.out.println("Direccion: " + proveedor.getDireccion());
            System.out.println("-------------Siguiente--------------");
        }
    }

    public Proveedor(int id, String nombre, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public static void eliminarProveedor(List<Proveedor> proveedores, List<Producto> productos) {

        int id;
        Proveedor proveedor;

        do {
            id = Aplicacion.capturarNumeroEntero("Digite el número de ID del proveedor");

            if (id <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: Debe digitar un número de ID positivo.");
                Aplicacion.continuar();
                continue;
            }
        } while (id <= 0);

        proveedor = buscarProveedorPorId(proveedores, id);

        if (proveedor != null) {
            List<Producto> productosProveedor = Producto.buscarProductosPorIdProveedor(productos, id);

            if (productosProveedor.isEmpty()) {
                proveedores.remove(proveedor);

                Aplicacion.mostrarMensaje(String.format("MENSAJE: Se ha eliminado el proveedor con ID %d.\n", id));
            } else {
                Aplicacion.mostrarMensaje(
                        "MENSAJE: No puede eliminar este proveedor. Tiene asociado uno o más productos.");
                Aplicacion.continuar();
            }
        } else {
            Aplicacion.mostrarMensaje("MENSAJE: No existe un proveedor con el número de ID especificado.");
            Aplicacion.continuar();
        }
    }

    public static void actualizarProveedor(Proveedor proveedor) {
        System.out.println("--- 3. Actualizar Proveedor ---");

        String nombre;
        long telefono;
        String direccion;

        nombre = Aplicacion.capturarCadenaCaracteres("Digite el nuevo nombre del proveedor");

        do {
            telefono = Aplicacion.capturarTelefono("Digite el nuevo número de teléfono proveedor");

            if (String.valueOf(telefono).length() < 10 || String.valueOf(telefono).length() > 10) {
                Aplicacion.mostrarMensaje(
                        "MENSAJE: El número de teléfono debe ser un valor positivo igual a 10 digitos.");
                Aplicacion.continuar();
            }
        } while (String.valueOf(telefono).length() < 10 || String.valueOf(telefono).length() > 10);

        direccion = Aplicacion.capturarCadenaCaracteres("Digite la nueva dirección del proveedor");

        proveedor.setNombre(nombre);
        proveedor.setTelefono(String.valueOf(telefono));
        proveedor.setDireccion(direccion);
    }

    public static void mostrarDatosProveedor(Proveedor proveedor) {
        System.out.println("Datos del proveedor");
        System.out.println("ID: " + proveedor.getId());
        System.out.println("Nombre: " + proveedor.getNombre());
        System.out.println("Teléfono: " + proveedor.getTelefono());
        System.out.println("Dirección: " + proveedor.getDireccion());
    }

    public static Proveedor buscarProveedor(List<Proveedor> proveedores) {
        System.out.println("--- 2. Buscar Proveedor ---");

        int id;

        do {
            id = Aplicacion.capturarNumeroEntero("Digite el número ID del proveedor");

            if (id <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: Debe digitar un número de ID positivo.");
                Aplicacion.continuar();
                continue;
            }
        } while (id <= 0);

        return buscarProveedorPorId(proveedores, id);
    }

    public static Proveedor crearProveedor(List<Proveedor> proveedores) {
        System.out.println();
        System.out.println("--- 1. Crear Proveedor ---");

        int id;
        Proveedor proveedor;
        String nombre;
        long telefono;
        String direccion;

        do {
            id = Aplicacion.capturarNumeroEntero("Digite el número ID del nuevo proveedor");

            if (id <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: Debe digitar un número de ID positivo.");
                Aplicacion.continuar();
                continue;
            }

            proveedor = buscarProveedorPorId(proveedores, id);

            if (proveedor != null) {
                Aplicacion.mostrarMensaje("MENSAJE: Ya existe un proveedor con el ID especificado");
                Aplicacion.continuar();
                id = 0;
            }
        } while (id <= 0);

        nombre = Aplicacion.capturarCadenaCaracteres("Digite el nombre para el nuevo proveedor");

        do {
            telefono = Aplicacion.capturarTelefono("Digite el número de teléfono del nuevo proveedor");

            if (String.valueOf(telefono).length() < 10 || String.valueOf(telefono).length() > 10) {
                Aplicacion.mostrarMensaje("MENSAJE: El número de teléfono debe ser un valor positivo de 10 digitos.");
                Aplicacion.continuar();
            }
        } while (String.valueOf(telefono).length() < 10 || String.valueOf(telefono).length() > 10);

        direccion = Aplicacion.capturarCadenaCaracteres("Digite la dirección del nuevo proveedor");

        return new Proveedor(id, nombre, String.valueOf(telefono), direccion);
    }

    public static Proveedor buscarProveedorPorId(List<Proveedor> proveedores, int id) {
        return proveedores.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
