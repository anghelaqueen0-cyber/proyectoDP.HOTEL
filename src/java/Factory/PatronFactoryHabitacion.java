package Factory;
/**
 * PRINCIPIO SOLID: Responsabilidad Única (S).
 * Esta clase tiene una sola razón de existir: encapsular y centralizar la creación de objetos.
 * Ninguna otra parte del sistema usa el operador "new" para crear habitaciones.
 */
public class PatronFactoryHabitacion {

    public static TipoHabitacion crear(String tipo) {
        if (tipo.equalsIgnoreCase("simple")) {
            return new Simple();
        } else {
            if (tipo.equalsIgnoreCase("doble")) {
                return new Doble();
            } else {
                if (tipo.equalsIgnoreCase("matrimonial")) {
                    return new Matrimonial();
                }
            }
        }
        throw new IllegalArgumentException("Tipo no valido");
    }
}