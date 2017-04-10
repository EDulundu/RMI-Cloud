import cizge.City;
import cizge.Graph;
import cizge.GraphInt;
import cizge.Person;
import server.Account;
import server.service.HamiltonianCycle;
import server.service.ShortestPathFinder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        try {
            GraphInt<String> graph = new Graph<String>();
            graph.addEdge("Istanbul", "Bursa", 1);
            graph.addEdge("Istanbul", "Ankara", 4);
            graph.addEdge("Bursa", "Bilecik", 2);
            graph.addEdge("Bilecik", "Ankara", 3);
            graph.addEdge("Bilecik", "Giresun", 4);
            graph.addEdge("Ankara", "Giresun", 3);
            graph.addEdge("Giresun", "Hatay", 2);
            graph.addEdge("Hatay", "Istanbul", 5);
            graph.addEdge("Ankara", "Hatay", 4);
            System.out.println(graph.toString());

            //////////////////////////////////////////////////////////////////////////////////

            List<String> path = ShortestPathFinder.getShortestPath(graph, "Istanbul", "Bilecik");
            System.out.print("Istanbul" + "-" + "Bilecik arasi en kisa yol: ");
            System.out.println(path.toString());

            /////////////////////////////////////////////////////////////////////////////////

            List<String> path2 = HamiltonianCycle.getHamiltonianCycle(graph);
            System.out.print("Hamiltonian Cycle: ");
            System.out.println(path2.toString());
            System.out.println();

            /////////////////////////////////////////////////////////////////////////////////

            LinkedList<Account> data = new LinkedList<Account>();
            data.add(new Account("emredulundu", "123", 70));
            data.add(new Account("hakanosturk", "abc", 80));
            data.add(new Account("musasow", "qwe", 90));
            data.add(new Account("aykuter", "123", 100));
            data.add(new Account("mustafa", "456", 10));
            data.add(new Account("ali", "789", 20));
            data.add(new Account("omer", "a1b2", 30));
            data.add(new Account("hasan", "a1b3",40));
            data.add(new Account("mehmet", "a1b4", 0));
            data.add(new Account("dursun", "pass", 50));
            data.add(new Account("veli", "fail", 60));
            data.add(new Account("ahmet", "aa", 150));
            data.add(new Account("yasin", "bb", 70));
            data.add(new Account("onur", "cc", 55));
            data.add(new Account("recep", "dd", 40));

            FileOutputStream fos = new FileOutputStream("data.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (int i = 0; i < data.size(); i++)
                oos.writeObject(data.get(i));
            oos.close();

            /////////////////////////////////////////////////////////////////////////////////

            GraphInt<City> g = new Graph<City>();

            City c = new City("Istanbul");
            City c2 = new City("Bilecik");
            City c3 = new City("Ankara");
            City c4 = new City("Bursa");
            City c5 = new City("Hatay");
            City c6 = new City("Giresun");

            g.addEdge(c, c4, 100);
            g.addEdge(c, c3, 400);
            g.addEdge(c4,c2, 200);
            g.addEdge(c2, c3, 300);
            g.addEdge(c2, c6, 400);
            g.addEdge(c3, c6, 300);
            g.addEdge(c6, c5, 200);
            g.addEdge(c5, c, 500);
            g.addEdge(c3, c5, 400);

            FileOutputStream f = new FileOutputStream("city.bin");
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(g);
            o.close();

            FileInputStream ff = new FileInputStream("city.bin");
            ObjectInputStream oo = new ObjectInputStream(ff);

            GraphInt<City> gg = (GraphInt<City>) oo.readObject();
            oo.close();
            System.out.println(gg.toString());

            /////////////////////////////////////////////////////////////////////////////////

            GraphInt<Person> gPerson = new Graph<Person>();

            Person p = new Person("Ali", 15);
            Person p2 = new Person("Veli", 20);
            Person p3 = new Person("Mehmet", 10);
            Person p4 = new Person("Cansu", 18);
            Person p5 = new Person("Mahmut", 28);
            Person p6 = new Person("Hakan", 33);
            Person p7 = new Person("Dilara", 8);

            gPerson.addEdge(p,p2, 5);
            gPerson.addEdge(p,p7, 7);
            gPerson.addEdge(p3,p, 5);
            gPerson.addEdge(p5,p, 3);
            gPerson.addEdge(p5, p3, 18);
            gPerson.addEdge(p5, p4, 2);
            gPerson.addEdge(p4, p3, 3);
            gPerson.addEdge(p7, p2, 2);
            gPerson.addEdge(p7, p6, 3);
            gPerson.addEdge(p2, p6, 6);
            gPerson.addEdge(p6, p5, 5);
            gPerson.addEdge(p2, p5, 8);

            FileOutputStream file = new FileOutputStream("person.bin");
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(gPerson);
            out.close();

            FileInputStream file2 = new FileInputStream("person.bin");
            ObjectInputStream output = new ObjectInputStream(file2);

            GraphInt<Person> gPerson2 = (GraphInt<Person>) output.readObject();
            output.close();
            System.out.println(gPerson2.toString());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
