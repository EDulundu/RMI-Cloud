package server;

import cizge.GraphInt;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by dulun on 26.12.2016.
 */
public interface GraphRemote extends Remote {

    void launchServer(String ip, int port) throws RemoteException;

    boolean createAccount(String username, String password) throws RemoteException;

    boolean login(String username, String password) throws RemoteException;

    boolean loadCredit(String username, int credit) throws RemoteException;

    <T> String getShortestPath(GraphInt<T> graph, T A, T B, String username) throws RemoteException;

    <T> String getHamiltonianCycle(GraphInt<T> graph, String username) throws RemoteException;
}
