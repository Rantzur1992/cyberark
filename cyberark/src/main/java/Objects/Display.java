package Objects;

import Core.LotteryMachine;
import Utils.Constants;

import java.util.Objects;

public class Display {
    public void display(){
        int counter = Constants.BALL_LIMIT;
        while(counter-- > 0) {
            try {
                // By using blocking queue, the consumer (the display) will wait until the producer(the balls selector) signals it produced a ball
                Ball ball = LotteryMachine.ballsBuffer.take();
                System.out.println(Objects.requireNonNull(ball).toString());
            } catch (InterruptedException e) {
                printError(e.getMessage());
            }
        }
    }

    public void printError(String error) {
        System.out.println(error);
    }
}
