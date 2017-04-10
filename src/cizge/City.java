package cizge;

import java.io.Serializable;

/**
 * Created by dulun on 28.12.2016.
 */
public class City implements Serializable{

    private String name = null;

    public City(String name){

        this.name = name;
    }

    public String getName() {


        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        City city = (City) o;

        return name != null ? name.equals(city.name) : city.name == null;
    }

    @Override
    public int hashCode() {

        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString(){

        return name;
    }
}
