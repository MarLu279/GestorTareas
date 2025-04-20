package Servicio;

import Dominio.Tarea;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ServicioTareasArchivo implements IServicioTareas{
    private final String NOM_Archivo = "listaTareas.txt";
    private List<Tarea> tareas = new ArrayList<>();

    public ServicioTareasArchivo(){
        File archivo = new File(NOM_Archivo);
        boolean existe = false;
        try {
            existe = archivo.exists();
            if(existe){
                this.tareas = obtenerTareas();
            } else{
                Files.createFile(Paths.get(NOM_Archivo));
                System.out.println("Archivo creado");
            }
        } catch (IOException e){
            System.err.println("Error al crear el archivo");
            System.out.println(e.getMessage());
        }
    }
    //Métodos de la clase
    private List<Tarea> obtenerTareas(){
        List<Tarea> auxTareas = new ArrayList<>();
        try{
            List<String> lineas = Files.readAllLines(Paths.get(NOM_Archivo));
            for (String linea:lineas){
                String [] lineaTarea = linea.split(",");
                String id = lineaTarea[0];
                String descripcion = lineaTarea[1];
                String fecha = lineaTarea[2];
                boolean realizado = Boolean.parseBoolean(lineaTarea[3]);
                Tarea tarea = new Tarea(descripcion,fecha,realizado);
                auxTareas.add(tarea);
            }
        } catch (IOException e){
            System.err.println("Error al leer archivo");
            System.out.println(e.getMessage());
        }
        return auxTareas;
    }

    private void agregarTareaArchivo(Tarea tarea){
        boolean anexar = false;
        File archivo = new File(NOM_Archivo);
        anexar = archivo.exists();
        try(PrintWriter pw = new PrintWriter(new FileWriter(archivo,anexar))){
            pw.println(tarea.toCSV());
        } catch (IOException e){
            System.err.println("Error al agregar tareas en el archivo");
            System.out.println(e.getMessage());
        }
    }

    //Metodos de interfas
    @Override
    public void listarTareas() {
        System.out.println("*** Listado de Tareas ***");
        String lista = "";
        for (Tarea tarea:this.tareas){
            lista += tarea.toString() + "\n";
        }
        System.out.println(lista);
    }

    @Override
    public void crearTarea(Tarea tarea) {
        //añadir: para manejo en memoria
        this.tareas.add(tarea);
        //añadir: persistencia en archivo
        this.agregarTareaArchivo(tarea);

    }

    @Override
    public void buscarTareaID(Tarea tarea, String idbuscar) {
        List<Tarea> auxTareas = tareas;
        for(Tarea aux: auxTareas){
            if(aux.getIdTarea() == Integer.parseInt(idbuscar)){
                System.out.println("Tarea encontrada con Id: " + aux.getIdTarea());
                System.out.println(aux.toString());
            }
        }
    }

    @Override
    public void buscarTareaTexto(Tarea tarea, String texto) {
        List<Tarea> auxTareas = tareas;
        List<Tarea> resultado = new ArrayList<>();
        String textoLower = texto.toLowerCase();

        for(Tarea tar: auxTareas ){
            if(tar.getDescripcion().toLowerCase().contains(textoLower)){
                resultado.add(tar);
            }
        }
        resultado.forEach(System.out::println);
    }

    @Override
    public void marcarTareaCompleta(int id, boolean completa) {
        List<Tarea> auxTareas = tareas;
        String actualizado = "";
        for(Tarea tar : auxTareas){
            if(tar.getIdTarea() == id){
                tar.setCompletada(completa);
                actualizado = tar.toString();
                agregarTareaArchivo(tar);
                break;
            }
        }
        System.out.println("Tarea actualizada");
        System.out.println(actualizado);
    }

}