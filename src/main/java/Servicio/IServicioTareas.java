package Servicio;

import Dominio.Tarea;

public interface IServicioTareas {
    void crearTarea(Tarea tarea);

    void listarTareas();

    void buscarTareaID(Tarea tarea, String idbuscar);

    void buscarTareaTexto(Tarea tarea, String texto);

    void marcarTareaCompleta(int id, boolean completa);
}
