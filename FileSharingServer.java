package fileSharing;

import java.io.*;
import java.net.*;

public class FileSharingServer {
    public static void main(String[] args) throws IOException {
       
    	ServerSocket soc = new ServerSocket(12345);
    	System.out.println("Server started...");
    	Socket sock = soc.accept();
    	System.out.println("Client connected...");
    	
    	DataInputStream dis = new DataInputStream(sock.getInputStream());
    	String fileName = dis.readUTF();
    	long fileSize = dis.readLong();
    	
    	FileOutputStream fos = new FileOutputStream(fileName);
    	byte[] buffer = new byte[4096];
    	int read;
    	long remaining = fileSize;
    	
    	while((read = dis.read(buffer,0,Math.min(buffer.length, (int) remaining))) > 0) {
    		fos.write(buffer,0,read);
    		remaining -= read;
    	}
    	
    	fos.close();
    	System.out.println("File received: " + fileName);
    	
    }
}

