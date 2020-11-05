
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.sql.*;


public class Server {
    private static final String CONFIG_FILE = "config.txt";
    public int nbClients;

    public Server(){
        nbClients=0;
    }

    public int getTCPPort() throws IOException {
        FileReader fr = new FileReader(CONFIG_FILE);
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        br.readLine();
        return (Integer.parseInt(br.readLine()));

    }


    public static String getServerIp() throws IOException {
        FileReader fr = new FileReader(CONFIG_FILE);
        BufferedReader br = new BufferedReader(fr);
        String ip=br.readLine();
        String port=br.readLine();
        String portTCP= br.readLine();
        return ip+","+port;

    }


    public static Connection getConnection(String name, String pass) throws Exception {
        String currentLine[] = getServerIp().split(",");
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://" + currentLine[0] + ":"+currentLine[1]+"/PRJPSI2?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
        String username = name;
        String passwd = pass;
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, passwd);
        System.out.println("Connected to DataBase ....");
        return conn;
    }


    public static  void main(String[] args) throws Exception {
        Server server = new Server();
        ServerSocket serverSocket=new ServerSocket(server.getTCPPort());
        int counter=0;
        System.out.println("Server Started on : " + InetAddress.getLocalHost()+ " , Port : " + server.getTCPPort());
        Connection connection=server.getConnection("root","");

        while(true){
            counter++;
            Socket serverClient=serverSocket.accept();
            System.out.println(" >> " + "Client No:" + counter + " started!");
            ServerClientThread sct = new ServerClientThread(serverClient , connection, counter);
            sct.start();
        }

    }
}
