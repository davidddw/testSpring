package com.yunshan.cloudstack.utils.security;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.log4j.Logger;

public class SecureSSLSocketFactory extends SSLSocketFactory {

	public static final Logger s_logger = Logger
			.getLogger(SecureSSLSocketFactory.class);
	private SSLContext _sslContext;

	public SecureSSLSocketFactory() throws NoSuchAlgorithmException {
		_sslContext = SSLUtils.getSSLContext();
	}

	public SecureSSLSocketFactory(SSLContext sslContext)
			throws NoSuchAlgorithmException {
		if (sslContext != null) {
			_sslContext = sslContext;
		} else {
			_sslContext = SSLUtils.getSSLContext();
		}
	}

	public SecureSSLSocketFactory(KeyManager[] km, TrustManager[] tm,
			SecureRandom random) throws NoSuchAlgorithmException,
			KeyManagementException, IOException {
		_sslContext = SSLUtils.getSSLContext();
		_sslContext.init(km, tm, random);
	}

	@Override
	public String[] getDefaultCipherSuites() {
		return getSupportedCipherSuites();
	}

	@Override
	public String[] getSupportedCipherSuites() {
		String[] ciphers = null;
		try {
			ciphers = SSLUtils.getSupportedCiphers();
		} catch (NoSuchAlgorithmException e) {
			s_logger.error("SecureSSLSocketFactory::getDefaultCipherSuites found no cipher suites");
		}
		return ciphers;
	}

	@Override
	public Socket createSocket(Socket s, String host, int port,
			boolean autoClose) throws IOException {
		SSLSocketFactory factory = _sslContext.getSocketFactory();
		Socket socket = factory.createSocket(s, host, port, autoClose);
		if (socket instanceof SSLSocket) {
			((SSLSocket) socket).setEnabledProtocols(SSLUtils
					.getSupportedProtocols(((SSLSocket) socket)
							.getEnabledProtocols()));
		}
		return socket;
	}

	@Override
	public Socket createSocket(String host, int port) throws IOException,
			UnknownHostException {
		SSLSocketFactory factory = _sslContext.getSocketFactory();
		Socket socket = factory.createSocket(host, port);
		if (socket instanceof SSLSocket) {
			((SSLSocket) socket).setEnabledProtocols(SSLUtils
					.getSupportedProtocols(((SSLSocket) socket)
							.getEnabledProtocols()));
		}
		return socket;
	}

	@Override
	public Socket createSocket(String host, int port, InetAddress inetAddress,
			int localPort) throws IOException, UnknownHostException {
		SSLSocketFactory factory = _sslContext.getSocketFactory();
		Socket socket = factory
				.createSocket(host, port, inetAddress, localPort);
		if (socket instanceof SSLSocket) {
			((SSLSocket) socket).setEnabledProtocols(SSLUtils
					.getSupportedProtocols(((SSLSocket) socket)
							.getEnabledProtocols()));
		}
		return socket;
	}

	@Override
	public Socket createSocket(InetAddress inetAddress, int localPort)
			throws IOException {
		SSLSocketFactory factory = _sslContext.getSocketFactory();
		Socket socket = factory.createSocket(inetAddress, localPort);
		if (socket instanceof SSLSocket) {
			((SSLSocket) socket).setEnabledProtocols(SSLUtils
					.getSupportedProtocols(((SSLSocket) socket)
							.getEnabledProtocols()));
		}
		return socket;
	}

	@Override
	public Socket createSocket(InetAddress address, int port,
			InetAddress localAddress, int localPort) throws IOException {
		SSLSocketFactory factory = this._sslContext.getSocketFactory();
		Socket socket = factory.createSocket(address, port, localAddress,
				localPort);
		if (socket instanceof SSLSocket) {
			((SSLSocket) socket).setEnabledProtocols(SSLUtils
					.getSupportedProtocols(((SSLSocket) socket)
							.getEnabledProtocols()));
		}
		return socket;
	}
}