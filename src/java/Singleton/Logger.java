package Singleton;
//registrar eventos del sistema
public class Logger {
    //instancia unica
    private static Logger instancia;
    //constructor privado
    private Logger(){
        
    }
    //metodo singlenton
    public static Logger getInstancia(){
        //se crea solo una vez
        if (instancia==null) {
            instancia = new Logger();
        }
        return instancia;
    }
    //metodo para mostrar mensajes de registro
    public void log(String mensaje){
        //muestra lo q ocurrio
        System.out.println("[LOG] " + mensaje);
    }
}
