package se.snorbuse.coronaimporter.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Datapoints {
    Map<String, Datapoint> datapoints = new HashMap<>();

    public Datapoints(List<Datapoint> data) {
        data.stream()
                .forEach(d -> datapoints.put(d.getId(), d));
    }

    public void add(Datapoint datapoint) {
        datapoints.put(datapoint.getId(), datapoint);
    }

    public Datapoint get(String id) {
        return datapoints.get(id);
    }

    public Stream<Datapoint> stream() {
        return datapoints.values().stream();
    }

    public int size() {
        return datapoints.size();
    }

}
