package com.cloudtrading.collection.service;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import org.springframework.stereotype.Service;

@Service
public class SendCurrentDataService {

	private static final ByteArrayOutputStream baos=new ByteArrayOutputStream();
	private static final DataOutputStream dos=new DataOutputStream(baos);
	private static final InetSocketAddress isa=new InetSocketAddress("127.0.0.1",5678);
	
	
	public void sendData(int value) throws IOException{
		dos.writeInt(value);
		byte buf[]=baos.toByteArray();
		DatagramPacket dp=new DatagramPacket(buf,buf.length,isa);
		DatagramSocket ds =new DatagramSocket(9999);
		ds.send(dp);
		//ds.close();
		
	}
	
}
