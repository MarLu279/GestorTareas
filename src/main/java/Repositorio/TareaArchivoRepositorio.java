package Repositorio;

import Dominio.Tarea;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TareaArchivoRepositorio implements ITareaRepository{
    private final Path repoTareas;
    private final List<Tarea> tareas = new ArrayList<>();

    public TareaArchivoRepositorio(Path repoTareas) {
        this.repoTareas = repoTareas;
        File archivo = new File(repoTareas.toString());
        boolean existe;
        try {
            existe = archivo.exists();
            if(existe){
                obtenerTareas();
            } else{
                Files.createFile(Paths.get(repoTareas.toString()));
                System.out.println("Archivo creado con exito");
            }
        } catch (IOException e){
            System.err.println("[ERROR]: En la creaion del archivo");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Tarea> obtenerTareas() {
        try{
            Path archivo = Paths.get(repoTareas.toString());
            if(Files.exists(archivo)){
                List<String> lineas = Files.readAllLines(archivo);
                tareas.clear();
                List<Integer> idsLeidos = new ArrayList<>(); //Trakear Ids
                for(String linea : lineas){
                    if(!linea.trim().isEmpty()){
                        String[] datos = linea.split(",");
                        try{
                            int id = Integer.parseInt(datos[0]);
                            //Validar ID, ya existe en la lista
                            if(idsLeidos.contains(id)){
                                System.err.println("[ADVERTENCIA]: ID duplicado: " + id + ", Se omitira");
                                continue;
                            }
                            idsLeidos.add(id);
                            //Se crea la tarea despues de validar id, caso true crea tarea
                            String descripcion = datos[1];
                            String fecha = datos[2];
                            boolean completada = Boolean.parseBoolean(datos[3]);
                            Tarea tarea = new Tarea(id,descripcion, fecha, completada);
                            tareas.add(tarea);
                        } catch (NumberFormatException e){
                            System.err.println("[ERROR]: ID no valido: " + datos[0] + ". Se omitira");
                        }
                    }
                }
            }
        } catch (IOException e){
            System.err.println("[ERROR]: al leer archivo");
            System.out.println(e.getMessage());
        }
        return tareas;
    }

    @Override
    public void guardarTareas(List<Tarea> tareas) {
        try(PrintWriter pw = new PrintWriter(new FileWriter(repoTareas.toString()))){
            for(Tarea tarea : tareas){
                pw.println(tarea.toCSV());
            }
        } catch (IOException e){
            System.err.println("[ERROR]: al guardar tareas");
            System.out.println(e.getMessage());
        }
    }
}
