package Servicio;

import Dominio.Tarea;

import java.util.List;

public interface IServicioTareas {
    void crearTarea(Tarea tarea);

    String listarTareas();

    Tarea buscarTareaID(String idbuscar);

    List<Tarea> buscarTareaTexto(String texto);

   boolean marcarTareaCompleta(int id, boolean completa);
}
