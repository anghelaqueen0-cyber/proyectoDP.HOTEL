package State;

import Factory.TipoHabitacion;
/**
 * Representa una habitación libre que está lista para alquilarse.
 */
public class Disponible implements EstadoHabitacion {
    //Si está disponible, la reserva se procesa con éxito.
    @Override
    public void reservar(TipoHabitacion habitacion) {
        System.out.println("Habitacion " + habitacion.getNumero() + " reservada con exito.");
        habitacion.setEstado(new Ocupada()); // Cambia automáticamente a Ocupada
    }

    @Override
    public void liberar(TipoHabitacion habitacion) {
        // Restricción: No se puede liberar algo que ya está libre.
        System.out.println("La habitacion " + habitacion.getNumero() + " ya esta disponible.");
    }
}