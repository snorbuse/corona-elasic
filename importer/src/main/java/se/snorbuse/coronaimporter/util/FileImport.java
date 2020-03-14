package se.snorbuse.coronaimporter.util;

import se.snorbuse.coronaimporter.model.Datapoint;
import se.snorbuse.coronaimporter.model.Datapoints;
import se.snorbuse.coronaimporter.model.Location;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class FileImport {

    public Datapoints run(File file) throws IOException {
        Optional<String> header = Files.lines(file.toPath()).findFirst();
        List<LocalDate> dates = findDates(header);

        Datapoints datapoints = Files.lines(file.toPath())
                .skip(1)
                .map(line -> formatLine(line))
                .map(line -> createItem(line, dates))
                .flatMap(Collection::stream)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Datapoints::new));

//        datapoints.stream()
//                .filter(k -> k.getCountry().contains("Korea"))
//                .forEach(this::print);

        return datapoints;
    }

    private String formatLine(String line) {
        if (!line.contains("\"")) {
            return line;
        }

        String result = "";
        String[] split = line.split("\"");
        for (int x = 0; x < split.length; x++) {
            if (x % 2 != 0) {
                result += split[x].replace(",", "");
            } else {
                result += split[x];
            }
        }

        return result;
    }

    private void print(Datapoint k) {
        Logger.info("item: %s, %d, %s %s", k.getDate(), k.getCount(), k.getCountry(), k.getProvince());
    }

    private List<Datapoint> createItem(String line, List<LocalDate> dates) {
        String[] parts = line.split(",");

        String province = parts[0];
        String country = parts[1];
        double lat = Double.parseDouble(parts[2]);
        double lon = Double.parseDouble(parts[3]);
        Location location = new Location(lat, lon);

        List<Datapoint> datapointList = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            LocalDate date = dates.get(i);
            int count = Integer.parseInt(parts[i + 4]);

            datapointList.add(
                    new Datapoint(province, country, count, date, location)
            );
        }

        return datapointList;
    }


    private List<LocalDate> findDates(Optional<String> line) {
        if (!line.isPresent()) {
            throw new RuntimeException("First line is not available");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        return Arrays.stream(line.get().split(","))
                .skip(4)
                .map(date -> LocalDate.parse(date, formatter))
                .collect(Collectors.toList());
    }
}
