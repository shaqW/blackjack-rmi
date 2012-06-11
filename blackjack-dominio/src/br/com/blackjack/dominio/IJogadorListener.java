package br.com.blackjack.dominio;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Representa um listener para os eventos do jogo
 * 
 * @author fernando
 * 
 */
public interface IJogadorListener extends Remote {

	/**
	 * notifica que um novo turno come�ou
	 * 
	 * @param jogador
	 * @throws RemoteException
	 */
	void notificaTurno(IJogador jogador) throws RemoteException;

	/**
	 * notifica que uma nova carta foi retirada por algu�m
	 * 
	 * @param jogador
	 * @throws RemoteException
	 */
	void notificaCartaRetirada(IJogador jogador) throws RemoteException;

	/**
	 * notifica que um novo jogador se conectou ao jogo
	 * 
	 * @param jogador
	 * @throws RemoteException
	 */
	void notificarEntradaJogador(IJogador jogador) throws RemoteException;

	/**
	 * notifica que o jogo come�ou
	 * 
	 * @param jogador
	 * @throws RemoteException
	 */
	void notificaInicioJogo(IJogador jogador) throws RemoteException;

	/**
	 * notifica que algum jogador estourou a pontuacao, ou seja, fez mais que 21
	 * pontos
	 * 
	 * @param jogador
	 * @throws RemoteException
	 */
	void notificaEstouroPontuacao(IJogador jogador) throws RemoteException;

	/**
	 * notifica que o jogo chegou ao fim
	 * 
	 * @param jogador
	 * @throws RemoteException
	 */
	void notificarFimJogo(IJogador jogador) throws RemoteException;

	/**
	 * Notifica que é a vez do Croupier jogar
	 * 
	 * @throws RemoteException
	 */
	void notificaVezCroupier() throws RemoteException;

}
