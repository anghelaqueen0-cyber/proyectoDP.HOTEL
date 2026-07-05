package Singleton;
public class SeguridadService {
    //instancia unica
    private static SeguridadService instancia;
    //usuario y contraseña
    private String usuario;
    private String contraseña;
    //constructor privador
    private SeguridadService(){
        //usuario autorizado
        usuario="Admin";
        contraseña="123";
    }
    // metodo Singleton
    public static SeguridadService getInstancia() {
        //crear una sola vez
        if (instancia == null) {
            instancia = new SeguridadService();
        }
        return instancia;
    }
    //verificar acceso
    public boolean accesoPermitido(String usuario, String contraseña){
        return this.usuario.equals(usuario) && this.contraseña.equals(contraseña);
    }
}
