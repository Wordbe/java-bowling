package bowling.view;

import bowling.domain.frame.Frame;
import bowling.domain.frame.FrameNumber;
import bowling.domain.frame.Frames;
import bowling.domain.player.PlayerName;
import bowling.domain.score.Score;
import bowling.domain.state.FrameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static bowling.domain.state.Symbol.BAR;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class ResultView {
    private static final String NAME_COLUMN_TITLE = "NAME";

    public static void printFrames(PlayerName playerName, Frames frames) {
        System.out.println(scoreTableRow(NAME_COLUMN_TITLE, frameNumberColumnTitles()));
        System.out.println(scoreTableRow(playerName.getName(), triedFrames(frames)));
        System.out.println(scoreTableRow("", accumulatedScores(frames)));
    }

    private static List<String> accumulatedScores(Frames frames) {
        List<Integer> scores = scores(frames);

        List<String> result = new ArrayList<>();
        int accumulatedScore = 0;
        for (int score: scores) {
            accumulatedScore += score;
            result.add(String.valueOf(accumulatedScore));
        }
        return result;
    }

    private static List<Integer> scores(Frames frames) {
        return frames.getFrames()
                .stream()
                .filter(frame -> !frame.isReady())
                .filter(Frame::isFinished)
                .map(Frame::getScore)
                .filter(Objects::nonNull)
                .map(Score::getScore)
                .collect(toList());
    }

    private static List<String> frameNumberColumnTitles() {
        return IntStream.rangeClosed(1, FrameNumber.FINAL_FRAME_NUMBER)
                .mapToObj(i -> String.format("%02d", i))
                .collect(toList());
    }

    private static String scoreTableRow(String name, List<String> bodyStrings) {
        List<String> rowElements = new ArrayList<>();
        rowElements.add(name);
        rowElements.addAll(bodyStrings);
        rowElements.addAll(remainingFrames(FrameNumber.FINAL_FRAME_NUMBER - bodyStrings.size()));

        return rowElements.stream()
                .map(ResultView::padded)
                .collect(joining(BAR, BAR, BAR));
    }

    private static List<String> remainingFrames(int remainingFrameCount) {
        return IntStream.range(0, remainingFrameCount)
                .mapToObj(i -> "")
                .collect(toList());
    }

    private static List<String> triedFrames(Frames frames) {
        return frames.getFrames()
                .stream()
                .filter(frame -> !frame.isReady())
                .map(ResultView::frameString)
                .collect(toList());
    }

    private static String frameString(Frame frame) {
        return frame.getStates()
                .stream()
                .map(FrameState::description)
                .collect(joining(BAR));
    }

    private static String padded(String string) {
        String result = " ".repeat(2) + string + " ".repeat(4);
        if (string.length() > 3) {
            result = " " + string + " ";
        }
        return result.substring(0, 6);
    }
}
