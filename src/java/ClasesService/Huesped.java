package ClasesService;

public class Huesped {
    // Atributos privados mapeados idénticamente a MySQL
    private int idHuesped; 
    private String nombre;
    private String dni;  
    private int edad;
    private String procedencia;
    private String estadoCivil;

    // Constructor vacío (Esencial para cuando listamos datos con SELECT)
    public Huesped() {
    }

    // Constructor completo
    public Huesped(String nombre, String dni, int edad, String procedencia, String estadoCivil) {
        this.nombre = nombre;
        this.dni = dni;
        this.edad = edad;
        this.procedencia = procedencia;
        this.estadoCivil = estadoCivil;
    }
    
    // GETTERS Y SETTERS 
    public int getIdHuesped() {
        return idHuesped;
    }

    public void setIdHuesped(int idHuesped) {
        this.idHuesped = idHuesped;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }
    
}