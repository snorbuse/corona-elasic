package se.snorbuse.coronaimporter;

import se.snorbuse.coronaimporter.model.Datapoint;
import se.snorbuse.coronaimporter.util.FileImport;
import se.snorbuse.coronaimporter.util.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
        Logger.info("Starting application");

        FileImport fileImport = new FileImport();
        List<Datapoint> confirmed = fileImport.run(new File("../time_series_19-covid-Confirmed.csv"));
        List<Datapoint> deaths = fileImport.run(new File("../time_series_19-covid-Deaths.csv"));
        List<Datapoint> recovered = fileImport.run(new File("../time_series_19-covid-Recovered.csv"));
        Logger.info("Confirmed: %d, Deaths: %d, Recovered: %d", confirmed.size(), deaths.size(), recovered.size());

        Logger.info("Application finished");
    }
}
