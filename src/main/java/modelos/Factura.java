package modelos;

import app.Aplicacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Factura {
	private static int contadorIds = 1;
	private int id;
	private Date fecha;
	private String cedulaCliente;
	private double impuesto;
	private double total;
	private List<Integer> idsProductos;
	
	public Factura() {
		id = contadorIds;
		++contadorIds;
		idsProductos = new ArrayList<>();
	}

	public Factura(String cedulaCliente, double impuesto) {
		this();
		fecha = new Date();
		this.cedulaCliente = cedulaCliente;
		this.impuesto = impuesto;
	}

    public static void mostrarDatosFactura(Factura factura, List<Cliente> clientes, List<Producto> productos) {
        System.out.println();
        System.out.println("Datos de la factura");

        System.out.println("ID: " + factura.getId());
        System.out.println("Fecha: " + factura.getFecha().toString());
        System.out.println("Total factura: $" + factura.getTotal());

        Cliente cliente = Cliente.buscarClientePorCedula(clientes, factura.getCedulaCliente());

        System.out.println("Datos del cliente:");
        System.out.println("Cédula: " + cliente.getCedula());
        System.out.printf("Nombre completo: %s %s\n", cliente.getNombres(), cliente.getApellidos());

        System.out.println();

        System.out.println("Productos comprados:");

        Producto producto;

        for (Integer id : factura.getIdsProductos()) {
            producto = Producto.buscarProductoPorId(productos, id);

            System.out.println("ID: " + id);
            System.out.println("Nombre: " + producto.getNombre());
            System.out.println("Precio: $" + producto.getPrecioVenta());

            System.out.println();
        }
    }

    public static Factura buscarFactura(List<Factura> facturas) {
        System.out.println();
        System.out.println("--- 2. Buscar Factura ---");

        int idFactura;
        Factura factura;

        do {
            System.out.println("Listado de facturas");

            for (Factura f : facturas) {
                System.out.printf("%d. %s - %s\n", f.getId(), f.getCedulaCliente(), f.getFecha().toString());
            }

            idFactura = (int) Aplicacion.capturarNumeroEntero("Digite el ID de la factura");

            if (idFactura <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: El ID de la factura debe ser un número positivo.");
                Aplicacion.continuar();
                continue;
            }

            factura = buscarFacturaPorId(facturas, idFactura);

            if (factura != null) {
                break;
            } else {
                Aplicacion.mostrarMensaje("MENSAJE: No se ha encontrado una factura con el ID especificado.");
                Aplicacion.continuar();
            }
        } while (true);

        return factura;
    }

    public static Factura buscarFacturaPorId(List<Factura> facturas, int idFactura) {
        return facturas.stream().filter(f -> f.getId() == idFactura).findFirst().orElse(null);
    }

    public static Factura crearFactura(List<Cliente> clientes, List<Producto> productos, List<Factura> facturas) {
        System.out.println();
        System.out.println("--- 1. Crear Factura ---");

        String cedula;
        int numeroCedula;
        Cliente cliente;
        List<Integer> idsProductos = new ArrayList<>();
        int idProducto;
        double total = 0;
        Producto producto;
        int cantidad;
        int impuesto;

        do {
            System.out.println("Listado de clientes");
            for (Cliente c : clientes) {
                System.out.printf("%s. %s %s\n", c.getCedula(), c.getNombres(), c.getApellidos());
            }
            numeroCedula = (int) Aplicacion.capturarNumeroEntero("Digite el número de cédula del cliente");

            if (numeroCedula <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: El número de cédula no puede ser negativo.");
                Aplicacion.continuar();
                continue;
            }

            cedula = String.valueOf(numeroCedula);

            cliente = Cliente.buscarClientePorCedula(clientes, cedula);

            if (cliente != null) {
                break;
            } else {
                Aplicacion.mostrarMensaje("MENSAJE: No existe un cliente con el número de cédula especificado.");
                Aplicacion.continuar();
            }

        } while (true);

        System.out.println();

        do {
            System.out.println("Listado de productos:");
            for (Producto p : productos) {
                System.out.printf("%d. %s - $%.2f - %d unidades\n", p.getId(), p.getNombre(), p.getPrecioVenta(),
                        p.getCantidad());
            }
            System.out.println("0. Salir");
            idProducto = Aplicacion.capturarNumeroEntero("Digite el ID del producto");

            if (idProducto == Aplicacion.SALIR && !idsProductos.isEmpty()) {
                break;
            }

            if (idProducto <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: El ID del producto debe ser un número positivo.");
                continue;
            }

            producto = Producto.buscarProductoPorId(productos, idProducto);

            if (producto != null) {
                if (producto.getCantidad() > 0) {
                    do {
                        cantidad = Aplicacion.capturarNumeroEntero("Digite la cantidad a comprar de este producto");

                        if (cantidad <= 0) {
                            Aplicacion.mostrarMensaje("MENSAJE: Debe digitar una cantidad positiva.");
                            Aplicacion.continuar();
                            continue;
                        }

                        if (cantidad > producto.getCantidad()) {
                            Aplicacion.mostrarMensaje("MENSAJE: No hay cantidad suficiente de este producto. Cantidad disponible: "
                                    + producto.getCantidad());
                            Aplicacion.continuar();
                            continue;
                        }

                        break;
                    } while (true);

                    producto.setCantidad(producto.getCantidad() - cantidad);

                    total += producto.getPrecioVenta() * cantidad;

                    idsProductos.add(idProducto);
                } else {
                    Aplicacion.mostrarMensaje("MENSAJE: Este producto no cuenta con existencias.");
                    Aplicacion.continuar();
                }
            } else {
                Aplicacion.mostrarMensaje("MENSAJE: No existe un producto con el ID especificado.");
                Aplicacion.continuar();
            }

        } while (true);

        do {
            impuesto = Aplicacion.capturarNumeroEntero("Digite el impuesto para esta factura");

            if (impuesto <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: El impuesto debe ser un número positivo.");
                Aplicacion.continuar();
                continue;
            }

            if (impuesto > 100) {
                Aplicacion.mostrarMensaje("MENSAJE: El impuesto especificado no es un valor válido. Debe estar entre 1 y 100.");
                Aplicacion.continuar();
                continue;
            }

            break;
        } while (true);

        Factura nuevaFactura = new Factura(cedula, impuesto / 100.0);
        idsProductos.forEach(id -> nuevaFactura.agregarIdProducto(id));
        nuevaFactura.setTotal(total);

        return nuevaFactura;
    }

    public static Factura buscarFacturaPorCedula(List<Factura> facturas, String cedula) {
        for (Factura factura : facturas) {
            if (factura.getCedulaCliente().equals(cedula)) {
                return factura;
            }
        }

        return null;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getCedulaCliente() {
		return cedulaCliente;
	}

	public void setCedulaCliente(String cedulaCliente) {
		this.cedulaCliente = cedulaCliente;
	}

	public double getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(double impuesto) {
		this.impuesto = impuesto;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	public void agregarIdProducto(int idProducto) {
		idsProductos.add(idProducto);
	}

	public Integer[] getIdsProductos() {
		Integer[] idsProductosCopia = new Integer[idsProductos.size()];
		idsProductos.toArray(idsProductosCopia);
		
		return idsProductosCopia;
	}

	public void setIdsProductosDesdeArregloCadenas(String[] idsProductosCadenas) {
		for (String idProducto : idsProductosCadenas) {
			idsProductos.add(Integer.parseInt(idProducto));
		}
	}
}
