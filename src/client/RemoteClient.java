package client;

import cizge.City;
import cizge.Graph;
import cizge.GraphInt;
import cizge.Person;
import server.GraphRemote;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;
import java.util.Scanner;

public class RemoteClient {

    private String username = null;
    private String password = null;
    private String hostName = null;
    private GraphRemote graphService = null;

    private GraphInt<String> graphString = new Graph<String>();
    private GraphInt<City> graphCity = new Graph<City>();
    private GraphInt<Person> grapPerson = new Graph<Person>();

    private GraphInt myGraph = null;
    private String type = null;

    public RemoteClient() throws IOException, ClassNotFoundException {

        this.graphString = new Graph<String>();
        graphString.addEdge("Istanbul", "Bursa", 1);
        graphString.addEdge("Istanbul", "Ankara", 4);
        graphString.addEdge("Bursa", "Bilecik", 2);
        graphString.addEdge("Bilecik", "Ankara", 3);
        graphString.addEdge("Bilecik", "Giresun", 4);
        graphString.addEdge("Ankara", "Giresun", 3);
        graphString.addEdge("Giresun", "Hatay", 2);
        graphString.addEdge("Hatay", "Istanbul", 5);
        graphString.addEdge("Ankara", "Hatay", 4);

        this.graphCity = new Graph<City>();
        FileInputStream ff = new FileInputStream("city.bin");
        ObjectInputStream oo = new ObjectInputStream(ff);
        graphCity = (GraphInt<City>) oo.readObject();
        oo.close();

        this.grapPerson = new Graph<Person>();
        FileInputStream file2 = new FileInputStream("person.bin");
        ObjectInputStream output = new ObjectInputStream(file2);
        GraphInt<Person> grapPerson = (GraphInt<Person>) output.readObject();
        output.close();
    }

    public void connetToServer(String ip, int port) throws RemoteException, NotBoundException {

        hostName = "BULUTAS";
        Registry registry = LocateRegistry.getRegistry(ip, port);
        this.graphService = (GraphRemote) registry.lookup(hostName);
    }

    private boolean login() throws RemoteException {

        // login to server
        Scanner input = new Scanner(System.in);
        System.out.println("************* WELCOME TO BULUT CIZGE A.S SERVICE *************");
        System.out.println("Connection Time: " + LocalTime.now());

        int select = 0;
        boolean ans = false;
        do{
            System.out.print("1) Create Account 2) login 3) Quit" + "\n>");
            select = input.nextInt();

            switch (select){
                case 1:
                    while(!ans) {
                        System.out.print("username: ");
                        username = input.next();
                        System.out.print("password: ");
                        password = input.next();
                        if(graphService.createAccount(username, password))
                            ans = true;
                        else
                            System.out.println("Boyle bir username var. Tekrar deneyiniz!!!");
                    }
                    break;

                case 2:
                    System.out.print("username: ");
                    username = input.next();
                    System.out.print("password: ");
                    password = input.next();
                    ans = this.graphService.login(username, password);
                    break;

                case 3:
                    System.out.println("Quit!!!");
                    return false;

                default:
                    System.out.println("Wrong input!!!");
                    break;
            }
        }while (!ans);

        System.out.println("> Success login to Server!!!");

        return ans;
    }

    private void menu(){
        System.out.println("******* 1) Create Graph (Integer, Character, String, City, Person)");
        System.out.println("******* 2) Add Edge");
        System.out.println("******* 3) Add Vertex");
        System.out.println("******* 4) Get Shortest Path");
        System.out.println("******* 5) Get Hamiltonian Cycle");
        System.out.println("******* 6) Try GetShortestPath or HamiltonianCycle");
        System.out.println("******* 7) Exit");
        System.out.println("******* 8) Print Graph");
        System.out.println("******* 9) Load Credit");
        System.out.print("> ");
    }

