package br.com.blackjack.dominio;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IBlackJack extends Remote {

	static final String ID = "BLACKJACK";

	IJogador adicionarJogador(String nome, IJogadorListener listener)
			throws RemoteException;

	IJogador getJogadorAtual() throws RemoteException;

	IJogador getCroupier() throws RemoteException;

	void iniciar() throws RemoteException;

	void pedirCarta() throws RemoteException;

	List<IJogador> getJogadores() throws RemoteException;

	public void passarTurno() throws RemoteException;
	
}
