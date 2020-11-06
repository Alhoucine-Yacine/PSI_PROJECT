
import java.net.*;
import java.io.*;


public class Client {
    private static final String CONFIG_FILE = "config.txt";

    public static String getServerIp() throws IOException {
        FileReader fr = new FileReader(CONFIG_FILE);
        BufferedReader br = new BufferedReader(fr);
        String ip=br.readLine();
        br.readLine();
        String portTCP= br.readLine();
        return ip+","+portTCP;

    }

    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void execCommande(){


                try {
                    String serverMessage="";
                    String[] serverIPPORT = getServerIp().split(",");
                    Socket socket=new Socket(serverIPPORT[0], Integer.parseInt(serverIPPORT[1]));
                    DataInputStream inStream=new DataInputStream(socket.getInputStream());
                    DataOutputStream outStream=new DataOutputStream(socket.getOutputStream());
                    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
                    String clientMessage;
                    serverMessage=inStream.readUTF();
                    System.out.println(serverMessage);
                    serverMessage="-2";
                    System.out.print("-> ");
                    clientMessage=br.readLine();
                    while (true){
                        try {
                            if (clientMessage.toUpperCase().equals("LOGIN") || clientMessage.toUpperCase().equals("SIGNUP")) {
                                if (clientMessage.toUpperCase().equals("LOGIN")) {
                                    System.out.print("Enter your email :\n->");
                                    String email = br.readLine();
                                    System.out.print("\nEnter your password :\n->");
                                    String pswd = br.readLine();
                                    clientMessage  = "LOGIN|" + email+"|"+pswd;
                                    outStream.writeUTF(clientMessage);
                                    outStream.flush();
                                    serverMessage=inStream.readUTF();
                                    System.out.println(serverMessage);
                                }
                                if (serverMessage.equals("-1")) {clientMessage="LOGIN"; serverMessage="-2";}
                                if (Integer.parseInt(serverMessage)>-1) { setID(Integer.parseInt(serverMessage));break; }

                                if (clientMessage.toUpperCase().equals("SIGNUP")) {
                                    System.out.print("Enter your First name :\n->");
                                    String FName = br.readLine();
                                    System.out.print("\nEnter your Last name :\n->");
                                    String Lname = br.readLine();
                                    System.out.print("Enter your Date of birth (yyyy-mm-dd) :\n->");
                                    String DN = br.readLine();
                                    System.out.print("\nEnter your email :\n->");
                                    String email = br.readLine();
                                    System.out.print("\nEnter your Password :\n->");
                                    String pwd = br.readLine();
                                    System.out.print("\nEnter your address :\n->");
                                    String adr = br.readLine();
                                    System.out.print("\nEnter your Postal code :\n->");
                                    String Pcode = br.readLine();
                                    System.out.print("\nEnter your city :\n->");
                                    String city = br.readLine();
                                    System.out.print("\nEnter your phone number :\n->");
                                    String phone = br.readLine();
                                    clientMessage = "SIGNUP|"+FName+"|"+Lname+"|"+DN+"|"+email+"|"+pwd+"|"+adr+"|"+Pcode+"|"+city+"|"+phone;
                                    outStream.writeUTF(clientMessage);
                                    outStream.flush();
                                    serverMessage=inStream.readUTF();
                                    System.out.println(serverMessage);
                                }
                                if (serverMessage.equals("-1")) clientMessage="SIGNUP";
                                if (Integer.parseInt(serverMessage)>-1) { setID(Integer.parseInt(serverMessage)); break;}

                            }
                            else {
                                System.out.print("Wrong request :(\n-> ");
                                clientMessage = br.readLine();}

                        }catch (Exception e) {
                            System.out.print("Wrong request :(\n-> ");
                            clientMessage = br.readLine();
                        }

                    }

                    while(!clientMessage.equals("LOGOUT")){
                    System.out.println("Choose an op :");
                    System.out.println("1- All Ads");
                    System.out.println("2- Information about an Ad");
                    System.out.println("3- Post a new Ad");
                    System.out.println("4- Update your Ad");
                    System.out.println("5- Delete your Ad");
                    System.out.println("6- Update your information");
                    System.out.println("7- Reserve an Ad");
                    System.out.println("8- Unreserve an Ad");
                    System.out.println("9- Logout");

                    System.out.print("-> ");
                    clientMessage = br.readLine();
                    String req;
                    switch (Integer.parseInt(clientMessage)){
                        case (3):{
                            req="ADDAD|";
                            System.out.print("Title on the Ad :\n-> ");
                            req += br.readLine()+"|";
                            System.out.print("Description of the Ad :\n-> ");
                            req += br.readLine()+"|";
                            System.out.print("Price of the product :\n-> ");
                            req += br.readLine()+"|";
                            System.out.print("Domain_ID of the Ad :\n-> ");
                            req += br.readLine();
                            outStream.writeUTF(req);
                            outStream.flush();
                            serverMessage=inStream.readUTF();
                            if (Integer.parseInt(serverMessage)==1) System.out.println("Ad successfully added !");
                            else System.out.println("Failed !");
                            break;
                        }

                        case (9): {clientMessage="LOGOUT";
                                outStream.writeUTF(clientMessage);
                                outStream.flush();
                                System.out.println("logged out !");
                                break;
                        }

                        case (2) : {
                            clientMessage="GETAD|";
                            System.out.print("Enter the ID :\n-> ");
                            clientMessage+=br.readLine();
                            outStream.writeUTF(clientMessage);
                            outStream.flush();
                            serverMessage=inStream.readUTF();
                            String[] Data = serverMessage.split("\\|");
                            if (Data.length==1) System.out.println(serverMessage);
                            else {
                                System.out.println("Title : " + Data[1]);
                                System.out.println("Description : " + Data[2]);
                                System.out.println("Price : " + Data[3]);
                                System.out.println("Domain_ID : " + Data[4]);
                                System.out.println("Owner_ID : " + Data[5]);
                                System.out.println("Posted On : " + Data[6]);
                                if (Data[7].equals("1")) System.out.println("Reserved already");
                                else System.out.println("Not reserved yet");
                                System.out.println("\n");

                            }
                            break;
                        }

                        case (1):{
                            req="GETADS|true";
                            outStream.writeUTF(req);
                            outStream.flush();
                            serverMessage=inStream.readUTF();
                            String[] anns = serverMessage.split("\\|");
                            if (anns.length>1){
                                for (int i=0;i<anns.length;i++){
                                 if (i%3==0) System.out.println("\n");
                                    System.out.print(anns[i]+"--");
                                }
                            }
                            System.out.println("\n");
                            break;
                        }

                        case (6):{
                            req ="UPDATECLIENT|";
                            System.out.print("Enter your First name :\n->");
                            String FName = br.readLine();
                            System.out.print("\nEnter your Last name :\n->");
                            String Lname = br.readLine();
                            System.out.print("Enter your Date of birth (yyyy-mm-dd) :\n->");
                            String DN = br.readLine();
                            System.out.print("\nEnter your email :\n->");
                            String email = br.readLine();
                            System.out.print("\nEnter your old Password :\n->");
                            String pwd = br.readLine();
                            System.out.print("\nEnter your new Password :\n->");
                            String Npwd = br.readLine();
                            System.out.print("\nEnter your address :\n->");
                            String adr = br.readLine();
                            System.out.print("\nEnter your Postal code :\n->");
                            String Pcode = br.readLine();
                            System.out.print("\nEnter your city :\n->");
                            String city = br.readLine();
                            System.out.print("\nEnter your phone number :\n->");
                            String phone = br.readLine();
                            req += FName+"|"+Lname+"|"+DN+"|"+email+"|"+pwd+"|"+Npwd+"|"+adr+"|"+Pcode+"|"+city+"|"+phone+"|"+getID();
                            outStream.writeUTF(req);
                            outStream.flush();
                            serverMessage=inStream.readUTF();
                            if (serverMessage.equals("success")) System.out.println("Your info had been updated !");
                            else System.out.println("Failed to update :(");
                            break;
                        }

                        case (4):{
                            req="UPDATEAD|";
                            System.out.print("Enter the ID :\n-> ");
                            req+=br.readLine()+"|";
                            System.out.print("Title on the Ad :\n-> ");
                            req += br.readLine()+"|";
                            System.out.print("Description of the Ad :\n-> ");
                            req += br.readLine()+"|";
                            System.out.print("Price of the product :\n-> ");
                            req += br.readLine()+"|";
                            System.out.print("Domain_ID of the Ad :\n-> ");
                            req += br.readLine();
                            outStream.writeUTF(req);
                            outStream.flush();
                            serverMessage=inStream.readUTF();
                            if (serverMessage.equals("success")) System.out.println("AD Updated !");
                            else System.out.println("Failed to update");
                            break;
                        }

                        case(5):{
                            req="DELETEAD|";
                            System.out.print("Enter the ID :\n-> ");
                            req+=br.readLine();
                            outStream.writeUTF(req);
                            outStream.flush();
                            serverMessage=inStream.readUTF();
                            if (serverMessage.equals("success")) System.out.println("AD Deleted !");
                            else System.out.println("Failed to delete");
                            break;
                        }

                        case (7) : {
                            req = "RESERVEAD|";
                            System.out.print("Enter the ID :\n-> ");
                            req+=br.readLine();
                            outStream.writeUTF(req);
                            outStream.flush();
                            serverMessage=inStream.readUTF();
                            if (serverMessage.equals("success")) System.out.println("AD Reserved !");
                            else System.out.println("Failed to reserve");
                            break;

                        }

                        case (8) : {
                            req = "UNRESERVEAD|";
                            System.out.print("Enter the ID :\n-> ");
                            req+=br.readLine();
                            outStream.writeUTF(req);
                            outStream.flush();
                            serverMessage=inStream.readUTF();
                            if (serverMessage.equals("success")) System.out.println("AD Uneserved !");
                            else System.out.println("Failed to Unreserve");
                            break;

                        }
                    }
                    }
                    outStream.close();
                    outStream.close();
                    socket.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }


    public static void main(String[] args) throws Exception {
        Client tcpClient2 = new Client();
        tcpClient2.execCommande();

    }
}