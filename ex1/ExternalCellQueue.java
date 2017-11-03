package ex1;

import java.util.LinkedList;
import java.util.Queue;

public class ExternalCellQueue implements Comparable<ExternalCellQueue>{
    public ExternalCellQueue(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    private Integer id;
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

    @Override
    public int compareTo(ExternalCellQueue o) {
        return this.id.compareTo(o.getId());
    }
}
