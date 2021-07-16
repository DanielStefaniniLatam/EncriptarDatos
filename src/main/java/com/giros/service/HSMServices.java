package com.giros.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

public class HSMServices {

	private String host;
	private int port;
	private String trustStoreLocation;
	private String keyStoreLocation;
	private String keyStorePassword;
	private Socket socket;
	private SSLSocketFactory socketSSLFactory;
	private SSLSocket socketSSL;
	private boolean secureConnection;
	private DataOutputStream out;
	private DataInputStream in;

	/**
	 * Contructor para establecer conexion sin certificados
	 * 
	 * @param host
	 *            nombre o direccion IP del host del HSM
	 * @param port
	 *            puerto de escucha del HSM
	 */
	public HSMServices(String host, int port) {
		this.host = host;
		this.port = port;
		this.secureConnection = Boolean.FALSE;
	}

	/**
	 * Contructor para establecer conexion sin certificados
	 * 
	 * @param host
	 *            nombre o direccion IP del host del HSM
	 * @param port
	 *            puerto de escucha del HSM
	 * @param trustStoreLocation
	 *            ruta del sistema de archivos en la cual se encientra el
	 *            almacen de llaves
	 * @param keyStoreLocation
	 *            ruta del sistema de archivos en la cual se encientra el
	 *            almacen de llaves
	 * @param keyStorePassword
	 *            contraseña para acceder al almacen de llaves
	 */
	public HSMServices(String host, int port, String trustStoreLocation,
			String keyStoreLocation, String keyStorePassword) {
		this.host = host;
		this.port = port;
		this.trustStoreLocation = trustStoreLocation;
		this.keyStoreLocation = keyStoreLocation;
		this.keyStorePassword = keyStorePassword;
		this.secureConnection = Boolean.TRUE;
	}

	/**
	 * Metodo encargado de establecer la conexion con el HSM
	 * 
	 * @throws HSMSecurityProviderException
	 */
	public void OpenConnection() throws HSMSecurityProviderException {
		if (secureConnection) {
			try {
				this.socketSSLFactory = (SSLSocketFactory) SSLSocketFactory
						.getDefault();
				this.socketSSL = (SSLSocket) this.socketSSLFactory
						.createSocket(this.host, this.port);

				this.socketSSL.startHandshake();
				this.in = new DataInputStream(this.socketSSL.getInputStream());
				this.out = new DataOutputStream(
						this.socketSSL.getOutputStream());
			} catch (UnknownHostException e) {
				e.printStackTrace();
				HSMSecurityProviderException ex = new HSMSecurityProviderException(
						e);
				ex.setError(ErrorsEnum.HSM007);
				throw ex;
			} catch (Exception e) {
				HSMSecurityProviderException ex = new HSMSecurityProviderException(
						e);
				ex.setError(ErrorsEnum.HSM008);
				throw ex;
			}

		} else {

			try {
				this.socket = new Socket(this.host, this.port);
				this.in = new DataInputStream(socket.getInputStream());
				this.out = new DataOutputStream(socket.getOutputStream());
			} catch (UnknownHostException e) {
				e.printStackTrace();
				HSMSecurityProviderException ex = new HSMSecurityProviderException(
						e);
				ex.setError(ErrorsEnum.HSM007);
				throw ex;
			} catch (Exception e) {
				HSMSecurityProviderException ex = new HSMSecurityProviderException(
						e);
				ex.setError(ErrorsEnum.HSM008);
				throw ex;
			}

		}
	}

	/**
	 * Metodo encargado de cerrar la conexion al HSM
	 */
	public void closeConnection() {
		try {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
			if (socket != null) {
				socket.close();
			}
			if (socketSSL != null) {
				socketSSL.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo encargado de enviar el comando al HSM
	 * 
	 * @param commandcomando
	 *            a ejecutar
	 * @return respuesta del HSM
	 * @throws HSMSecurityProviderException
	 */
	public String executeCommand(String command)
			throws HSMSecurityProviderException {
		try {
			this.out.write(Hex.decodeHex(command.toCharArray()));
			byte[] byteArray = new byte[4096];
			in.read(byteArray);
			String response = Hex.encodeHexString(byteArray);
			Integer longitudRespuesta = Integer.parseInt(
					response.substring(8, 12), 16) * 2;
			response = response.substring(0, longitudRespuesta + 12);
			return response;
		} catch (Exception e) {
			HSMSecurityProviderException ex = new HSMSecurityProviderException(
					e);
			ex.setError(ErrorsEnum.HSM009);
			throw ex;
		}
	}
}
