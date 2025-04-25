package Servicio;

import Dominio.Tarea;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServicioTareasArchivoTest {

    //T1 Varificar que se agregue una tarea al archivo
    @Test
    void testAgregarTarea(@TempDir Path tempDir) throws IOException, IllegalAccessException, NoSuchFieldException {
        //Arrage
        Path archivoPrueba = tempDir.resolve("tareas.txt");
        ServicioTareasArchivo servicio = new ServicioTareasArchivo(archivoPrueba);

        //Act
        servicio.crearTarea(new Tarea(1,"Hacer ejercicio","03/03/2023", false));

        //Assert
        assertTrue(Files.exists(archivoPrueba));
        List<String> lineas = Files.readAllLines(archivoPrueba);
        String esperado = "1,Hacer ejercicio,03/03/2023,false";
        assertEquals(esperado, lineas.get(0));
    }

    //T2,Simula el repositorio con Mockito
    @Test
    public void testListarTareas(){
        //Arrage (config Mockito)
        IServicioTareas servicioMock = mock(IServicioTareas.class);

        when(servicioMock.listarTareas())
                .thenReturn(List.of(
                    new Tarea(1, "Tarea 1", "01/01/2025", false),
                    new Tarea(2, "Tarea 2", "02/02/2025", true)
        ));

        //Act
        List<Tarea> tareas = servicioMock.listarTareas();

        //Assert
        assertEquals(2, tareas.size());
        assertEquals("Tarea 1", tareas.get(0).getDescripcion());
    }
}