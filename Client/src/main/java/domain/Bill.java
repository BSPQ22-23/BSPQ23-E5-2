package domain;

import java.util.Date;
import java.util.List;

public class Bill {
	 private int id;
	    private Date issueDate;
	    private List<Service> services;
	    private double billTotal;
	    private Booking booking;

	    public Bill(int id, Date issueDate, List<Service> services, double billTotal, Booking booking) {
	        this.id = id;
	        this.issueDate = issueDate;
	        this.services = services;
	        this.billTotal = billTotal;
	        this.booking = booking;
	    }

	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public Date getIssueDate() {
	        return issueDate;
	    }

	    public void setIssueDate(Date issueDate) {
	        this.issueDate = issueDate;
	    }

	    public List<Service> getServices() {
	        return services;
	    }

	    public void setServices(List<Service> services) {
	        this.services = services;
	    }

	    public double getBillTotal() {
	        return billTotal;
	    }

	    public void setBillTotal(double billTotal) {
	        this.billTotal = billTotal;
	    }

	    public Booking getBooking() {
	        return booking;
	    }

	    public void setBooking(Booking booking) {
	        this.booking = booking;
	    }

	    public void addService(Service service) {
	        services.add(service);
	    }

	    public void removeService(Service service) {
	        services.remove(service);
	    }
	    
}
