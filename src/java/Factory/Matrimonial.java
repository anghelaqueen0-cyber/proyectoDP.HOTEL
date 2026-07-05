package Factory;

import State.EstadoHabitacion;
import State.Disponible;

public class Matrimonial implements TipoHabitacion {

    private EstadoHabitacion estadoActual = new Disponible();
    private int numero;

    @Override
    public void habitacionElegida(String habitacion) {
        System.out.println("Habitacion de dos plazas elegida");
    }

    @Override
    public void setEstado(EstadoHabitacion nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

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
        return 2; // Una habitación matrimonial puede ser para dos personas
    }
}