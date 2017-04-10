package server;

import cizge.GraphInt;
import server.service.HamiltonianCycle;
import server.service.ShortestPathFinder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class GraphRemoteImpl implements GraphRemote {

    private List<Account> users = null;

    private int costOfShortestPath = 10;

    private int costOfHamiltoianPath = 15;

    private String hostName = null;

    public GraphRemoteImpl() throws RemoteException {

        super();
        this.users = new ArrayList<Account>();

        loadDataBase();
    }

    @Override
    public void launchServer(String ip, int port) throws RemoteException {

        hostName = "BULUTAS";
        System.setProperty("java.rmi.server.hostname", ip);
        GraphRemote graphService = (GraphRemote) UnicastRemoteObject.exportObject(this, port);
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind(hostName, graphService);

        System.out.println("Server started at " + LocalTime.now() + "...");
        System.out.println();
    }

    @Override
    public boolean createAccount(String username, String password) throws RemoteException {

        if(username == null || password == null)
            return false;

        for (Account acc : users)
            if(acc.getUsername().equals(username))
                return false;

        Account a = new Account(username, password);
        users.add(a);

        updateData();

        return true;
    }

    @Override
    public boolean login(String username, String password){

        for(Account acc : users)
            if(acc.getUsername().equals(username) && acc.getPassword().equals(password))
                return true;

        return false;
    }

    @Override
    public boolean loadCredit(String username, int credit) throws RemoteException {

        for(Account acc : users){
            if(acc.getUsername().equals(username)){
                acc.setCredit(credit);
                updateData();
                return true;
            }
        }

        return false;
    }

    @Override
    public <T> String getShortestPath(GraphInt<T> graph, T A, T B, String username) throws RemoteException {

        if(!hasCredit(username, costOfShortestPath))
            return null;

        System.out.print("Request: GetShortestPath     - Call Time: " + LocalTime.now() + " - ");

        long startTime = System.nanoTime();

        List<T> result = ShortestPathFinder.getShortestPath(graph, A, B);

        long endTime   = System.nanoTime();
        long totalTime = (endTime - startTime) / 1000;
        System.out.println("Took: " + totalTime + " microseconds");

        if(result.toString().equals("[]"))
            return "Shortest Path bulunamadi";

        return result.toString();
    }

    @Override
    public <T> String getHamiltonianCycle(GraphInt<T> graph, String username) throws RemoteException {

        if(!hasCredit(username, costOfHamiltoianPath))
            return null;

        System.out.print("Request: GetHamiltonianCycle - Call Time: " + LocalTime.now() + " - ");

        long startTime = System.nanoTime();

        List<T> result = HamiltonianCycle.getHamiltonianCycle(graph);

        long endTime   = System.nanoTime();
        long totalTime = (endTime - startTime) / 1000;
        System.out.println("Took: " + totalTime + " microseconds");

        if(result.toString().equals("[]"))
            return "Hamiltonian Cycle bulunamadi";

        return result.toString();
    }

    private boolean hasCredit(String username, int cost) {

        for(Account acc : users){
            if(username.equals(acc.getUsername())){
                if(acc.getCredit() > cost){
                    acc.setCredit(acc.getCredit() - cost);
                    updateData();
                    return true;
                }
                else
                    return false;
            }
        }

        return false;
    }

    private void updateData() {

        try {
            FileOutputStream f = new FileOutputStream("data.bin");
            ObjectOutputStream o = new ObjectOutputStream(f);

            for (int i = 0; i < users.size(); i++)
                o.writeObject(users.get(i));
            o.close();

        }catch (Exception e){
            e.getMessage();
        }
    }

    private void loadDataBase() {

        try{
            FileInputStream fos = new FileInputStream("data.bin");
            ObjectInputStream oos = new ObjectInputStream(fos);

            while( fos.available() > 0)
                users.add((Account) oos.readObject());

            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try {

            GraphRemote server = new GraphRemoteImpl();

            server.launchServer("192.168.2.79", 8080);

        }catch (Exception e){
            e.getMessage();
        }
    }
}
