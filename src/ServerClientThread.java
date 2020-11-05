import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;
import java.time.LocalDate;

class ServerClientThread extends Thread {
    Connection connection;
    Socket serverClient;
    String userEmail;
    int clientNo;

    ServerClientThread(Socket inSocket, Connection conn, int clientNo){
            serverClient = inSocket;
            connection = conn;
            this.clientNo=clientNo;
    }

    public boolean deleteAD (int IdADD, int prop){
            String req=" delete from annonces where id = "+IdADD+" and prop = "+prop+" ;";
            Statement statement= null;
        try {
            statement = connection.createStatement();
            int rep = statement.executeUpdate(req);
            if (rep ==1)return true;
            else return false;
        } catch (SQLException throwables) {
            return false;
        }

    }


    public boolean editAD( String title,String description, int price, int domain, int prop, int id)  {
        String req  = "update annonces set titre ='"+title+"', description='"+description+"',prix="+price+",domaine="+domain+" where id="+id+" and prop= "+prop+" ;";
        Statement statement= null;
        try {
            statement = connection.createStatement();
            int rep = statement.executeUpdate(req);
            if (rep ==1)return true;
            else return false;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public String getAdByID(int id) throws SQLException {
        String rep ="";
        String req = "select * from annonces where id = "+id +";";
        Statement statement= connection.createStatement();
        ResultSet rs = statement.executeQuery(req);
        if (rs.next()) rep += id + "|" +rs.getString("titre") + "|"+rs.getString("description") +"|" +rs.getString("prix") + "|" + rs.getString("domaine") + "|" +rs.getString("prop") + "|" + rs.getString("date") + "|" +rs.getString("reserved");
        return rep;

    }

    public String getAds() throws SQLException {
        String rep="";
        String req = "select * from annonces where reserved=0";
        Statement statement= connection.createStatement();
        ResultSet rs=statement.executeQuery(req);
        while (rs.next()){
            rep += rs.getInt("id") + "|" +rs.getString("titre") + "|"+rs.getString("description") +"|";
        }
        return rep;
    }

    public int getClientId(String email) throws SQLException {
        Statement statement= connection.createStatement();
        ResultSet resultset = statement.executeQuery("select id from client where email='"+email+"';");
        if (resultset!=null) resultset.next();
        else return -1;
        return resultset.getInt(1);

    }

    public  int addAnns( String titre, String desc, int prix, int domaine, int prop, String date) throws SQLException {
        String str= "insert into annonces (titre,description,prix,domaine,prop,date,reserved) values ('"+titre+"','"+desc+"',"+prix+","+domaine+","+prop+",'"+date+"',0);";
        System.out.println(str);
        Statement statement= connection.createStatement();
        return statement.executeUpdate(str);
    }

    public int addClient( String nom, String prenom, String DateNS, String email, String pwd, String adr, String codepostal, String commune, String phone) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "insert into client (nom,prenom,DateNS,email,pwd,adr,codePostal,commune,phone) values ('"+nom+"','"+prenom+"','"+DateNS+"','"+email+"','"+pwd+"','"+adr+"','"+codepostal+"','"+commune+"','"+phone+"');";
        int insert = statement.executeUpdate(query);
        return insert;
    }

    public int UpdateClient(String nom, String prenom, String DateNS, String email, String pwd, String adr, String codepostal, String commune, String phone, String OldPwd) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "update client  set nom ='"+ nom+"', prenom = '"+prenom+"',DateNS = '"+DateNS+"',email='"+email+"' , pwd = '"+ pwd+"' ,adr = '"+ adr +"', codePostal = '"+codepostal +"',commune = '"+ commune+"',phone = '"+ phone+ "'  where email ='"+userEmail+"' and pwd='"+OldPwd+"';";
        System.out.println(query);
        int insert = statement.executeUpdate(query);
        if (insert==1) userEmail=email;
        connection.close();
        return insert;
    }

