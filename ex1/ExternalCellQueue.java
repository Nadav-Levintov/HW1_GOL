package ex1;

import java.util.ArrayList;
import java.util.List;

public class ExternalCellQueue implements Comparable<ExternalCellQueue>{
    ExternalCellQueue(Integer id) {
        this.id = id;
    }

    private Integer getId() {
        return id;
    }

    private Integer id;
    private List<ExternalParams> externalParamsList = new ArrayList<>();

    synchronized List<ExternalParams> dequeue() {
        while (externalParamsList.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        List<ExternalParams> ret = externalParamsList;
        externalParamsList=new ArrayList<>();
        return ret;
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
