package Dominio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TareaTest {
    @Test
    void testToCSV() {
        Tarea t1 = new Tarea("Comprar comida", "19/04/2025", false);
        Tarea t2 = new Tarea("Correr", "20/04/2025",false);
        String esperado = "1,Comprar comida,19/04/2025,false";
        String esperado2 = "2,Correr,20/04/2025,false";
        assertEquals(esperado, t1.toCSV());
        assertEquals(esperado2, t2.toCSV());
        System.out.println("Esperado --> " + esperado);
        System.out.println("Actual --> " + t1.toCSV());
        System.out.println("***************************************");
        System.out.println("Esperado --> " + esperado2);
        System.out.println("Actual --> " + t2.toCSV());
    }
}