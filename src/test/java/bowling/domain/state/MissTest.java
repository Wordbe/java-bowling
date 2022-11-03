package bowling.domain.state;

import bowling.domain.pin.FallenPin;
import bowling.domain.score.Score;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MissTest {

    @Test
    void description() {
        assertThat(miss().description()).isEqualTo("9|-");
    }

    @Test
    void tries() {
        assertThat(miss().tries()).isEqualTo(2);
    }

    @Test
    void getScore() {
        FrameState frameState = miss();

        assertThat(frameState.getScore()).isEqualTo(new Score(9, 0));
    }

    @Test
    void addScore() {
        FrameState frameState = miss();
        Score previousScore = new Score(10, 1);

        assertThat(frameState.addScore(previousScore)).isEqualTo(new Score(19, 0));
    }

    private Miss miss() {
        return new Miss(FallenPin.of(9), FallenPin.of(0));
    }
}
