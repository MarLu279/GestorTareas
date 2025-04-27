package Servicio;

import Dominio.Tarea;
import Repositorio.ITareaRepository;

import java.util.ArrayList;
import java.util.List;

public class ServicioTarea implements IServicioTareas{
    private final ITareaRepository repositorio;
    private List<Tarea> tareas = new ArrayList<>();

    public ServicioTarea(ITareaRepository repositorio){
        this.repositorio = repositorio;
    }

    //Metodos de interfas
    @Override
    public String listarTareas() {
        tareas = repositorio.obtenerTareas();
        StringBuilder lista = new StringBuilder();
        for (Tarea tarea: tareas){
            lista.append(tarea.toString()).append("\n");
        }
        return lista.toString();
    }

    @Override
    public void crearTarea(Tarea tarea) {
        //añadir: para manejo en memoria
        this.tareas.add(tarea);
        //añadir: persistencia en archivo
        repositorio.guardarTareas(tareas);
    }

    @Override
    public Tarea buscarTareaID(String idbuscar) {
        Tarea tarea = null;
        for(Tarea aux: this.tareas){
            if(aux.getIdTarea() == Integer.parseInt(idbuscar)){
                tarea = aux;
            }
        }
        return  tarea;
    }

    @Override
    public List<Tarea> buscarTareaTexto(String texto) {
        List<Tarea> resultado = new ArrayList<>();
        String textoLower = texto.toLowerCase();

        for(Tarea tar: this.tareas){
            if(tar.getDescripcion().toLowerCase().contains(textoLower)){
                resultado.add(tar);
            }
        }
        return resultado;
    }

    @Override
    public boolean marcarTareaCompleta(int id, boolean completa) {
        boolean banderaCompletada = false;
        for(Tarea tarea : this.tareas){
            if(tarea.getIdTarea() == id){
                tarea.setCompletada(completa);
                repositorio.guardarTareas(this.tareas);
                banderaCompletada = true;
                break;
            }
        }
        return banderaCompletada;
    }
}