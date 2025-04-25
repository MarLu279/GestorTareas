package Servicio;

import Dominio.Tarea;

import java.util.List;

public interface IServicioTareas {
    void crearTarea(Tarea tarea);

    List<Tarea> listarTareas();

    void buscarTareaID(Tarea tarea, String idbuscar);

    void buscarTareaTexto(Tarea tarea, String texto);

    void marcarTareaCompleta(int id, boolean completa);
}
