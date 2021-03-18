package Objects;

import Core.LotteryMachine;
import Utils.Constants;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class BallSelector {

    private final LinkedList<Ball> balls;
    private static BallSelector instance = null;


    private BallSelector(LinkedList<Ball> balls) {
        this.balls = balls;
    }

    //Singleton instance of the Objects.BallSelector class, since there is only one ball selector in one Lottery Machine
    public static BallSelector getInstance(LinkedList<Ball> balls) {
        if(instance == null)
            instance = new BallSelector(balls);
        return instance;
    }

    private Ball selectBall(){
        int ranBallIndex = balls.size() > 1 ? ThreadLocalRandom
                .current()
                .nextInt(0, balls.size() - 1): 0; // get a random index in the linked list - > if the bound are even just take the first one
        Ball chosenBall = balls.remove(ranBallIndex);
        chosenBall.setBallNum(Constants.BALL_LIMIT - balls.size()); // Indexing the balls by the order of removal
        return chosenBall;
    }

    public void run() throws InterruptedException {
        int counter = Constants.BALL_LIMIT;
        while(counter-- > 0) {
            LotteryMachine.ballsBuffer.put(selectBall());
            Thread.sleep(ThreadLocalRandom
                        .current()
                        .nextInt(Constants.DELAY_LOWER_LIMIT, Constants.DELAY_UPPER_LIMIT));
        }
    }

}
