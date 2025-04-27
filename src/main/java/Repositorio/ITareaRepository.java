package Repositorio;

import Dominio.Tarea;

import java.util.List;

public interface ITareaRepository {
    List<Tarea> obtenerTareas();

    void guardarTareas(List<Tarea> tareas);
}
