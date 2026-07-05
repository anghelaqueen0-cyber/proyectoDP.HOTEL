package Decorator;
public class DesayunoHabitacion extends ServicioDecorator{

    public DesayunoHabitacion(Servicio servicio) {
        super(servicio);
    }

    @Override
    public String getServicio() {
        return servicio.getServicio() + "\n + Desayuno en la habitacion";
    }

    @Override
    public double getCosto() {
        return servicio.getCosto() + 20.00;
    }
    
}
