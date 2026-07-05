package State;

import Factory.TipoHabitacion;
/**
 * Representa una habitación con un huésped adentro en este momento.
 */
public class Ocupada implements EstadoHabitacion {
    @Override
    public void reservar(TipoHabitacion habitacion) {
        // RESTRICCIÓN DE SEGURIDAD: Evita la sobre-reserva (doble alquiler) sin usar IFs.
        System.out.println("Alerta: No se puede reservar. La habitacion " + habitacion.getNumero() + " esta OCUPADA.");
    }

    @Override
    public void liberar(TipoHabitacion habitacion) {
        //El cliente hace checkout.
        System.out.println("Habitacion " + habitacion.getNumero() + " liberada. Pasando a mantenimiento.");
        habitacion.setEstado(new Mantenimiento()); // Pasa a limpieza/mantenimiento
    }
}