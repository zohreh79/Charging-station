package webService.charging.station.model;

public class AroundStationsResponse {

    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private double distanceFromLocation;

    public AroundStationsResponse() {
    }

    public AroundStationsResponse(int id, String name, double latitude, double longitude, double distanceFromLocation) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distanceFromLocation = distanceFromLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistanceFromLocation() {
        return distanceFromLocation;
    }

    public void setDistanceFromLocation(double distanceFromLocation) {
        this.distanceFromLocation = distanceFromLocation;
    }
}
