package weatherXML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class WeatherXMLParser extends DefaultHandler {

    private ArrayList<WeatherMeasurement> list;
    private ByteArrayInputStream data;
    private WeatherMeasurement temp;
    private String current;
    private WeatherXMLErrorHandler errHandler;

    public WeatherXMLParser(ByteArrayInputStream data) {
        this.data = data;
        list = new ArrayList<>();
        errHandler = new WeatherXMLErrorHandler();
        parseData();
        //printData();
    }

    public void parseData() {
        SAXParserFactory fac = SAXParserFactory.newInstance();
        try {
            SAXParser par = fac.newSAXParser();
            par.parse(data, this);
        }
        catch (ParserConfigurationException pce) {}
        catch (SAXException se) {}
        catch (IOException ioe) {}
    }

    public void startDocument() throws SAXException {}

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("MEASUREMENT")) {
            temp = new WeatherMeasurement();
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        try {
            System.out.println(current);
            switch(qName.toUpperCase()) {
                case "MEASUREMENT":
                    list.add(temp);
                    break;
                case "STN":
                    temp.setStation(Integer.parseInt(current));
                    break;
                case "DATE":
                    temp.setDate(current);
                    break;
                case "TIME":
                    temp.setTime(current);
                    break;
                case "TEMP":
                    temp.setTemperature(Float.parseFloat(current));
                    break;
                case "DEWP":
                    temp.setDew(Float.parseFloat(current));
                    break;
                case "STP":
                    temp.setAirStation(Float.parseFloat(current));
                    break;
                case "SLP":
                    temp.setAirSea(Float.parseFloat(current));
                    break;
                case "VISIB":
                    temp.setVisibility(Float.parseFloat(current));
                    break;
                case "WDSP":
                    temp.setWindSpeed(Float.parseFloat(current));
                    break;
                case "PRCP":
                    temp.setRain(Float.parseFloat(current));
                    break;
                case "SNDP":
                    temp.setSnow(Float.parseFloat(current));
                    break;
                case "CLDC":
                    temp.setClouds(Float.parseFloat(current));
                    break;
                case "WNDDIR":
                    temp.setWindDegree(Integer.parseInt(current));
                    break;
                case "FRSHTT":
                    //temp.setEvents(Byte.parseByte(current));
                    break;
                default:
                    break;
            }
        }
        catch (NumberFormatException nfe) {
            errHandler.handleEMptyString(nfe);
        }
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        current = new String(ch, start, length);
    }

    public ArrayList<WeatherMeasurement> getData() {
        return list;
    }
}