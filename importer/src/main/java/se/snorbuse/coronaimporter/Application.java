package se.snorbuse.coronaimporter;

import se.snorbuse.coronaimporter.model.CombinedDatapoint;
import se.snorbuse.coronaimporter.model.Datapoints;
import se.snorbuse.coronaimporter.util.DatapointCombiner;
import se.snorbuse.coronaimporter.util.FileImport;
import se.snorbuse.coronaimporter.util.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
        Logger.info("Starting application");

        FileImport fileImport = new FileImport();
        Datapoints confirmed = fileImport.run(new File("../time_series_19-covid-Confirmed.csv"));
        Datapoints deaths = fileImport.run(new File("../time_series_19-covid-Deaths.csv"));
        Datapoints recovered = fileImport.run(new File("../time_series_19-covid-Recovered.csv"));
        Logger.info("Confirmed: %d, Deaths: %d, Recovered: %d", confirmed.size(), deaths.size(), recovered.size());

        DatapointCombiner combiner = new DatapointCombiner();
        List<CombinedDatapoint> combinedDatapoints = combiner.combine(confirmed, deaths, recovered);
        Logger.info("Combined %d datapoints", combinedDatapoints.size());



        Logger.info("Application finished");
    }
}
