package Server;

import java.net.*;
import java.io.*;

public class FileServer
{
    public static void main(String[] args)
    {
        int nPort = Integer.parseInt(args[0]);
        System.out.println("Server: Listening on port " + args[0] + "...");
        ServerSocket serverSocket;
        Socket serverEndpoint;

        try
        {
            serverSocket = new ServerSocket(nPort);

            serverEndpoint = serverSocket.accept();

            String fileName = "Download.txt";

            File file = new File(fileName);
            byte[] buffer = new byte[4096];
            FileInputStream fis = new FileInputStream(file);
            //System.out.println("Server: New client connected: " + serverEndpoint.getRemoteSocketAddress());

            DataOutputStream dosWriter = new DataOutputStream(serverEndpoint.getOutputStream());

            DataInputStream disReader = new DataInputStream(serverEndpoint.getInputStream());
            System.out.println(disReader.readUTF());


            //System.out.println(file.length());
            dosWriter.writeInt( (int) file.length());

            System.out.println("Server: Sending file \"Download.txt\" ("+file.length()+" bytes)");

            int bytesRead;
            while((bytesRead = fis.read(buffer)) != -1){
                dosWriter.write(buffer, 0, bytesRead);
            }

            fis.close();
            dosWriter.close();
            serverEndpoint.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            System.out.println("Server: Connection is terminated.");
        }
    }
}