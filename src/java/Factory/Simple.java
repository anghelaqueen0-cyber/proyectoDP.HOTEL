package Factory;

import State.EstadoHabitacion;
import State.Disponible;

/**
 * Clase concreta que implementa la interfaz TipoHabitacion. Almacena de forma
 * encapsulada sus propios datos de estado y número.
 */
public class Simple implements TipoHabitacion {

    // Cada habitación nace con el estado inicial "Disponible"
    private EstadoHabitacion estadoActual = new Disponible();
    private int numero;

    @Override
    public void habitacionElegida(String habitacion) {
        System.out.println("Habitacion simple de una cama elegida");
    }

    // Permite al patrón State modificar el estado internamente
    @Override
    public void setEstado(EstadoHabitacion nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    // Permite recuperar el estado para llamar a reservar() o liberar()
    @Override
    public EstadoHabitacion getEstado() {
        return this.estadoActual;
    }

    @Override
    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public int getNumero() {
        return this.numero;
    }

    @Override
    public int getCapacidadMaxima() {
        return 2; // Una habitación simple puede ser para dos personas
    }
}
