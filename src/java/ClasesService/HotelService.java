package ClasesService;

import Composite.Piso;
import Factory.PatronFactoryHabitacion;
import Factory.TipoHabitacion;
import Singleton.ConexionBD; // Tu conexión Singleton
import Singleton.Logger;     // Tu Logger Singleton
import State.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HotelService {

    private static HotelService instancia;
    private final List<Piso> listaPisos;

    // Constructor privado (Singleton)
    private HotelService() {
        listaPisos = new ArrayList<>();
        inicializarHotelDesdeBD();
    }

    // Obtener la instancia única (Singleton)
    public static HotelService getInstancia() {
        if (instancia == null) {
            instancia = new HotelService();
        }
        return instancia;
    }

    //CARGA DESDE BASE DE DATOS (Une Composite, Factory y State)
    private void inicializarHotelDesdeBD() {
        // Creamos los contenedores de los pisos de forma lógica (Composite)
        Piso piso2 = new Piso(2);
        Piso piso3 = new Piso(3);
        Piso piso4 = new Piso(4);
        listaPisos.add(piso2);
        listaPisos.add(piso3);
        listaPisos.add(piso4);

        String sql = "SELECT p.Numero_Piso, h.Numero, h.Tipo, h.Estado, h.Capacidad_Maxima FROM Habitacion h INNER JOIN Piso p ON h.id_piso = p.id_piso ORDER BY p.Numero_Piso, h.Numero;";

        // Usamos ConexionBD Singleton
        try (Connection con = ConexionBD.getInstancia().getConexion(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int numero = rs.getInt("Numero");
                String tipo = rs.getString("Tipo");     // 'simple', 'doble', 'matrimonial'
                String estado = rs.getString("Estado"); // 'Disponible', 'Ocupada', 'Mantenimiento'

                // 1. El Factory crea el objeto correcto según el tipo de la BD
                TipoHabitacion habitacion = PatronFactoryHabitacion.crear(tipo);
                habitacion.setNumero(numero);

                // 2. Le inyectamos el objeto Estado correspondiente (State)
                if (estado.equalsIgnoreCase("Disponible")) {
                    habitacion.setEstado(new Disponible());
                } else if (estado.equalsIgnoreCase("Ocupada")) {
                    habitacion.setEstado(new Ocupada());
                } else if (estado.equalsIgnoreCase("Mantenimiento")) {
                    habitacion.setEstado(new Mantenimiento());
                }

                // 3. El Composite lo guarda en el piso correcto según su número
                if (numero >= 200 && numero < 300) {
                    piso2.agregarHabitacion(habitacion);
                } else if (numero >= 300 && numero < 400) {
                    piso3.agregarHabitacion(habitacion);
                } else if (numero >= 400 && numero < 500) {
                    piso4.agregarHabitacion(habitacion);
                }
            }

        } catch (Exception e) {
            System.out.println("Error al cargar habitaciones desde MySQL: " + e.getMessage());
        }
    }

    //PROCESAR RESERVA
    public void reservarHabitacion(int numero, Huesped titular, String datosAcompanantes, int cantidadPersonasReal) {
        for (Piso p : listaPisos) {
            TipoHabitacion h = p.obtenerHabitacion(numero); // Busca en el Composite

            if (h != null) {
                // Validación de capacidad usando el Factory
                if (cantidadPersonasReal > h.getCapacidadMaxima()) {
                    System.out.println("ERROR: Capacidad superada. Máximo permitido: " + h.getCapacidadMaxima());
                    return;
                }

                // El patrón State valida si se puede reservar o lanza error si está ocupada/mantenimiento
                h.getEstado().reservar(h);

                // Logger Singleton
                Logger.getInstancia().log("Reserva procesada para la habitación " + numero);

                // NOTA: Aquí agregarías tu "INSERT INTO Reserva..." para guardarlo en la base de datos real
                return;
            }
        }
        System.out.println("❌ La habitación " + numero + " no existe.");
    }

    public int getCantidadHabitacionesDisponibles() {
        int contador = 0;

        // Recorremos los pisos (Composite)
        for (Piso piso : listaPisos) {
            // Recorremos las habitaciones de cada piso
            for (TipoHabitacion h : piso.getListaHabitaciones()) {

                // Evaluamos si el estado actual es "Disponible" usando instanceof
                if (h.getEstado() instanceof State.Disponible) {
                    contador++;
                }
            }
        }
        return contador;
    }

    // Permite al JSP u otras clases obtener la estructura completa del Composite
    public List<Piso> getListaPisos() {
        return this.listaPisos;
    }
}
