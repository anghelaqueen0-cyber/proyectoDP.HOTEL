package Decorator;
public class Estacionamiento extends ServicioDecorator{

    public Estacionamiento(Servicio servicio) {
        super(servicio);
    }

    @Override
    public String getServicio() {
        return servicio.getServicio() + "\n + estacionamiento";
    }

    @Override
    public double getCosto() {
        return servicio.getCosto() + 10.00;
    }
    
}
