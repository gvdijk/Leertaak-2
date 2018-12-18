package weatherXML;

public class WeatherXMLErrorHandler {

    public WeatherXMLErrorHandler() {}

    public void handleEMptyString(Exception e) {
        System.out.println("Big oof: " + e);
    }
}
