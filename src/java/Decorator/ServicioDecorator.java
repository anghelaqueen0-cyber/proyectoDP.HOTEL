package Decorator;
abstract class ServicioDecorator implements Servicio{
    protected Servicio servicio;
    //recibe un servicio y la almacena
    public ServicioDecorator(Servicio servicio){
        this.servicio=servicio;
    }
}
