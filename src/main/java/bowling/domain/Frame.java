package bowling.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Frame {
    protected final List<Bowling> bowlings;

    Frame(List<Bowling> bowlings) {
        this.bowlings = bowlings;
    }

    public static Frame init() {
        return new Frame(List.of(Bowling.init()));
    }

    public boolean isFinished() {
        return lastBowling().isFinished();
    }

    public Frame bowl(FallenPins fallenPins) {
        if (lastBowling().isFinished()) {
            return create(add(fallenPins));
        }

        return create(update(fallenPins));
    }

    public List<Bowling> getBowlings() {
        return bowlings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Frame)) return false;

        Frame that = (Frame) o;

        return Objects.equals(bowlings, that.bowlings);
    }

    @Override
    public int hashCode() {
        return bowlings != null ? bowlings.hashCode() : 0;
    }

    protected Frame create(List<Bowling> bowlings) {
        return new Frame(bowlings);
    }

    private List<Bowling> add(FallenPins fallenPins) {
        List<Bowling> result = new ArrayList<>(bowlings);
        Bowling bowling = Bowling.init();
        result.add(bowling.bowl(fallenPins));
        return result;
    }

    private List<Bowling> update(FallenPins fallenPins) {
        List<Bowling> result = new ArrayList<>(bowlings);
        result.set(lastIndex(), lastBowling().bowl(fallenPins));
        return result;
    }

    private Bowling lastBowling() {
        return bowlings.get(lastIndex());
    }

    private int lastIndex() {
        return bowlings.size() - 1;
    }
}
