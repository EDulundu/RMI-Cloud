package cizge;

import java.io.Serializable;

public class Edge<T> implements Serializable{

    private T source;

    private T dest;

    private double weight;

    public T getSource() {

        return source;
    }

    public void setSource(T source) {

        this.source = source;
    }

    public T getDest() {

        return dest;
    }

    public void setDest(T dest) {

        this.dest = dest;
    }

    public double getWeight() {

        return weight;
    }

    public void setWeight(double weight) {

        this.weight = weight;
    }

    public Edge(T v, T w, double weight){

        this.source = v;
        this.dest = w;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge<?> edge = (Edge<?>) o;

        if (source != null ? !source.equals(edge.source) : edge.source != null) return false;
        if (dest != null ? !dest.equals(edge.dest) : edge.dest != null) return false;

        return true;
    }
}