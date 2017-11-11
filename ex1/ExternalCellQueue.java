package ex1;

import java.util.ArrayList;
import java.util.List;

public class ExternalCellQueue implements Comparable<ExternalCellQueue>{

    /* this is a producer-consumer queue used for communication between threads */

    private Integer id;
    private List<ExternalParams> externalParamsList = new ArrayList<>();

    ExternalCellQueue(Integer id) {
        this.id = id;
    }

    private Integer getId() {
        return id;
    }

    synchronized List<ExternalParams> dequeue() {
        /* consumer thread uses this function to consume */
        while (externalParamsList.isEmpty()) {
            try {
                /* if the queue is empty the consumer thread waits to be notified */
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /* we return the entire list and allocate a new one for the queue so the thread can continue
         * to work for as long as it can with out entering the synchronized function again
         */

        List<ExternalParams> ret = externalParamsList;
        externalParamsList=new ArrayList<>();
        return ret;
    }

    synchronized void enqueue(ExternalParams params) {
        /* producer threads uses this function to produce */
        externalParamsList.add(params);
        notifyAll();
    }

    @Override
    public int compareTo(ExternalCellQueue o) {
        return this.id.compareTo(o.getId());
    }
}
