package domain;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillTest {

    @Test
    public void testGetId() {
        int id = 1;
        Bill bill = new Bill(id, new Date(), new ArrayList<Service>(), 0, new Booking(id, null, null, null, null));
        assertEquals(id, bill.getId());
    }

    @Test
    public void testSetId() {
        int id = 1;
        Bill bill = new Bill(0, new Date(), new ArrayList<Service>(), 0, new Booking(id, null, null, null, null));
        bill.setId(id);
        assertEquals(id, bill.getId());
    }

    @Test
    public void testGetIssueDate() {
        Date date = new Date();
        Bill bill = new Bill(0, date, new ArrayList<Service>(), 0, new Booking(0, date, date, null, null));
        assertEquals(date, bill.getIssueDate());
    }

    @Test
    public void testSetIssueDate() {
        Date date = new Date();
        Bill bill = new Bill(0, new Date(), new ArrayList<Service>(), 0, new Booking(0, date, date, null, null));
        bill.setIssueDate(date);
        assertEquals(date, bill.getIssueDate());
    }

    @Test
    public void testGetServices() {
        List<Service> services = new ArrayList<>();
        services.add(new Service("testService", null, 10.0));
        Bill bill = new Bill(0, new Date(), services, 0, new Booking(0, null, null, null, null));
        assertEquals(services, bill.getServices());
    }

    @Test
    public void testSetServices() {
        List<Service> services = new ArrayList<>();
        services.add(new Service("testService", null, 10.0));
        Bill bill = new Bill(0, new Date(), new ArrayList<Service>(), 0, new Booking(0, null, null, null, null));
        bill.setServices(services);
        assertEquals(services, bill.getServices());
    }

    @Test
    public void testGetBillTotal() {
        double billTotal = 100.0;
        Bill bill = new Bill(0, new Date(), new ArrayList<Service>(), billTotal, new Booking(0, null, null, null, null));
        assertEquals(billTotal, bill.getBillTotal(), 0.001);
    }

    @Test
    public void testSetBillTotal() {
        double billTotal = 100.0;
        Bill bill = new Bill(0, new Date(), new ArrayList<Service>(), 0, new Booking(0, null, null, null, null));
        bill.setBillTotal(billTotal);
        assertEquals(billTotal, bill.getBillTotal(), 0.001);
    }

    @Test
    public void testGetBooking() {
        Booking booking = new Booking(0, null, null, null, null);
        Bill bill = new Bill(0, new Date(), new ArrayList<Service>(), 0, booking);
        assertEquals(booking, bill.getBooking());
    }

    @Test
    public void testSetBooking() {
        Booking booking = new Booking(0, null, null, null, null);
        Bill bill = new Bill(0, new Date(), new ArrayList<Service>(), 0, new Booking(0, null, null, null, null));
        bill.setBooking(booking);
        assertEquals(booking, bill.getBooking());
    }
    @Test
    public void testAddService() {
        Service service = new Service("testService", null, 10.0);
        List<Service> services = new ArrayList<>();
        Bill bill = new Bill(0, new Date(), services, 0, new Booking(0, null, null, null, null));
        bill.addService(service);
        assertTrue(bill.getServices().contains(service));
    }

    @Test
    public void testRemoveService() {
        Service service = new Service("testService", null, 10.0);
        List<Service> services = new ArrayList<>();
        services.add(service);
        Bill bill = new Bill(0, new Date(), services, 0, new Booking(0, null, null, null, null));
        bill.removeService(service);
        assertFalse(bill.getServices().contains(service));
    }
    
}

