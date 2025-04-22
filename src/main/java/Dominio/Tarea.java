package Dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

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
        setDescripcion(descripcion);
        setFechaCreacion(fechaCreacion);
        this.completada = completada;
    }

    public int getIdTarea(){
        return this.idTarea;
    }

    public String getDescripcion(){
        return this.descripcion;
    }
    public void setDescripcion(String descripcion){
        if(descripcion == null || descripcion.trim().isEmpty()){
            throw new IllegalArgumentException("La descripcion no puede estar vacia");
        }
        this.descripcion = descripcion;
    }

    public String getFechaCreacion(){
        return fechaCreacion;
    }
    public void setFechaCreacion(String fechaCreacion){
        if(fechaCreacion == null || fechaCreacion.trim().isEmpty()){
            throw new IllegalArgumentException("La fecha no puede estas vacia");
        }
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try{
            LocalDate.parse(fechaCreacion, formato);
            this.fechaCreacion = fechaCreacion;
        }catch (DateTimeParseException e){
            throw new IllegalArgumentException("Formato de fecha invalido. Usar dd/MM/yyyy");
        }
    }

    public boolean isCompletada(){
        return this.completada;
    }
    public void setCompletada(boolean completada){
      if(this.completada && !completada){
          throw new IllegalStateException("No se puede marcar como pendiente una tarea marcada como completada");
      }
      this.completada = completada;
    }

    public String toCSV(){
        return this.idTarea + "," + this.descripcion+ "," + this.fechaCreacion + "," + this.completada;
    }

    public String toString(){
        return "Id: " + this.idTarea + ", Descripcion: " + this.descripcion + ", completada: " + this.completada;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Tarea tarea = (Tarea) o;
        return idTarea == tarea.idTarea;
    }

    @Override
    public int hashCode() {
        int result = idTarea;
        result = 31 * result + Objects.hashCode(descripcion);
        result = 31 * result + Objects.hashCode(fechaCreacion);
        result = 31 * result + Boolean.hashCode(completada);
        return result;
    }
}