package ex1;

import java.util.LinkedList;
import java.util.Queue;

public class ExternalCellQueue implements Comparable<ExternalCellQueue>{
    ExternalCellQueue(Integer id) {
        this.id = id;
    }

    private Integer getId() {
        return id;
    }

    private Integer id;
    private Queue<ExternalParams> externalParamsList = new LinkedList<>();

    synchronized ExternalParams dequeue() {
        while (externalParamsList.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return externalParamsList.remove();
    }

    synchronized void enqueue(ExternalParams params) {
        externalParamsList.add(params);
        notifyAll();
    }

    @Override
    public int compareTo(ExternalCellQueue o) {
        return this.id.compareTo(o.getId());
    }
}
