package Decorator;
public class TeleIncluida extends ServicioDecorator{

    public TeleIncluida(Servicio servicio) {
        super(servicio);
    }

    @Override
    public String getServicio() {
        return servicio.getServicio() + "\n + tele incluida";
    }

    @Override
    public double getCosto() {
        return servicio.getCosto() + 7.00;
    }
    
}
