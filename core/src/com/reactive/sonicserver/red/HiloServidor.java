package com.reactive.sonicserver.red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import utiles.Global;

public class HiloServidor extends Thread {
	
	private DatagramSocket socket;
	private int puerto = 7517;
	private boolean fin = false;
	private DireccionRed[] clientes = new DireccionRed[2];
	private int cantClientes = 0;
	
	private ArrayList<DireccionRed> conexiones = new ArrayList<DireccionRed>();
	
	public HiloServidor() {
		try {
			socket = new DatagramSocket(puerto);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		do {
			byte[] data = new byte[1024];
			DatagramPacket dp = new DatagramPacket(data,data.length);
			try {
				socket.receive(dp);
				procesarMensaje(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}while(!fin);
	}

	private void procesarMensaje(DatagramPacket dp) {
		String msg = (new String(dp.getData())).trim();
		int nroCliente = -1;
		
		if(cantClientes>1) {
			for(int i = 0; i < clientes.length; i++) {
				if(dp.getPort()==clientes[i].getPuerto() && dp.getAddress().equals(clientes[i].getIp())) {
					nroCliente = i;
				}
			}
		}
		
		if(cantClientes<2) {
			if(msg.equals("Conexion")) {
				if (cantClientes<2) {
					clientes[cantClientes] = new DireccionRed(dp.getAddress(), dp.getPort(), msg);
					enviarMensaje("OK", clientes[cantClientes].getIp(), clientes[cantClientes++].getPuerto());
					if(cantClientes==2) {
						Global.empieza = true;
						for (int i = 0; i<clientes.length; i++) {
							enviarMensaje ("Empieza", clientes[i].getIp(), clientes[i].getPuerto());
						}
					}
				}
			}
		} else {
			if(nroCliente!=-1) {
				if(msg.equals("teclaArriba")) {
					if(nroCliente==0) {
						
					}
				} else if (msg.equals("teclaDer")) {
					
				} else if(msg.equals("teclaIzq")) {
					
				}
			}
		}
//		System.out.println(msg);
//		String[] msgCompuesto = msg.split("#");
//		
//		if(msgCompuesto.length>1) {
//			if(msgCompuesto[0].equals("Conexion")) {
//				
//				int indiceCliente = -1;
//				
//				if(conexiones.size()>0) {
//					indiceCliente = verificarExisteCliente(dp);
//				} 
//				
//				if(conexiones.size() < 2) {
//					if(indiceCliente==-1) {
//						conexiones.add(new DireccionRed(dp.getAddress(), dp.getPort(),msgCompuesto[1]));
//						enviarMensaje("Conexion exitosa",dp.getAddress(),dp.getPort());
//					}
//				} else if (conexiones.size() == 2) {
//					Global.empieza = true;
//					System.out.println("Empieza");
//					
//				} else {
//					enviarMensaje("La sala esta llena, no se puede conectar",dp.getAddress(),dp.getPort());
//				}
//			} else {
//			if(msg.equals("Desconectar")) {
//				int indiceCliente = verificarExisteCliente(dp);
//				if(indiceCliente>-1) {
//					conexiones.remove(indiceCliente);
//					enviarMensaje("Desconectado", dp.getAddress(),dp.getPort());
//				} else {
//					enviarMensaje("Acceso denegado", dp.getAddress(),dp.getPort());
//					}
//				}
//			}
//		}
	}

	public void enviarMensaje(String mensaje, InetAddress ipDestino, int puerto) {
		byte[] data = mensaje.getBytes();
		DatagramPacket dp = new DatagramPacket(data,data.length,ipDestino, puerto);
		try {
			socket.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
