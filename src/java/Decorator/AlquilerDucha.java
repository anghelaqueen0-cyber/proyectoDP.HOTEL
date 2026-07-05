package Decorator;
public class AlquilerDucha extends ServicioDecorator{

    public AlquilerDucha(Servicio servicio) {
        super(servicio);
    }

    @Override
    public String getServicio() {
        return servicio.getServicio() + "\n + Alquiler de ducha caliente";
    }

    @Override
    public double getCosto() {
        return servicio.getCosto() + 5.00;
    }
    
}
