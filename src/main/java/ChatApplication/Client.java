package ChatApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {

    Socket mySocket;
    ServerSocket serverSocket;
    BufferedReader br;
    PrintWriter out;

    public Client()
    {
        try{
            System.out.println("Client is trying to connect with server ...");
            mySocket = new Socket("127.0.0.1",7777);
            br = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            out = new PrintWriter(mySocket.getOutputStream());

            startReading();
            startWriting();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void startReading()
    {
        Runnable r1 = ()->{
            System.out.println("Server is reading data ");
            try {
                while(true)
                {
                    String msg = null;

                        msg = br.readLine();

                    if(msg.equals("quit"))
                    {
                        mySocket.close();
                        break;
                    }
                    System.out.println("Client: "+msg);
                }
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("Connection closed");
            }

        };
        new Thread(r1).start();
    }

    public void startWriting()
    {
        Runnable r1 = ()->{
            System.out.println("Server is writing data ");

            try {
                while(!mySocket.isClosed())
                {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String input = null;

                    input = br1.readLine();
                    out.println(input);
                    out.flush();

                    if(input.equals("quit"))
                    {
                        mySocket.close();
                        break;
                    }
                }
                System.out.println("Connection closed");
            } catch (IOException e) {
               // e.printStackTrace();
                System.out.println("Connection closed");
            }

        };
        new Thread(r1).start();
    }

    public static void main(String[] args) {

        System.out.println("Client is starting up .... ");
        new Client();

    }

}
