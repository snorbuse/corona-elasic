package se.snorbuse.coronaimporter.util;

import se.snorbuse.coronaimporter.model.CombinedDatapoint;
import se.snorbuse.coronaimporter.model.Datapoints;

import java.util.List;
import java.util.stream.Collectors;

public class DatapointCombiner {

    public List<CombinedDatapoint> combine(Datapoints confirmed, Datapoints deaths, Datapoints recovered) {
        List<CombinedDatapoint> combinedDatapoints = confirmed.stream()
                .map(datapoint -> new CombinedDatapoint(
                                datapoint,
                                datapoint.getCount(),
                                deaths.get(datapoint.getId()).getCount(),
                                recovered.get(datapoint.getId()).getCount()
                        )
                )
                .collect(Collectors.toList());

//        combinedDatapoints.stream()
//                .filter(d -> d.getDate().toString().equals("2020-01-28"))
//                .filter(d -> d.getCountry().equals("Sweden"))
//                .forEach(Logger::info);

        return combinedDatapoints;
    }
}
