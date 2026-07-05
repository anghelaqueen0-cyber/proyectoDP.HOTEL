package Composite;

import Factory.TipoHabitacion;
import State.EstadoHabitacion;
import java.util.ArrayList;
import java.util.List;

/**
 * Centraliza el almacenamiento físico de las habitaciones por pisos. 
 */
public class Piso implements TipoHabitacion {

    // La lista REAL y ÚNICA donde se guardarán las habitaciones de este piso específico
    private final List<TipoHabitacion> habitaciones = new ArrayList<>();
    private int numeroPiso;

    // Constructor que define qué piso estamos instanciando (Piso 1, Piso 2, etc.)
    public Piso(int numeroPiso) {
        this.numeroPiso = numeroPiso;
    }
    // MÉTODOS PROPIOS DEL COMPOSITE (Gestión e Indexación de Habitaciones)
    //Agrega una habitación previamente fabricada (por el Factory) a este piso.
    public void agregarHabitacion(TipoHabitacion habitacion) {
        this.habitaciones.add(habitacion);
    }
    
    //Elimina una habitación del piso si es necesario reestructurar el hotel.
    public void eliminarHabitacion(TipoHabitacion habitacion) {
        this.habitaciones.remove(habitacion);
    }
    //El buscador centralizado del piso.
    public TipoHabitacion obtenerHabitacion(int numeroHabitacion) {
        for (TipoHabitacion h : habitaciones) {
            if (h.getNumero() == numeroHabitacion) {
                return h; // Retorna la habitación específica (con su estado, decoradores, etc.)
            }
        }
        return null; // Si no pertenece a este piso, retorna null
    }
    //Método auxiliar para obtener todas las habitaciones del piso de golpe
    public List<TipoHabitacion> getListaHabitaciones() {
        return this.habitaciones;
    }
    // IMPLEMENTACIÓN DE LA INTERFAZ (Operaciones en Cascada)
    @Override
    public void habitacionElegida(String habitacion) {
        System.out.println("====== REPORTE DE ESTADO: PISO " + numeroPiso + " ======");
        // Aplicando la naturaleza del Composite: ejecuta la acción sobre todas sus hojas automáticamente
        if (habitaciones.isEmpty()) {
            System.out.println("Este piso aún no tiene habitaciones registradas.");
        } else {
            for (TipoHabitacion h : habitaciones) {
                // Muestra el número, tipo de habitación y su estado actual
                System.out.print("[" + h.getNumero() + "] ");
                h.habitacionElegida(habitacion);
            }
        }
    }

    @Override
    public int getNumero() {
        return this.numeroPiso; // Para el contenedor Composite, el número representa al piso entero
    }

    @Override
    public void setNumero(int numero) {
        this.numeroPiso = numero;
    }
    // CONTROL DE EXCEPCIONES EN COMPOSITE
    
    @Override
    public void setEstado(EstadoHabitacion nuevoEstado) {
        // Operación masiva: Si se desinfecta o se cierra el piso entero por mantenimiento, 
        // altera el estado de todas las habitaciones del piso en cascada.
        for (TipoHabitacion h : habitaciones) {
            h.setEstado(nuevoEstado);
        }
    }

    @Override
    public EstadoHabitacion getEstado() {
        // Un piso completo no posee un único estado consolidado en la lógica del negocio 
        // (puede haber habitaciones ocupadas y disponibles en el mismo pasillo).
        throw new UnsupportedOperationException(
            "Operación denegada: Un piso entero no maneja un único estado. Consulte las habitaciones individualmente."
        );
    }
    
    @Override
    public int getCapacidadMaxima() {
        return 0;
    }
    
}