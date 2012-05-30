package br.com.blackjack.dominio;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBlackJack extends Remote {

	static final String ID = "BLACKJACK";

	IJogador adicionarJogador(String nome) throws RemoteException;

	IJogador getJogador(Long id) throws RemoteException;

	IJogador getCroupier() throws RemoteException;

	void iniciar() throws RemoteException;
	
	void adicionaJogadorListener(IJogadorListener listener) throws RemoteException;
}
