package Core;

import Objects.Ball;
import Objects.BallSelector;
import Objects.Display;
import Utils.Constants;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class LotteryMachine {

    // by using a static blocking queue, I will manage the synchronization of the two threads
    // Since this is a classic consumer/producer problem, the blocking queue will signal the consumer
    // once there is a ball ready to be displayed
    public static final BlockingQueue<Ball> ballsBuffer = new SynchronousQueue<>();

    public static void start() {
        BallSelector selector = BallSelector.getInstance(createBallsCollection());
        Display display = new Display();


        // Setting the selector thread, also passing possible excpetions to the display to shows
        Thread selectorThread = new Thread(() -> {
            try {
                selector.run();
            } catch (InterruptedException e) {
                display.printError(e.getMessage());
            }
        });

        Thread displayThread = new Thread(display::display);

        // Start both threads
        selectorThread.start();
        displayThread.start();
    }

    // By using a linked list, they will be ordered by their extraction sequence.
    private static LinkedList<Ball> createBallsCollection(){
        final LinkedList<Ball> ballsCollection = new LinkedList<>();
        for(int i = 0; i< Constants.BALL_LIMIT; i++)
            ballsCollection.add(new Ball());
        return ballsCollection;
    }
}