    private void createGraph(){

        Scanner in = new Scanner(System.in);
        System.out.println("1) Integer 2) Character 3) String 4) City 5) Person");
        System.out.print("> ");
        int select = in.nextInt();
        switch (select){
            case 1:
                this.myGraph = new Graph<Integer>();
                this.type = "Integer";
                break;
            case 2:
                this.myGraph = new Graph<Character>();
                this.type = "Character";
                break;
            case 3:
                this.myGraph = new Graph<String>();
                this.type = "String";
                break;
            case 4:
                this.myGraph = new Graph<City>();
                this.type = "City";
                break;
            case 5:
                this.myGraph = new Graph<Person>();
                this.type = "Person";
                break;
        }
    }

    private void addEdge(){

        System.out.println("Your Graph");
        System.out.println(myGraph.toString());
        Scanner in = new Scanner(System.in);

        if(type.equals("Integer")){
            System.out.print("Please Enter the 2 - Integer and weight\n" + "> ");
            int a = in.nextInt(); System.out.print("> ");
            int b = in.nextInt(); System.out.print("> ");
            double w = in.nextDouble();
            myGraph.addEdge(a, b, w);
        }else if(type.equals("Character")){
            System.out.println("Please Enter the 2 - Character and weight");
            String a = in.next(); System.out.print("> ");
            String b = in.next(); System.out.print("> ");
            double w = in.nextDouble();
            myGraph.addEdge(a.charAt(0), b.charAt(0), w);
        }else if(type.equals("String")){
            System.out.println("Please Enter the 2 - String and weight");
            String a = in.next(); System.out.print("> ");
            String b = in.next();
            double w = in.nextDouble();
            myGraph.addEdge(a, b, w);
        }else if(type.equals("City")){
            System.out.println("Please Enter the 2 - City name and weight");
            City a = new City(in.next()); System.out.print("> ");
            City b = new City(in.next());
            double w = in.nextDouble();
            myGraph.addEdge(a, b, w);
        }else if(type.equals("Person")){
            System.out.println("Please Enter the 2 - Person name and age and a weight");
            String name = in.next(); System.out.print("> ");
            int age = in.nextInt();  System.out.print("> ");
            String name2 = in.next(); System.out.print("> ");
            int age2 = in.nextInt();  System.out.print("> ");
            double w = in.nextDouble();
            myGraph.addEdge(new Person(name, age), new Person(name2, age2), w);
        }else{
            System.out.println("Lutfen dogru tipte veri giriniz!!!");
        }
    }

    private void addVertex(){
        System.out.println("Your Graph's vertices!!!");
        System.out.println(myGraph.toString());
        Scanner in = new Scanner(System.in);
        if(type.equals("Integer")){
            System.out.print("Please Enter the Integer\n" + "> ");
            int a = in.nextInt();
            myGraph.addVertex(a);
        }else if(type.equals("Character")){
            System.out.print("Please Enter the Character\n" + "> ");
            String  a = in.next();
            myGraph.addVertex(a.charAt(0));
        }else if(type.equals("String")){
            System.out.print("Please Enter the String\n" + "> ");
            String a = in.next();
            myGraph.addVertex(a);
        }else if(type.equals("City")){
            System.out.print("Please Enter the City name\n" + "> ");
            City a = new City(in.next());
            myGraph.addVertex(a);
        }else if(type.equals("Person")){
            System.out.print("Please Enter the Person name and age\n" + "> ");
            String name = in.next();
            System.out.print("> ");
            int age = in.nextInt();
            myGraph.addVertex(new Person(name, age));
        }else{
            System.out.println("Lutfen dogru tipte veri giriniz!!!");
        }
    }

    private void getShortest() throws RemoteException {
        System.out.println("Your Graph!!!");
        System.out.println(myGraph.toString());
        System.out.println("Please source and destination vertices!!! Fakat index olarak seciniz");

        for (int i = 0; i < myGraph.getVertices().size(); i++) {
            System.out.println(i + ") " + myGraph.getVertices().get(i).toString());
        }

        System.out.print("> ");
        Scanner in = new Scanner(System.in);
        int source = in.nextInt();
        System.out.print("> ");
        int dest = in.nextInt();

        String result = graphService.getShortestPath(myGraph, myGraph.getVertices().get(source),
                myGraph.getVertices().get(dest), username);
        if(result == null)
            System.out.println("Krediniz bitmis hizmet verilememektedir!!!");
        else
            System.out.println(myGraph.getVertices().get(source) + " - " +
                    myGraph.getVertices().get(dest) + " en kisa yol: " + result);
    }

