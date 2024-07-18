import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;
class Activity {
    private String name;
    private String description;
    private double cost;
    private int capacity;
    private List<Passenger> passengers;

    public Activity(String name, String description, double cost, int capacity) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.capacity = capacity;
        this.passengers = new ArrayList<>();
    }

    public boolean addPassenger(Passenger passenger) {
        if (passengers.size() < capacity) {
            passengers.add(passenger);
            return true;
        } else {
            return false;
        }
    }

    public String getName() {
        return String.format("%s - %s, Cost: %.2f, Capacity: %d", name, description, cost, capacity);
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public double getCost() {
        return cost;
    }

    public int getCapacity() {
        return capacity;
    }
}

class Destination {
    private String name;
    private List<Activity> activities;

    public Destination(String name) {
        this.name = name;
        this.activities = new ArrayList<>();
    }

    public void addActivity(Activity a) {
        if (!activities.contains(a)) {
            activities.add(a);
        } else {
            System.out.println("Activity already exists in the destination.");
        }
    }

    public String getName() {
        return name;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public List<Activity> getAvailableActivities() {
        List<Activity> availableActivities = new ArrayList<>();
        for (Activity activity : activities) {
            if (activity.getPassengers().size() < activity.getCapacity()) {
                availableActivities.add(activity);
            }
        }
        return availableActivities;
    }
}

class Passenger {
    private String name;
    private String type;
    private double balance;
    private List<Activity> activities;

    public Passenger(String name, String type, double balance) {
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.activities = new ArrayList<>();
    }

    public boolean addActivity(Activity activity) {
        double actualCost = ("Gold".equals(type)) ? activity.getCost() * 0.9 : activity.getCost();
        if (actualCost > balance && !"Premium".equals(type)) {
            return false;
        } else {
            balance -= actualCost;
            activities.add(activity);
            return true;
        }
    }

    @Override
    public String toString() {
        return String.format("%s, Balance: %.2f", name, balance);
    }
}

class TravelPackage {
    private String name;
    private int capacity;
    private List<Destination> itineraries;
    private List<Passenger> passengers;

    public TravelPackage(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.itineraries = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    public void addDestination(Destination destination) {
        itineraries.add(destination);
    }

    public boolean addPassenger(Passenger passenger) {
        if (passengers.size() >= capacity) {
            System.out.println("Package " + name + " is full");
            return false;
        }
        passengers.add(passenger);
        return true;
    }

    public void printItinerary() {
        System.out.println("Travel Package: " + name);
        for (Destination destination : itineraries) {
            System.out.println("Destination: " + destination.getName());
            for (Activity activity : destination.getActivities()) {
                System.out.println(activity.getName());
            }
        }
    }

    public void printPassengerList() {
        System.out.println("Travel Package: " + name);
        System.out.println("Passenger Capacity: " + capacity);
        System.out.println("Number of Passengers: " + passengers.size());
        for (Passenger passenger : passengers) {
            System.out.println(passenger);
        }
    }

    public void printActivityAvailability() {
        System.out.println("Activities with Available Spaces in " + name + ":");
        for (Destination destination : itineraries) {
            for (Activity activity : destination.getAvailableActivities()) {
                int availableSpaces = activity.getCapacity() - activity.getPassengers().size();
                System.out.println(activity.getName() + " at " + destination.getName() + ": " + availableSpaces + " spaces available");
            }
        }
    }

    public void addActivityToDestination(String destinationName, Activity activity) {
        for (Destination destination : itineraries) {
            if (destination.getName().equals(destinationName)) {
                destination.addActivity(activity);
                return;
            }
        }
    }
}

public class Example {

    // Test addPassenger method in Activity class
    @Test
    public void testActivityAddPassenger() {
        Activity activity = new Activity("Test Activity", "Test Description", 10.0, 5);
        assertTrue(activity.addPassenger("Test Passenger"));
        assertFalse(activity.addPassenger("Test Passenger 2"));
    }

    // Test addActivity method in Destination class
    @Test
    public void testDestinationAddActivity() {
        Destination destination = new Destination("Test Destination");
        Activity activity = new Activity("Test Activity", "Test Description", 10.0, 5);
        destination.addActivity(activity);
        assertTrue(destination.getActivities().contains(activity));
    }

    // Test addActivity method in Passenger class
    @Test
    public void testPassengerAddActivity() {
        Passenger passenger = new Passenger("Test Passenger", "Standard", 100.0);
        Activity activity = new Activity("Test Activity", "Test Description", 10.0, 5);
        assertTrue(passenger.addActivity(activity));
        assertFalse(passenger.addActivity(activity));
    }

    // Test addDestination and addPassenger methods in TravelPackage class
    @Test
    public void testTravelPackageAddDestinationAndPassenger() {
        TravelPackage travelPackage = new TravelPackage("Test Package", 10);
        Destination destination = new Destination("Test Destination");
        travelPackage.addDestination(destination);
        assertEquals(1, travelPackage.getItineraries().size());
        Passenger passenger = new Passenger("Test Passenger", "Standard", 100.0);
        travelPackage.addPassenger(passenger);
        assertTrue(travelPackage.getPassengers().contains(passenger));
    }

}