    public void run(){
        try{
            Statement statement = connection.createStatement();
            DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
            DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());
            String clientMessage="", serverMessage="Connected to server, type :\nLOGIN\nSIGNUP";
            outStream.writeUTF(serverMessage);
            outStream.flush();
            clientMessage=inStream.readUTF();

            System.out.println("From Client-" +clientNo+ ": Message :"+clientMessage);
            String[] req = clientMessage.split("\\|");
            if (req[0].equals("LOGIN")){
                while (true) {
                    ResultSet rs = statement.executeQuery("select * from client where email='" + req[1] + "' and pwd ='" + req[2] + "';");
                    if (rs.next())
                    {serverMessage = String.valueOf(getClientId(req[1]));
                    userEmail = req[1];}
                    else serverMessage = "-1";
                    outStream.writeUTF(serverMessage);
                    outStream.flush();
                    if (serverMessage.equals("-1")) {
                        clientMessage=inStream.readUTF();
                        System.out.println(clientMessage);
                        req = clientMessage.split("\\|");
                        System.out.println(req[1]+req[2]);}
                    else break;
                }

            }
            else if(req[0].equals("SIGNUP")){
                while (true){
                    try {
                        int report = addClient(req[1], req[2], req[3], req[4], req[5], req[6], req[7], req[8], req[9]);
                        if (report == 1) {
                            serverMessage = String.valueOf(getClientId(req[4]));
                            userEmail = req[4];

                            outStream.writeUTF(serverMessage);
                            outStream.flush();
                            break;
                        }

                    else {
                        serverMessage="-1";
                        outStream.writeUTF(serverMessage);
                        outStream.flush();
                        clientMessage=inStream.readUTF();
                        System.out.println(clientMessage);
                        req = clientMessage.split("\\|");
                    }
                    } catch (Exception e) {
                        serverMessage="-1";
                        outStream.writeUTF(serverMessage);
                        outStream.flush();
                        clientMessage=inStream.readUTF();
                        System.out.println(clientMessage);
                        req = clientMessage.split("\\|");
                    }
                }
            }

            while(!clientMessage.equals("LOGOUT")){
                clientMessage=inStream.readUTF();
                req = clientMessage.split("\\|");
                System.out.println("From Client-" +clientNo+ ": Message :"+clientMessage);
                switch (req[0]){
                    case "ADDAD":{
                        Date date = Date.valueOf(LocalDate.now());
                        System.out.println(getClientId(userEmail));
                        int rep = addAnns(req[1],req[2],Integer.parseInt(req[3]),Integer.parseInt(req[4]),getClientId(userEmail), String.valueOf(date));
                        outStream.writeUTF(String.valueOf(rep));
                        outStream.flush();
                        break;
                    }
                    case "GETADS":{
                        String anns= getAds();
                        if (anns.equals("")) outStream.writeUTF("No Ads :(");
                        else  outStream.writeUTF(anns);
                        outStream.flush();
                        break;
                    }
                    case "GETAD":{
                        serverMessage=getAdByID(Integer.parseInt(req[1]));
                        if (serverMessage.equals("")) outStream.writeUTF("No AD found for this ID :(");
                        else outStream.writeUTF(serverMessage);
                        outStream.flush();
                        break;
                    }
                    case "UPDATEAD":{
                        if (editAD((req[2]),req[3],Integer.parseInt(req[4]),Integer.parseInt(req[5]),getClientId(userEmail),Integer.parseInt(req[1]))) outStream.writeUTF("success");
                        else outStream.writeUTF("Failed");
                        outStream.flush();
                        break;
                    }
                    case "DELETEAD":{
                        if (deleteAD(Integer.parseInt(req[1]),getClientId(userEmail))) outStream.writeUTF("success");
                        else outStream.writeUTF("Failed");
                        outStream.flush();
                        break;
                    }

                    case "UPDATECLIENT":{
                        if (UpdateClient(req[1],req[2],req[3],req[4],req[6],req[7],req[8],req[9],req[10],req[5])==1) outStream.writeUTF("success");
                        else

                        break;
                    }
                    case "LOGOUT":{
                        inStream.close();
                        outStream.close();
                        serverClient.close();
                        break;
                    }
                }
            }

        }catch(Exception ex){
            System.out.println(ex);
        }finally{
            System.out.println("Client -" + clientNo + " exit!! ");
        }
    }
}