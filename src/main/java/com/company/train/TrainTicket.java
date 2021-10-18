package com.company.train;

import java.util.Set;
import java.util.stream.Collectors;

public class TrainTicket {

  private final TrainStationRepository trainStationRepository;

  public TrainTicket(TrainStationRepository trainStationRepository) {
    this.trainStationRepository = trainStationRepository;
  }

  public Set<TrainStation> getStationNamesByWord(String word) {

    if (word == null) {
      throw new IllegalArgumentException("Word is required");
    }

    String regex = createRegex(word);

    return trainStationRepository.getAll().parallelStream()
        .filter(it -> it.getName().matches("(?i).*" + regex + ".*"))
        .collect(Collectors.toSet());
  }

  private String createRegex(String word) {
    StringBuilder stringBuilder = new StringBuilder();
    for (String w : word.split("")) {
      stringBuilder.append(w).append(".*");
    }
    return stringBuilder.toString();
  }
}
