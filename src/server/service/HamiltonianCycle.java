package server.service;


import cizge.GraphInt;

import java.util.*;

/**
 * Created by dulun on 26.12.2016.
 */
public class HamiltonianCycle {

    public static <T> List<T> getHamiltonianCycle(GraphInt<T> graph){

        List<T> result = new ArrayList<T>();
        Set<T> visited = new HashSet<T>();

        T start = graph.getVertices().iterator().next();

        hamiltonian(graph, start, start, result, visited);

        return result;
    }

    private static <T> boolean hamiltonian(GraphInt<T> graph, T start, T current, List<T> result, Set<T> visited){

        visited.add(current);
        result.add(current);

        for(T child : graph.getAdjacentVertices(current)){
            if(start.equals(child) && graph.getVertices().size() == result.size()){
                result.add(start);
                return true;
            }

            if(!visited.contains(child))
                if(hamiltonian(graph, start, child, result, visited))
                    return true;
        }

        result.remove(result.size() - 1);
        visited.remove(current);

        return false;
    }
}
