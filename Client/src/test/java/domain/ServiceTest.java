package domain;

import static org.junit.Assert.*;

import org.junit.Test;

import org.junit.Before;




public class ServiceTest {

    private Service service;

    @Before
    public void setUp() {
        service = new Service("Massage", "Full body massage for 60 minutes", 80.0);
    }

    @Test
    public void testGetName() {
        assertEquals("Massage", service.getName());
    }

    @Test
    public void testSetName() {
        service.setName("Sauna");
        assertEquals("Sauna", service.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Full body massage for 60 minutes", service.getDescription());
    }

    @Test
    public void testSetDescription() {
        service.setDescription("Facial treatment for 30 minutes");
        assertEquals("Facial treatment for 30 minutes", service.getDescription());
    }

    @Test
    public void testGetPrice() {
        assertEquals(80.0, service.getPrice(), 0.001);
    }

    @Test
    public void testSetPrice() {
        service.setPrice(100.0);
        assertEquals(100.0, service.getPrice(), 0.001);
    }
}
