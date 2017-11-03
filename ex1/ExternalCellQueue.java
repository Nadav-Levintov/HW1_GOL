package ex1;

import java.util.LinkedList;
import java.util.Queue;

public class ExternalCellQueue {
    private Queue<ExternalParams> externalParamsList = new LinkedList<>();

    public synchronized ExternalParams dequeue() {
        while (externalParamsList.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ExternalParams ret = externalParamsList.remove();

        return ret;
    }

    public synchronized void enqueue(ExternalParams params) {
        externalParamsList.add(params);
        notifyAll();
    }
}
