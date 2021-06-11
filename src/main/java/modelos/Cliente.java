package modelos;

import app.Aplicacion;

import java.util.List;

public class Cliente {
	private String cedula; //Get y Set
	private String nombres;
	private String apellidos;
	private String telefono;
	private String direccion;
	private String correoElectronico;
	
	public Cliente() {
	}

	public Cliente(String cedula, String nombres, String apellidos, String telefono, String direccion,
			String correoElectronico) {
		super();
		this.cedula = cedula;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.direccion = direccion;
		this.correoElectronico = correoElectronico;
	}

    public static void eliminarCliente(List<Cliente> clientes, List<Factura> facturas) {
        int numeroCedula;
        String cedula;

        do {
            numeroCedula = Aplicacion.capturarNumeroEntero("Digite la cédula del cliente");

            if (numeroCedula <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: La cédula debe ser un número entero positivo.");
                numeroCedula = 0;
                Aplicacion.continuar();
                continue;
            }
        } while (numeroCedula <= 0);

        cedula = String.valueOf(numeroCedula);

        Cliente cliente = buscarClientePorCedula(clientes, cedula);

        if (cliente != null) {
            Factura factura = Factura.buscarFacturaPorCedula(facturas, cedula);

            if (factura == null) {

                clientes.remove(cliente);

                Aplicacion.mostrarMensaje(String.format("MENSAJE: Se ha eliminado el cliente con cédula: %s\n", cedula));

            } else {
                Aplicacion.mostrarMensaje("No se puede eliminar el cliente. Tiene una o más facturas asignadas.");
                Aplicacion.continuar();
            }
        } else {
            Aplicacion.mostrarMensaje("MENSAJE: No se encontró ningún cliente con el número de cédula especificado.");
            Aplicacion.continuar();
        }
    }

    public static void actualizarCliente(Cliente cliente) {
        System.out.println("--- 3. Actualizar Cliente ---");

        String nombres = Aplicacion.capturarCadenaCaracteres("Digite los nuevos nombres del cliente");
        String apellidos = Aplicacion.capturarCadenaCaracteres("Digite los nuevos apellidos del cliente");

        int telefono;

        do {
            telefono = Math.toIntExact(Aplicacion.capturarTelefono("Digite el nuevo número de teléfono del cliente"));

            if (telefono <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: El número de teléfono debe ser un valor positivo.");
                Aplicacion.continuar();
            }
        } while (telefono <= 0);

        String direccion = Aplicacion.capturarCadenaCaracteres("Digite la nueva dirección del cliente");
        String correoElectronico;

        while (true) {
            correoElectronico = Aplicacion.capturarCadenaCaracteres("Digite el nuevo correo electrónico del cliente");

            if (!Aplicacion.correoElectronicoValido(correoElectronico)) {
                Aplicacion.mostrarMensaje("MENSAJE: Ha digito un valor que no corresponde con un correo electrónico.");
                Aplicacion.continuar();
                continue;
            }

            break;
        }

        cliente.setNombres(nombres);
        cliente.setApellidos(apellidos);
        cliente.setTelefono(String.valueOf(telefono));
        cliente.setDireccion(direccion);
        cliente.setCorreoElectronico(correoElectronico);
    }

    public static void mostrarDatosCliente(Cliente cliente) {
        System.out.println("Datos del cliente");
        System.out.println("Cédula: " + cliente.getCedula());
        System.out.println("Nombres: " + cliente.getNombres());
        System.out.println("Apellidos: " + cliente.getApellidos());
        System.out.println("Teléfono: " + cliente.getTelefono());
        System.out.println("Dirección: " + cliente.getDireccion());
        System.out.println("Correo electrónico: " + cliente.getCorreoElectronico());
    }

    public static Cliente buscarCliente(List<Cliente> clientes) {
        System.out.println();
        System.out.println("--- 2. Buscar Cliente ---");

        int numeroCedula;
        String cedula;

        do {
            numeroCedula = Aplicacion.capturarNumeroEntero("Digite la cédula del cliente");

            if (numeroCedula <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: La cédula debe ser un número entero positivo.");
                numeroCedula = 0;
                Aplicacion.continuar();
                continue;
            }
        } while (numeroCedula <= 0);

        cedula = String.valueOf(numeroCedula);

        return buscarClientePorCedula(clientes, cedula);
    }

    public static Cliente crearCliente(List<Cliente> clientes) {
        System.out.println();
        System.out.println("--- 1. Crear Cliente ---");
        int numeroCedula;
        long telefono;
        String cedula = "";
        Cliente cliente;

        do {
            numeroCedula = Aplicacion.capturarNumeroEntero("Digite la cédula del cliente nuevo");

            if (numeroCedula <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: La cédula debe ser un número entero positivo.");
                numeroCedula = 0;
                Aplicacion.continuar();
                continue;
            }

            cedula = String.valueOf(numeroCedula);

            cliente = buscarClientePorCedula(clientes, cedula);

            if (cliente != null) {
                Aplicacion.mostrarMensaje(String.format("MENSAJE: Ya existe otro con el número de cédula: %s.\n", cedula));
                numeroCedula = 0;
            }
        } while (numeroCedula <= 0);

        String nombres = Aplicacion.capturarCadenaCaracteres("Digite los nombres del cliente nuevo");
        String apellidos = Aplicacion.capturarCadenaCaracteres("Digite los apellidos del cliente nuevo");

        do {
            telefono = Aplicacion.capturarTelefono("Digite el número de teléfono del cliente nuevo");

            if (telefono <= 0) {
                Aplicacion.mostrarMensaje("MENSAJE: El número de teléfono debe ser un valor positivo.");
                System.out.println(telefono);
                System.out.println("hola");
                Aplicacion.continuar();
            }
        } while (telefono <= 0);

        String direccion = Aplicacion.capturarCadenaCaracteres("Digite la dirección del cliente nuevo");
        String correoElectronico;

        while (true) {
            correoElectronico = Aplicacion.capturarCadenaCaracteres("Digite el correo electrónico del cliente nuevo");

            if (!Aplicacion.correoElectronicoValido(correoElectronico)) {
                Aplicacion.mostrarMensaje("MENSAJE: Ha digito un valor que no corresponde con un correo electrónico.");
                Aplicacion.continuar();
                continue;
            }

            break;
        }


        return new Cliente(cedula, nombres, apellidos, String.valueOf(telefono), direccion, correoElectronico);
    }

    public static Cliente buscarClientePorCedula(List<Cliente> clientes, String cedula) {
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                return cliente;
            }
        }

        return null;
    }

    public static void listarClientes(List<Cliente> clientes) {
        for (Cliente cliente :clientes) {
            System.out.println("-----------------------------------");
            System.out.println("Cedula: " + cliente.getCedula());
            System.out.println("Nombres: " +cliente.getNombres());
            System.out.println("Apellidos: " +cliente.getApellidos());
            System.out.println("Correo: " +cliente.getCorreoElectronico());
            System.out.println("Direccion: " +cliente.getDireccion());
            System.out.println("Telefono: " +cliente.getTelefono());
            System.out.println("-------------Siguiente--------------");
        }
    }

    public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

}
