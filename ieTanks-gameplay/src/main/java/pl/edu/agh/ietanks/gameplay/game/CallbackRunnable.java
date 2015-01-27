package pl.edu.agh.ietanks.gameplay.game;

public class CallbackRunnable implements Runnable {
    private final Runnable runnable;
    private final Runnable onFinished;

    public CallbackRunnable(Runnable runnable, Runnable onFinished) {
        this.runnable = runnable;
        this.onFinished = onFinished;
    }

    @Override
    public void run() {
        runnable.run();
        onFinished.run();
    }
}
