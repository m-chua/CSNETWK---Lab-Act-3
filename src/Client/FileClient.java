package Client;

import java.net.*;
import java.io.*;

public class FileClient
{
    public static void main(String[] args)
    {
        String sServerAddress = args[0];
        int nPort = Integer.parseInt(args[1]);

        try
        {

            Socket clientEndpoint = new Socket(sServerAddress, nPort);
            System.out.println("Client: Connecting to server at" + clientEndpoint.getRemoteSocketAddress());

            System.out.println("Client: Connected to server at" + clientEndpoint.getRemoteSocketAddress());

            DataOutputStream dosWriter = new DataOutputStream(clientEndpoint.getOutputStream());
            dosWriter.writeUTF("Server: New client connected: " + clientEndpoint.getLocalSocketAddress());

            DataInputStream disReader = new DataInputStream(clientEndpoint.getInputStream());
            //String filename = disReader.readUTF();
            long fileSize = disReader.readInt();
            //System.out.println(fileSize);
            FileOutputStream fos = new FileOutputStream("Received.txt");

            byte[] buffer = new byte[4096];
            int bytesRead;
            long totalBytesRead = 0;

            while (totalBytesRead < fileSize && (bytesRead = disReader.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
            }

            System.out.println("Client: Downloaded file \"Received.txt\"");

            fos.close();
            disReader.close();
            dosWriter.close();

            clientEndpoint.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            System.out.println("Client: Connection is terminated.");
        }
    }
}