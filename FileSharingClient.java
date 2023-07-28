package fileSharing;

import java.io.*;
import java.net.*;
import java.util.*;
public class FileSharingClient {
    public static void main(String[] args) throws UnknownHostException, IOException {
    	Scanner sc = new Scanner(System.in);
    	Socket sock = new Socket("localhost" , 12345);
    	System.out.println("Connected to server");
    	
    	System.out.println("Enter file path : ");
    	String filePath = sc.next();
    	File file = new File(filePath);
    	long fileSize = file.length();
    	
    	DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
    	dos.writeUTF(file.getName());
    	dos.writeLong(fileSize);
    	
    	FileInputStream fis = new FileInputStream(file);
    	byte[] buffer = new byte[4096];
    	int read;
    	while((read = fis.read(buffer)) != -1) {
    		dos.write(buffer,0,read);
    	}
    	
    	fis.close();
    	dos.flush();
    	System.out.println("File Sent: " + file.getName());
    	sock.close();
    }
}