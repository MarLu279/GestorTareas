package Dominio;

public class Tarea {
    private int idTarea;
    private String descripcion;
    private String fechaCreacion;
    private boolean completada;
    private static int contador;

    public Tarea(){
        this.idTarea = ++contador;
    }
    public Tarea(String descripcion, String fechaCreacion, boolean completada){
        this();
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.completada = completada;
    }

    public Tarea (int idTarea){
        this.idTarea = idTarea;
    }

    public int getIdTarea(){
        return this.idTarea;
    }

    public String getDescripcion(){
        return this.descripcion;
    }
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getFechaCreacion(){
        return this.fechaCreacion;
    }
    public void setFechaCreacion(String fechaCreacion){
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isCompletada(){
        return this.completada;
    }
    public void setCompletada(boolean completada){
        this.completada = completada;
    }

    public String toCSV(){
        return this.idTarea + "," + this.descripcion+ "," + this.fechaCreacion + "," + this.completada;
    }

    public String toString(){
        return "Id: " + this.idTarea + ", Descripcion: " + this.descripcion + ", completada: " + this.completada;
    }
}
