package Servicio;

import Dominio.Tarea;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServicioTareasArchivo implements IServicioTareas{
    //private final String NOM_Archivo = "listaTareas.txt";
    private final Path archivoTareas;
    private List<Tarea> tareas = new ArrayList<>();

    public ServicioTareasArchivo(Path archivoTareas){
        this.archivoTareas = archivoTareas;
        //NOM_Archivo -> archivoTareas.toString()
        File archivo = new File(archivoTareas.toString());
        boolean existe = false;
        try {
            existe = archivo.exists();
            if(existe){
                obtenerTareas();
            } else{
                Files.createFile(Paths.get(archivoTareas.toString()));
                System.out.println("Archivo creado");
            }
        } catch (IOException e){
            System.err.println("Error al crear el archivo");
            System.out.println(e.getMessage());
        }
    }
    //Métodos de la clase
    private void obtenerTareas(){
        try{
            //NOM_Archivo -> archivoTareas.toString()
            if (Files.exists(Paths.get(archivoTareas.toString()))){
                List<String> lineas = Files.readAllLines(Paths.get(archivoTareas.toString()));
                tareas.clear();//limpriar lista existente (memoria)
                List<Integer> idsLeidos = new ArrayList<>(); //Trakear IDs

                for (String linea : lineas){
                    if (!linea.trim().isEmpty()){
                        String[] datos = linea.split(",");
                        // validar Id, que sea un numero
                        try{
                            int id = Integer.parseInt(datos[0]);
                            //validar id, ya existe el id en la lista
                            if (idsLeidos.contains(id)){
                                System.err.println("[ADVERTENCIA] ID duplicado: " + id + ". Se omitira.");
                                continue; //Saltar tarea, continuer al siguiente id
                            }
                            idsLeidos.add(id);
                            // Se crea la tarea despues de validar el id, true crea tarea
                            String descripcion = datos[1];
                            String fecha = datos[2];
                            boolean completada = Boolean.parseBoolean(datos[3]);
                            Tarea tarea = new Tarea(descripcion, fecha, completada);
                            tarea.setIdTarea(id);
                            tareas.add(tarea);
                        } catch (NumberFormatException e){
                            System.err.println("[ERROR] Id no valido: " + datos[0] + ". Se omitira");
                        }
                    }
                }
                //Actualiza el contador estatico con el id mas alto
                if (!idsLeidos.isEmpty()){
                    int maxId = Collections.max(idsLeidos);
                    Tarea.setContador(maxId + 1);
                }
            }
        } catch (IOException e){
            System.err.println("Error al leer archivo");
            System.out.println(e.getMessage());
        }
    }

    private void agregarTareaArchivo(Tarea tarea){
        //NOM_Archivo -> archivoTareas.toString()
        boolean anexar = false;
        File archivo = new File(archivoTareas.toString());
        anexar = archivo.exists();
        try(PrintWriter pw = new PrintWriter(new FileWriter(archivo,anexar))){
            pw.println(tarea.toCSV());
        } catch (IOException e){
            System.err.println("Error al agregar tareas en el archivo");
            System.out.println(e.getMessage());
        }
    }

    private void guardarTareasArchivo(){
        //NOM_Archivo -> archivoTareas.toString()
        try(PrintWriter pw = new PrintWriter(new FileWriter(archivoTareas.toString()))){
            tareas.forEach(t -> pw.println(t.toCSV()));
        } catch (IOException e){
            System.err.println("Error al guardar tareas:" + e.getMessage());
        }
    }

    //Metodos de interfas
    @Override
    public List<Tarea> listarTareas() {
        System.out.println("*** Listado de Tareas ***");
        String lista = "";
        for (Tarea tarea:this.tareas){
            lista += tarea.toString() + "\n";
        }
        System.out.println(lista);
        return null;
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
                guardarTareasArchivo();
                break;
            }
        }
        System.out.println("Tarea actualizada");
        System.out.println(actualizado);
    }

}