    private void getHamiltonian() throws RemoteException {
        System.out.println("Your Graph!!!");
        String result = graphService.getHamiltonianCycle(myGraph, username);
        if(result == null){
            System.out.println("Krediniz bitmis hizmet verilememektedir!!!");
        }else{
            System.out.println("Hamiltonian Cycle: " + result);
        }
    }

    private void printOn(){
        System.out.println(myGraph.toString());
    }

    private void loadCredit() throws RemoteException {

        System.out.print("Lutfen credit miktari giriniz...\n" + ">");
        Scanner in = new Scanner(System.in);
        int credit = in.nextInt();
        if(graphService.loadCredit(username, credit)){
            System.out.println("Yukleme Basarili!!!");
        }else{
            System.out.println("Yukleme Basarisiz!!!");
        }
    }

    /**
     * Bu denemek icin var ama bunu calistirirsan bile azalmaktadir credit.
     * @throws RemoteException
     */
    private void tryService() throws RemoteException {

        Scanner in = new Scanner(System.in);
        int select = 0;
        do {
            System.out.print("Please Try 1) String 2) City 3) Person 4) Exit\n" + "> ");
            select = in.nextInt();
            switch (select) {
                case 1:
                    String result = graphService.getShortestPath(graphString, "Istanbul", "Bilecik", username);
                    if(result == null)
                        System.out.println("Credin yok");
                    else
                        System.out.println("Istanbul - Bilecik en kisa yol: " + result);

                    result = graphService.getHamiltonianCycle(graphString, username);
                    if(result == null)
                        System.out.println("Credin yok");
                    else
                        System.out.println("Hamiltonian Cycle: " + result);
                    break;

                case 2:
                    result = graphService.getShortestPath(graphCity, new City("Istanbul"),
                            new City("Bilecik"), username);
                    if(result == null)
                        System.out.println("Credin yok");
                    else
                        System.out.println("Istanbul - Bilecik en kisa yol: " + result);

                    result = graphService.getHamiltonianCycle(graphCity, username);
                    if(result == null)
                        System.out.println("Credin yok");
                    else
                        System.out.println("Hamiltonian Cycle: " + result);
                    break;

                case 3:
                    result = graphService.getShortestPath(grapPerson, new Person("Ali", 15),
                            new Person("Hakan", 33), username);
                    if(result == null)
                        System.out.println("Credin yok");
                    else
                        System.out.println("Ali - Hakan en kisa yol: " + result);

                    result = graphService.getHamiltonianCycle(grapPerson, username);
                    if(result == null)
                        System.out.println("Credin yok");
                    else
                        System.out.println("Hamiltonian Cycle: " + result);
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Wron Input!!!");
                    break;
            }
        }while (select > 0 && select < 4);
    }

    public void run() throws RemoteException, NotBoundException {

        // login to server
        if(!login())
            return;

        Scanner input = new Scanner(System.in);
        do{
            menu();
            int select = input.nextInt();
            switch (select){

                case 1:
                    createGraph();
                    break;

                case 2:
                    addEdge();
                    break;

                case 3:
                    addVertex();
                    break;

                case 4:
                    getShortest();
                    break;

                case 5:
                    getHamiltonian();
                    break;

                case 6:
                    tryService();
                    break;

                case 7:
                    System.out.println("Quit!!!");
                    return;

                case 8:
                    printOn();
                    break;

                case 9:
                    loadCredit();
                    break;

                default:
                    System.out.println("Wrong input!!!");
                    break;
            }
        }while(true);
    }

    public static void main(String[] args) {

        try {
            RemoteClient client = new RemoteClient();

            client.connetToServer("192.168.2.79", 8080);

            client.run();
        } catch (Exception e) {
            System.err.println("cizge.bulut.Graph Error Exception!!!");
            e.printStackTrace();
        }
    }
}
