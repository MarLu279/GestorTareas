package Presentacion;

import Dominio.Tarea;
import Repositorio.ITareaRepository;
import Repositorio.TareaArchivoRepositorio;
import Servicio.IServicioTareas;
import Servicio.ServicioTarea;

import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        appTareas();
    }

    private static void appTareas(){
        ITareaRepository repo = new TareaArchivoRepositorio(Paths.get("listaTareas.txt"));
        IServicioTareas servicioTarea = new ServicioTarea(repo);
        Scanner consola = new Scanner(System.in);
        boolean bandera = false;
        listarTareas(servicioTarea);
        while (!bandera){
            int opcion = desplegarMenu(consola);
            bandera = ejecutarOpcion(servicioTarea, consola, opcion);
        }
    }

    private static int desplegarMenu(Scanner consola){
        int opcion = -1;
        while(opcion < 1 || opcion > 6){
            try{
                System.out.println("""
                        Menu:
                        1. Crear Tarea
                        2. Listar Tareas
                        3. Buscar Tareas por Id
                        4. Buscar Tareas por Descripcion
                        5. Marcar tareas realizadas
                        6. Salir
                        Opcion seleccionada: \s""");
                opcion = Integer.parseInt((consola.nextLine()));
                if(opcion < 1 || opcion > 6){
                    System.err.println("Opcion invalida, intenta de nuevo");
                }
            } catch (NumberFormatException e){
                System.err.println("Error: Ingreso un numero no valido");
            }
        }
        return opcion;
    }

    private static boolean ejecutarOpcion(IServicioTareas servicioTarea, Scanner consola, int opcion){
        boolean salir = false;
        switch (opcion){
            case 1 -> crearTarea(consola, servicioTarea);
            case 2 -> listarTareas(servicioTarea);
            case 3 -> buscarTareaId(consola, servicioTarea);
            case 4 -> buscarTareaTexto(consola, servicioTarea);
            case 5 -> marcarTarea(servicioTarea, consola);
            case 6 -> {
                System.out.println("Saliendo, Adios :D");
                salir = true;
            }
        }
        return salir;
    }

    private static void crearTarea(Scanner consola, IServicioTareas servicioTareas){
        System.out.println("Crear tarea: ");
        System.out.println("Ingresa la descripcion de la tarea: ");
        String descripcion = consola.nextLine();
        System.out.println("Ingresa fecha de la tarea: ");
        String fecha = consola.nextLine();
        System.out.println("La tarea fue realizada S/N: ");
        String realizada = consola.nextLine();
        boolean aux = realizada.equals("s");
        servicioTareas.crearTarea(new Tarea(descripcion,fecha,aux));
        System.out.println("Tarea agregada");
    }

    private static void listarTareas(IServicioTareas servicioTarea){
        System.out.println("*** Lista de Tareas ***");
        System.out.println(servicioTarea.listarTareas());
    }

    private static void buscarTareaId(Scanner consola, IServicioTareas servicioTareas){
        System.out.println("Ingresa ID a buscar: ");
        Tarea tarea = servicioTareas.buscarTareaID(consola.nextLine());
        System.out.println("Tarea encontrada: \n" + tarea.toString());
    }

    private static void buscarTareaTexto(Scanner consola, IServicioTareas servicioTareas){
        System.out.println("Ingresa la descripcion de la tarea: ");
        List<Tarea> tareasEncontradas = servicioTareas.buscarTareaTexto(consola.nextLine());
        for (Tarea tarea:tareasEncontradas){
            System.out.println(tarea.toString() + "\n");
        }
    }

    private static void marcarTarea(IServicioTareas servicioTareas, Scanner consola){
        System.out.println("Id de la tarea a completar: ");
        int id = Integer.parseInt(consola.nextLine());
        System.out.println("Escribe [S] si ya se completo la tarea");
        boolean aux = (consola.nextLine()).equals("s");
        boolean respServicio = servicioTareas.marcarTareaCompleta(id, aux);
        System.out.println((respServicio) ? "Tarea actualizada" : "Tarea no actualizada");
        System.out.println(servicioTareas.buscarTareaID(String.valueOf(id)));
    }
}