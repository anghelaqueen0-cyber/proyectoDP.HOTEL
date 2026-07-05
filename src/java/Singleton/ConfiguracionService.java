package Singleton;
public class ConfiguracionService {
    
    // Variable estática que guarda la única instancia
    private static ConfiguracionService instancia;
    // Variable de configuración
    private final String nombreHotel;

    // Constructor privado: evita crear objetos con "new"
    private ConfiguracionService() {
        // Configuración inicial del sistema
        nombreHotel = "Hotel Hindu";
    }

    // Método Singleton: devuelve siempre la misma instancia
    public static ConfiguracionService getInstancia() {    
        // Si no existe, se crea
        if (instancia == null) {
            instancia = new ConfiguracionService();
        }
        // Retorna la única instancia
        return instancia;
    }
    
    // Método getter para obtener el nombre del hotel
    public String getNombreHotel() {
        return nombreHotel;
    }
}