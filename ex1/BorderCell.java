package ex1;

import java.util.ArrayList;
import java.util.List;

public class BorderCell extends InnerCell {
    List<WorkQueue> neighborBlockQueue = new ArrayList<>();
    List<InnerCell> blockNeighbors = new ArrayList<>();
}
