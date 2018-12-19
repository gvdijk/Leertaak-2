package weatherSQL;

import weatherXML.WeatherMeasurement;

import java.util.ArrayList;

public class WeatherSQLParser {

    private StringBuilder query;

    public WeatherSQLParser() {
        query  = new StringBuilder("INSERT INTO `Measurement` (`Station_stn`, `date`, `time`, `temp`, `dew`, `stp`, `slp`, `vis`, `wdsp`, `prcp`, `sndp`, `cldc`, `wnddir`, `frz`, `rain`, `snow`, `hail`, `tndr`, `torn`) VALUES ");
    }

    public void parseChuck(ArrayList<WeatherMeasurement> data) {
        for (int x=0; x<10; x++) {
            query.append("(");
            query.append(data.get(x).getStation());
            query.append(",'");
            query.append(data.get(x).getDate());
            query.append("','");
            query.append(data.get(x).getTime());
            query.append("',");
            query.append(data.get(x).getTemperature());
            query.append(",");
            query.append(data.get(x).getDew());
            query.append(",");
            query.append(data.get(x).getAirStation());
            query.append(",");
            query.append(data.get(x).getAirSea());
            query.append(",");
            query.append(data.get(x).getVisibility());
            query.append(",");
            query.append(data.get(x).getWindSpeed());
            query.append(",");
            query.append(data.get(x).getRain());
            query.append(",");
            query.append(data.get(x).getSnow());
            query.append(",");
            query.append(data.get(x).getClouds());
            query.append(",");
            query.append(data.get(x).getWindDegree());
            query.append(",");
            query.append(data.get(x).isRained());
            query.append(",");
            query.append(data.get(x).isSnowed());
            query.append(",");
            query.append(data.get(x).isFrozen());
            query.append(",");
            query.append(data.get(x).isHailed());
            query.append(",");
            query.append(data.get(x).isThunder());
            query.append(",");
            query.append(data.get(x).isTornado());
            query.append("),");
        }
    }

    public String sendQuery() {
        query.replace(query.length() - 1, query.length(), ";");
        return query.toString();
    }
}
