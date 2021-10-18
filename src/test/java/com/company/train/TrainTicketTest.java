package com.company.train;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;


class TrainTicketTest {

  private TrainTicket trainTicketUnderTest;

  public static Stream<Arguments> words() {
    return Stream.of(
            Arguments.of("a"),
            Arguments.of("n"),
            Arguments.of("sant"),
            Arguments.of("Sant")
    );
  }

  public static Stream<Arguments> wordsWithSpace() {
    return Stream.of(
            Arguments.of("Santa ")
    );
  }

  @BeforeEach
  void setUp() {
    TrainStationRepository trainStationRepository = mock(TrainStationRepository.class);
    when(trainStationRepository.getAll())
        .thenReturn(
            new HashSet<>(
                Arrays.asList(
                    new TrainStation("Santa Iria"),
                    new TrainStation("Santa Margarida"),
                    new TrainStation("Santana-Cartaxo"),
                    new TrainStation("Santarem"),
                    new TrainStation("Santo Amamro de Oeiras"),
                    new TrainStation("Estacao")
                )));
    trainTicketUnderTest = new TrainTicket(trainStationRepository);
  }

  @ParameterizedTest
  @MethodSource("words")
  public void shouldMatchStationNameWithWord(String word) {
    Set<TrainStation> stationNamesByWord = trainTicketUnderTest.getStationNamesByWord(word);
    assertThat(stationNamesByWord)
        .containsOnlyOnce(
            new TrainStation("Santa Iria"),
            new TrainStation("Santa Margarida"),
            new TrainStation("Santana-Cartaxo"),
            new TrainStation("Santarem"),
            new TrainStation("Santo Amamro de Oeiras"));
  }


  @ParameterizedTest
  @MethodSource("wordsWithSpace")
  public void shouldMatchStationNameWithWordWithSpace(String word) {
    Set<TrainStation> stationNamesByWord = trainTicketUnderTest.getStationNamesByWord(word);
    assertThat(stationNamesByWord)
            .containsOnlyOnce(
                    new TrainStation("Santa Iria"),
                    new TrainStation("Santa Margarida"));
  }

  @Test
  public void shouldMatchStationNameWithEmpty() {
    Set<TrainStation> stationNamesByWord = trainTicketUnderTest.getStationNamesByWord("");
    assertThat(stationNamesByWord)
            .containsOnlyOnce(
                    new TrainStation("Santa Iria"),
                    new TrainStation("Santa Margarida"),
                    new TrainStation("Santana-Cartaxo"),
                    new TrainStation("Santarem"),
                    new TrainStation("Santo Amamro de Oeiras"),
                    new TrainStation("Estacao")
            );
  }

  @Test
  public void shouldMatchStationNameWithNull() {
    assertThrows(IllegalArgumentException.class, ()-> trainTicketUnderTest.getStationNamesByWord(null));

  }
}
