package weatherXML;

public class WeatherXMLErrorHandler {

    public WeatherXMLErrorHandler() {}

    public WeatherMeasurement handleEmptyString(Exception e, byte flag, WeatherMeasurement m) {
        System.out.println("Big oof: " + flag);
        switch (flag) {
            case -1:
                // Might as well surrender
                break;
            case 0:
                // Might as well surrender
                break;
            case 1:
                // Might as well surrender
                break;
            case 2:
                // Might as well surrender
                break;
            case 3:
                // Might as well surrender
                break;
            case 4:
                m.setTemperature(0.0f);
                break;
            case 5:
                m.setDew(0.0f);
                break;
            case 6:
                m.setAirStation(0.0f);
                break;
            case 7:
                m.setAirSea(0.0f);
                break;
            case 8:
                m.setVisibility(0.0f);
                break;
            case 9:
                m.setWindSpeed(0.0f);
                break;
            case 10:
                m.setRain(0.0f);
                break;
            case 11:
                m.setSnow(0.0f);
                break;
            case 12:
                m.setClouds(0);
                break;
            case 13:
                m.setWindDegree(0);
                break;
            case 14:
                byte b = 0;
                m.setEvents(b);
                break;
            default:
                // Might as well surrender
                break;
        }
        return m;
    }
}
