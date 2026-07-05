package State;

import Factory.TipoHabitacion;
/**
 * Esta interfaz define el "contrato de comportamiento".
 * Cualquier estado del hotel debe saber obligatoriamente qué hacer al "reservar" o "liberar".
 */
public interface EstadoHabitacion {
    // Reciben como parámetro a la habitación (el contexto) para poder alterar su estado interno.
    void reservar(TipoHabitacion habitacion);
    void liberar(TipoHabitacion habitacion);
}