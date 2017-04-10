package cizge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dulun on 26.12.2016.
 */
public interface GraphInt<T> extends Serializable {

    ArrayList<T> getAdjacentVertices(T v);

    List<T> getVertices();

    boolean isEdge(T v, T w);

    boolean isVertex(T vertex);

    boolean addEdge(T source, T dest, double weight);

    boolean addVertex(T vertex);

    Edge getEdge(T v, T w);

    String toString();
}
