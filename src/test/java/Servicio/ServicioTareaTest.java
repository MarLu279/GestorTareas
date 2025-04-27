package Servicio;

import Dominio.Tarea;
import Repositorio.ITareaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioTareaTest {
    @Mock
    private ITareaRepository repositorioMock;

    @InjectMocks
    private ServicioTarea servicioTarea;

    private final Tarea tarea1 = new Tarea(1, "Estudiar Java",
            "01/01/2025", false);
    private final Tarea tarea2 = new Tarea(2, "Hacer ejercicio",
            "02/02/2025", true);

    @Test
    void listarTareas_ConTareas_ListaFormateada(){
        //conguracion Mock
        when(repositorioMock.obtenerTareas()).thenReturn(
                Arrays.asList(tarea1, tarea2));
        //Ejecuta metodo
        String resultado = servicioTarea.listarTareas();
        //verficar resultados
        assertTrue(resultado.contains("Estudiar Java"));
        assertTrue(resultado.contains("Hacer ejercicio"));
        verify(repositorioMock, times(1)).obtenerTareas();
    }

    @Test
    void listarTareas_SinTareas_RetornaVacio(){
        when(repositorioMock.obtenerTareas()).thenReturn(new ArrayList<>());
        String resultado = servicioTarea.listarTareas();
        assertEquals("",resultado.trim());
    }

    @Test
    void crearTarea_TareaValida_GuardarRepositorio(){
        //Ejecuta
        servicioTarea.crearTarea(tarea1);
        //Verificar que se guardo en el repositorio
        verify(repositorioMock, times(1)).guardarTareas(anyList());
        assertEquals(1, servicioTarea.tareas.size());
    }

    @Test
    void buscarTareaId_IdExistente_retornaTrue(){
        servicioTarea.tareas.add(tarea1);
        Tarea resultado = servicioTarea.buscarTareaID("1");
        assertEquals(tarea1, resultado);
    }

    @Test
    void buscarTareaid_IdInexistente_retornaNull(){
        assertNull(servicioTarea.buscarTareaID("99"));
    }

    @Test
    void buscarTareaId_IdNumerico_LanzaExcepcion(){
        assertThrows(NumberFormatException.class, ()-> {
            servicioTarea.buscarTareaID("abc");
        });
    }

    @Test
    void buscarTareaTexto_Coincidencias_RetornaListaFiltrado(){
        servicioTarea.tareas.addAll(Arrays.asList(tarea1,tarea2));
        List<Tarea> resultado = servicioTarea.buscarTareaTexto("ejercicio");
        assertEquals(1, resultado.size());
        assertEquals(tarea2, resultado.get(0));
    }

    @Test
    void marcarTareaCompleta_IdExistente_RetornaTrue(){
        servicioTarea.tareas.add(tarea1);
        boolean resultado = servicioTarea.marcarTareaCompleta(1,true);
        assertTrue(resultado);
        assertTrue(tarea1.isCompletado());
        verify(repositorioMock, times(1)).guardarTareas(anyList());
    }

    @Test
    void marcarTareaCompleta_IdInexistente_RetornaFalse(){
        boolean resultado = servicioTarea.marcarTareaCompleta(99, true);
        assertFalse(resultado);
        verify(repositorioMock, never()).guardarTareas(anyList());
    }
}