package server.service;

import cizge.Edge;
import cizge.GraphInt;

import java.util.List;
import java.util.Stack;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Created by dulun on 25.12.2016.
 */
public class ShortestPathFinder {

    public static <T> List<T> getShortestPath(GraphInt<T> graph, T A, T B){

        HashMap<T, T> pred = dijkstraAlgorithm(graph, A);

        Stack<T> shortestPath = new Stack<T>();
        shortestPath.push(B);

        T temp = shortestPath.peek();
        while(!pred.get(temp).equals(A)){
            shortestPath.push(pred.get(temp));
            temp = shortestPath.peek();
        }
        shortestPath.push(pred.get(temp));

        List<T> path = new ArrayList<T>();
        while(!shortestPath.isEmpty())
            path.add(shortestPath.pop());

        return path;
    }

    private static <T> HashMap<T, T> dijkstraAlgorithm(GraphInt<T> graph, T A){

        HashMap<T, Double> distances = new HashMap<T, Double>();
        HashMap<T, T> pred = new HashMap<T, T>();
        ArrayList<T> visited = new ArrayList<T>();

        List<T> vertices = graph.getVertices();
        for (int i = 0; i < vertices.size(); i++) {
            T vertex = vertices.get(i);
            if(graph.getEdge(A, vertex) == null)
                distances.put(vertex, Double.POSITIVE_INFINITY);
            else
                distances.put(vertex, graph.getEdge(A, vertex).getWeight());
            pred.put(vertices.get(i), A);
        }

        Queue<T> queue = new LinkedList<T>();
        queue.add(A);
        while(!queue.isEmpty()){

            T v = queue.poll();
            for(T u : graph.getAdjacentVertices(v)){

                Edge e = graph.getEdge(v, u);
                if(e == null)
                    continue;

                double totalDistance = distances.get(v) + e.getWeight();
                if(totalDistance < distances.get(u)) {
                    distances.put(u, distances.get(v) + e.getWeight());
                    pred.put(u, v);
                }

                if (!visited.contains(u) && !queue.contains(u))
                    queue.add(u);
            }
            visited.add(v);
        }

        return pred;
    }
}
