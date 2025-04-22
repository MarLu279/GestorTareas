package Dominio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TareaTest {
    @Test
    public void testToCSV(){
        Tarea tarea = new Tarea("Comprar comida", "20/04/2025", false);
        String esperado = "2,Comprar comida,20/04/2025,false";
        assertEquals(esperado, tarea.toCSV());
    }

    @Test
    public void testSetFechaCreacion_Valida(){
        Tarea tarea = new Tarea();
        assertDoesNotThrow(()->tarea.setFechaCreacion("15/05/2022"));
    }

    @Test
    public void testSetFechaCreacion_Nula(){
        Tarea tarea = new Tarea();
        assertThrows(IllegalArgumentException.class, ()->tarea.setFechaCreacion(null), "Debe lanzar excepcion, fecha nula");
    }

    @Test
    public void testSetFechaCreacion_FormatoInvalido(){
        Tarea tarea = new Tarea();
        assertThrows(IllegalArgumentException.class, ()->tarea.setFechaCreacion("2023-05-15"),
                "Debe lanzar excepcion, por formato invalido");
    }

    @Test
    public void testSetDescripcion_Valida(){
        Tarea tarea = new Tarea();
        assertDoesNotThrow(()->tarea.setDescripcion("Caminar con el perro"));
    }

    @Test
    public void testSetDescripcion_Nula(){
        Tarea tarea = new Tarea();
        assertThrows(IllegalArgumentException.class, ()->tarea.setDescripcion(null));
    }

    @Test
    public void testSetDescripcion_Vacio(){
        Tarea tarea = new Tarea();
        assertThrows(IllegalArgumentException.class, ()->tarea.setDescripcion("  "));
    }

    @Test
    public void testEquals_ComparaId(){
        Tarea tarea1 = new Tarea("Tarea 1", "01/02/2024", false);
        Tarea tarea2 = new Tarea("Tarea2", "02/02/2024", true);
        assertFalse(tarea1.equals(tarea2));
    }
}