package com.reactive.sonicserver.red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.badlogic.gdx.math.Vector2;

import Pantallas.PantallaJuego;
import utiles.Global;

public class HiloServidor extends Thread {
	
	private DatagramSocket socket;
	private int puerto = 7517;
	private boolean fin = false;
	private DireccionRed[] clientes = new DireccionRed[2];
	private int cantClientes = 0;
	private PantallaJuego app;
	
	public HiloServidor(PantallaJuego app) {
		this.app = app;

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
	        DatagramPacket dp = new DatagramPacket(data, data.length);
	        try {
	            socket.receive(dp);
	            procesarMensaje(dp);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    } while (!fin);
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
						app.jugador.b2cuerpo.applyLinearImpulse(new Vector2(0, 6f), app.jugador.b2cuerpo.getWorldCenter(), true);
					}else {
						app.jugadorAlt.b2cuerpo.applyLinearImpulse(new Vector2(0, 6f), app.jugadorAlt.b2cuerpo.getWorldCenter(), true);
					}
				} else if (msg.equals("teclaDer")) {
					if(nroCliente==0) {
						app.jugador.b2cuerpo.applyLinearImpulse(new Vector2(0.105f, 0), app.jugador.b2cuerpo.getWorldCenter(), true);
					}else {
						app.jugadorAlt.b2cuerpo.applyLinearImpulse(new Vector2(0.105f, 0), app.jugadorAlt.b2cuerpo.getWorldCenter(), true);
					}
				} else if(msg.equals("teclaIzq")) {
					if(nroCliente==0) {
						app.jugador.b2cuerpo.applyLinearImpulse(new Vector2(-0.105f, 0), app.jugador.b2cuerpo.getWorldCenter(), true);
					}else {
						app.jugadorAlt.b2cuerpo.applyLinearImpulse(new Vector2(-0.105f, 0), app.jugadorAlt.b2cuerpo.getWorldCenter(), true);
					}
				}
			}
		}
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
	
	public void dispose() {
	    if (socket != null && !socket.isClosed()) {
	        socket.close();
	    }
	}

	public void enviarMsgATodos(String msg) {
		for (int i=0; i<clientes.length; i++) {
		enviarMensaje(msg,clientes[i].getIp(),clientes[i].getPuerto());
		}
	}

}
