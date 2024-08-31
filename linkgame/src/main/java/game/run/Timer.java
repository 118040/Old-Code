package game.run;

import java.util.function.Consumer;

public class Timer {
    private static final Timer TIMER = new Timer();
    public TimerState state = TimerState.WAITING;
    private TimeThread thread;

    private Timer() {
    }

    public static Timer getInstance() {
        return TIMER;
    }

    public void run(long remainder, Consumer<Object> action) {
        thread = new TimeThread(remainder, action, this);
        state = TimerState.RUNNING;
        thread.start();
    }

    public void extend(long millis) {
        thread.endTime += millis;
    }

    public void suspend() {
        if (state.equals(TimerState.RUNNING)) {
            state = TimerState.SUSPENDING;
        }
    }

    public void resume() {
        if (state.equals(TimerState.SUSPENDING)) {
            state = TimerState.RUNNING;
        }
    }

    public long getRemainder() {
        return thread.remainder;
    }

    public enum TimerState {
        RUNNING, SUSPENDING, WAITING
    }

    private static class TimeThread extends Thread {
        private final Consumer<Object> action;
        private final Timer timer;
        public long remainder, startTime, endTime;

        public TimeThread(long remainder, Consumer<Object> action, Timer timer) {
            this.remainder = remainder;
            this.action = action;
            this.timer = timer;
            startTime = System.currentTimeMillis();
            endTime = startTime + remainder;
        }

        @Override
        public void run() {
            while (endTime - System.currentTimeMillis() >= 0) {
                remainder = endTime - System.currentTimeMillis();
                if (timer.state.equals(TimerState.SUSPENDING)) {
                    while (timer.state.equals(TimerState.SUSPENDING)) {
                        startTime = System.currentTimeMillis();
                    }
                    endTime = startTime + remainder;
                }
            }
            timer.state = TimerState.WAITING;
            action.accept(null);
        }
    }
}
