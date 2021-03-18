package Objects;

import Utils.Constants;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Ball {
    private int uniqueNum;
    private int ballNum;
    private static final List<Integer> uniqueNums = ThreadLocalRandom.current()
            .ints(Constants.BALL_LOWER_BOUNDS, Constants.BALL_UPPER_BOUNDS)
            .distinct()
            .limit(Constants.BALL_LIMIT)
            .boxed()
            .collect(Collectors.toList());

    public Ball() {
        getUniqueNum();
    }

    private void getUniqueNum() {
        // Select a random unique number for the ball at creation
        int setNum = uniqueNums.size() > 1 ? ThreadLocalRandom.current()
                .nextInt(0, uniqueNums.size() - 1) : 0;
        this.uniqueNum = uniqueNums.get(setNum);
        uniqueNums.remove(setNum);
    }

    @Override
    public String toString() {
        return String.format("Ball number #%s is %s",
                this.ballNum,
                this.uniqueNum
        );
    }

    public void setBallNum(int i) {
        ballNum = i;
    }
}
