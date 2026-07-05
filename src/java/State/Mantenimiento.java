package State;

import Factory.TipoHabitacion;
/**
 * Representa una habitación que está siendo limpiada por el personal.
 */
public class Mantenimiento implements EstadoHabitacion {
    @Override
    public void reservar(TipoHabitacion habitacion) {
        //El sistema bloquea las reservas comerciales mientras se limpia.
        System.out.println("Alerta: No se puede reservar. La habitacion " + habitacion.getNumero() + " esta en MANTENIMIENTO.");
    }

    @Override
    public void liberar(TipoHabitacion habitacion) {
        //El personal termina de limpiar.
        System.out.println("Mantenimiento terminado. La habitacion " + habitacion.getNumero() + " vuelve a estar disponible.");
        habitacion.setEstado(new Disponible()); // Vuelve al estado inicial
    }
}