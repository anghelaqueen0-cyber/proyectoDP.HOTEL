package Factory;

import State.EstadoHabitacion;
/**
 * PRINCIPIO SOLID: Inversión de Dependencias (D).
 * Esta interfaz actúa como la abstracción general de todas las habitaciones.
 * El sistema no depende de clases concretas (como Simple o Doble), sino de esta interfaz.
 */
public interface TipoHabitacion {
    //identificar tipo de habitacion
    void habitacionElegida(String habitacion);
    
    // Métodos para el patron State
    //Permiten que cualquier habitacion cambie y consulte su comportamiento dinamico
    void setEstado(EstadoHabitacion nuevoEstado);
    EstadoHabitacion getEstado();
    
    //Permiten identificar individualmente cada habitación por su número físico.
    void setNumero(int numero);
    int getNumero();
    //obtener la capacidad
    int getCapacidadMaxima(); 
}