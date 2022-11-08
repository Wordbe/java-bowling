package bowling.domain.frame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class FrameNumberTest {
    @Test
    void create() {
        assertThatIllegalArgumentException().isThrownBy(() -> new FrameNumber(11))
                .withMessage("프레임번호(11)는 10 을 초과할 수 없습니다");
    }

    @DisplayName("프레임번호를 1 증가시킨다")
    @Test
    void increase() {
        assertThat(new FrameNumber(1).increase()).isEqualTo(new FrameNumber(2));
    }

    @DisplayName("최종프레임 번호인지 여부를 반환한다")
    @ParameterizedTest
    @CsvSource(value = {
            "9,false",
            "10,true"
    })
    void isFinal(int frameNumber, boolean expected) {
        assertThat(new FrameNumber(frameNumber).isFinal()).isEqualTo(expected);
    }
}
