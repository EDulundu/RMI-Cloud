package cizge;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by dulun on 25.12.2016.
 */
public class Graph<T> implements GraphInt<T> , Serializable {

    private Map<T, ArrayList<Edge<T>>> adjacencyList;

    private List<T> vertices;

    public Graph(){

        this.adjacencyList = new HashMap<T, ArrayList<Edge<T>>>();
        this.vertices = new ArrayList<T>();
    }

    public ArrayList<T> getAdjacentVertices(T v){

        ArrayList<T> out = new ArrayList<T>();

        for(Edge<T> e: adjacencyList.get(v))
            out.add(e.getDest());

        return out;
    }

    public List<T> getVertices(){

        return vertices;
    }

    public boolean isEdge(T v, T w){

        Iterator it = adjacencyList.get(v).iterator();
        while(it.hasNext()){
            Edge e = (Edge) it.next();
            if(e.getDest().equals(w))
                return true;
        }

        return false;
    }

    public boolean isVertex(T vertex){

        return adjacencyList.containsKey(vertex);
    }

    public boolean addEdge(T source, T dest, double weight){

        if(!isVertex(source))
            addVertex(source);

        if(!isVertex(dest))
            addVertex(dest);

        if(isEdge(source, dest)){
            System.out.println("Source: " + source + " Dest: " + dest + " Weight: " + weight + " boyle bir edge zaten var!!!");
            return false;
        }

        adjacencyList.get(source).add(new Edge<T>(source, dest, weight));

        return true;
    }

    public boolean addVertex(T vertex) {

        adjacencyList.put(vertex, new ArrayList<Edge<T>>());

        vertices.add(vertex);

        return true;
    }

    public Edge getEdge(T v, T w){

        ArrayList<Edge<T>> edges = adjacencyList.get(v);
        for(Edge e : edges){
            if(e.getDest().equals(w)){
                return e;
            }
        }

        return null;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        Set<T> set = adjacencyList.keySet();

        Iterator it = set.iterator();
        while(it.hasNext()){
            T v = (T) it.next();
            builder.append(v.toString() + " --> ");
            for (Edge e: adjacencyList.get(v))
                builder.append(e.getDest() + " ");
            builder.append("\n");
        }

        return builder.toString();
    }
}
