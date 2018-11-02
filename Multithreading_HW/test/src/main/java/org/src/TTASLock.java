package org.src;
import java.util.concurrent.atomic.AtomicBoolean;

public class TTASLock {
    AtomicBoolean state = new AtomicBoolean(false);

    void lock() {
        while (true) {
            while (state.get()) {}
            if (!state.getAndSet(true)) {
                return;
            }
        }
    }

    void unlock() {
        state.set(false);
    }
}
