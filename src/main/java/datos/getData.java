package datos;

import modelos.*;
import app.Aplicacion;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class getData {


    public static Map<String, Object> cargarDatos() {
        List<Cliente> clientes = null;
        List<Proveedor> proveedores = null;
        List<Producto> productos = null;
        List<Factura> facturas = null;

        Path rutaArchivo = Paths.get(Aplicacion.ARCHIVO_CSV_CLIENTES);

        if (Files.exists(rutaArchivo)) {
            clientes = new ArrayList<>();

            try {
                Reader reader = Files.newBufferedReader(rutaArchivo);
                CSVParser csvParser = new CSVParser(reader,
                        CSVFormat.DEFAULT
                                .withHeader("cedula", "nombres", "apellidos", "telefono", "direccion", "correo")
                                .withDelimiter(Aplicacion.SEPARADOR));

                Cliente cliente;

                List<CSVRecord> registros = csvParser.getRecords().stream().filter(r -> r.getRecordNumber() != 1)
                        .collect(Collectors.toList());

                for (CSVRecord r : registros) {
                    cliente = new Cliente(r.get("cedula"), r.get("nombres"), r.get("apellidos"), r.get("telefono"),
                            r.get("direccion"), r.get("correo"));

                    clientes.add(cliente);
                }

                csvParser.close();
            } catch (IOException e) {
                System.err.println("ERROR: " + e.getMessage());
            }
        }

        rutaArchivo = Paths.get(Aplicacion.ARCHIVO_CSV_PROVEEDORES);

        if (Files.exists(rutaArchivo)) {
            proveedores = new ArrayList<>();

            try {
                Reader reader = Files.newBufferedReader(rutaArchivo);
                CSVParser csvParser = new CSVParser(reader,
                        CSVFormat.DEFAULT.withHeader("id", "nombre", "telefono", "direccion").withDelimiter(Aplicacion.SEPARADOR));

                Proveedor proveedor;

                List<CSVRecord> registros = csvParser.getRecords().stream().filter(r -> r.getRecordNumber() != 1)
                        .collect(Collectors.toList());

                for (CSVRecord r : registros) {
                    proveedor = new Proveedor(Integer.parseInt(r.get("id")), r.get("nombre"), r.get("telefono"),
                            r.get("direccion"));

                    proveedores.add(proveedor);
                }

                csvParser.close();
            } catch (IOException e) {
                System.err.println("ERROR: " + e.getMessage());
            }
        }

        rutaArchivo = Paths.get(Aplicacion.ARCHIVO_CSV_PRODUCTOS);

        if (Files.exists(rutaArchivo)) {
            productos = new ArrayList<>();

            try {
                Reader reader = Files.newBufferedReader(rutaArchivo);
                CSVParser csvParser = new CSVParser(reader,
                        CSVFormat.DEFAULT.withHeader("id", "nombre", "descripcion", "precioCompra", "precioVenta",
                                "cantidad", "cantidadMinimaStock", "idProveedor").withDelimiter(Aplicacion.SEPARADOR));

                Producto producto;

                List<CSVRecord> registros = csvParser.getRecords().stream().filter(r -> r.getRecordNumber() != 1)
                        .collect(Collectors.toList());

                for (CSVRecord r : registros) {
                    producto = new Producto();

                    producto.setId(Integer.parseInt(r.get("id")));
                    producto.setNombre(r.get("nombre"));
                    producto.setDescripcion(r.get("descripcion"));
                    producto.setPrecioCompra(Double.parseDouble(r.get("precioCompra")));
                    producto.setPrecioVenta(Double.parseDouble(r.get("precioVenta")));
                    producto.setCantidad(Integer.parseInt(r.get("cantidad")));
                    producto.setCantidadMinimaStock(Integer.parseInt(r.get("cantidadMinimaStock")));
                    producto.setIdProveedor(Integer.parseInt(r.get("idProveedor")));

                    productos.add(producto);
                }

                csvParser.close();
            } catch (IOException e) {
                System.err.println("ERROR: " + e.getMessage());
            }
        }

        rutaArchivo = Paths.get(Aplicacion.ARCHIVO_CSV_FACTURAS);

        if (Files.exists(rutaArchivo)) {
            facturas = new ArrayList<>();

            try {
                Reader reader = Files.newBufferedReader(rutaArchivo);
                CSVParser csvParser = new CSVParser(reader,
                        CSVFormat.DEFAULT
                                .withHeader("id", "fecha", "cedulaCliente", "impuesto", "total", "idsProductos")
                                .withDelimiter(Aplicacion.SEPARADOR));

                Factura factura;

                List<CSVRecord> registros = csvParser.getRecords().stream().filter(r -> r.getRecordNumber() != 1)
                        .collect(Collectors.toList());

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                for (CSVRecord r : registros) {
                    factura = new Factura();
                    factura.setId(Integer.parseInt(r.get("id")));
                    try {
                        factura.setFecha(simpleDateFormat.parse(r.get("fecha")));
                    } catch (ParseException e) {

                    }
                    factura.setCedulaCliente(r.get("cedulaCliente"));
                    factura.setImpuesto(Double.parseDouble(r.get("impuesto")));
                    factura.setTotal(Double.parseDouble(r.get("total")));

                    String[] idsProductos = StringUtils.split(r.get("idsProductos"), ",");
                    factura.setIdsProductosDesdeArregloCadenas(idsProductos);

                    facturas.add(factura);
                }

                csvParser.close();
            } catch (IOException e) {
                System.err.println("ERROR: " + e.getMessage());
            }
        }

        Map<String, Object> inventario = new HashMap<>();
        inventario.put(Aplicacion.CLIENTES, clientes);
        inventario.put(Aplicacion.PROVEEDORES, proveedores);
        inventario.put(Aplicacion.PRODUCTOS, productos);
        inventario.put(Aplicacion.FACTURAS, facturas);

        return inventario;
    }

    public static void guardarDatosInventario(List<Cliente> clientes, List<Proveedor> proveedores,
                                              List<Producto> productos, List<Factura> facturas) {

        // CLIENTES GUARDADO
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(Aplicacion.ARCHIVO_CSV_CLIENTES));

            CSVPrinter csvPrinter = new CSVPrinter(writer,
                    CSVFormat.DEFAULT.withHeader("cedula", "nombres", "apellidos", "telefono", "direccion", "correo")
                            .withDelimiter(Aplicacion.SEPARADOR));

            for (Cliente c : clientes) {
                csvPrinter.printRecord(c.getCedula(), c.getNombres(), c.getApellidos(), c.getTelefono(),
                        c.getDireccion(), c.getCorreoElectronico());
            }

            csvPrinter.flush();
            csvPrinter.close();

        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        //PROVEEDORES GUARDADO
        if (!proveedores.isEmpty()) {
            try {
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(Aplicacion.ARCHIVO_CSV_PROVEEDORES));

                CSVPrinter csvPrinter = new CSVPrinter(writer,
                        CSVFormat.DEFAULT.withHeader("id", "nombre", "telefono", "direccion").withDelimiter(Aplicacion.SEPARADOR));

                for (Proveedor p : proveedores) {
                    csvPrinter.printRecord(p.getId(), p.getNombre(), p.getTelefono(), p.getDireccion());
                }

                csvPrinter.flush();
                csvPrinter.close();

            } catch (IOException e) {
                System.err.println("ERROR: " + e.getMessage());
            }
        }

        //PRODUCTOS GUARDADO
        if (!productos.isEmpty()) {
            try {
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(Aplicacion.ARCHIVO_CSV_PRODUCTOS));

                CSVPrinter csvPrinter = new CSVPrinter(writer,
                        CSVFormat.DEFAULT.withHeader("id", "nombre", "descripcion", "precioCompra", "precioVenta",
                                "cantidad", "cantidadMinimaStock", "idProveedor").withDelimiter(Aplicacion.SEPARADOR));

                for (Producto p : productos) {
                    csvPrinter.printRecord(p.getId(), p.getNombre(), p.getDescripcion(), p.getPrecioCompra(),
                            p.getPrecioVenta(), p.getCantidad(), p.getCantidadMinimaStock(), p.getIdProveedor());
                }

                csvPrinter.flush();
                csvPrinter.close();

            } catch (IOException e) {
                System.err.println("ERROR: " + e.getMessage());
            }
        }

        //FACTURAS GUARDADO
        if (!facturas.isEmpty()) {
            try {
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(Aplicacion.ARCHIVO_CSV_FACTURAS));

                CSVPrinter csvPrinter = new CSVPrinter(writer,
                        CSVFormat.DEFAULT
                                .withHeader("id", "fecha", "cedulaCliente", "impuesto", "total", "idsProductos")
                                .withDelimiter(Aplicacion.SEPARADOR));

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                for (Factura f : facturas) {
                    csvPrinter.printRecord(f.getId(), simpleDateFormat.format(f.getFecha()), f.getCedulaCliente(), f.getImpuesto(), f.getTotal(),
                            StringUtils.join(f.getIdsProductos(), ","));
                }

                csvPrinter.flush();
                csvPrinter.close();

            } catch (IOException e) {
                System.err.println("ERROR: " + e.getMessage());
            }
        }
    }
}
