package br.com.blackjack.dominio;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IJogadorListener extends Remote {

	void notificaTurno(IJogador jogador) throws RemoteException;

	void notificaCartaRetirada(IJogador jogador) throws RemoteException;

	void notificarEntradaJogador(IJogador jogador) throws RemoteException;

	void notificaInicioJogo(IJogador jogador) throws RemoteException;

	void notificaEstouroPontuacao(IJogador jogador) throws RemoteException;

	void notificaVencedorPorBlackJack(IJogador jogador) throws RemoteException;

	void notificarFimJogo(IJogador jogador) throws RemoteException;
}
