package br.com.blackjack.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import br.com.blackjack.dominio.ICarta;
import br.com.blackjack.dominio.IJogador;
import br.com.blackjack.dominio.IJogadorListener;

public class TurnoJogadorListener extends UnicastRemoteObject implements
		IJogadorListener {

	public TurnoJogadorListener() throws RemoteException {
		super();
	}

	@Override
	public void notificaTurno(IJogador jogador) {
		List<ICarta> cartas = jogador.getCartas();
		System.out.println("suas cartas:");
		for (ICarta c : cartas) {
			System.out.println(c.getPontuacao());
		}

		System.out.println("sua pontuacao atual: "
				+ jogador.getPontuacaoCartas());

		System.out.println(jogador.getNome() + " Ž sua vez de jogar!");
	}

	@Override
	public void notificaCartaRetirada(IJogador jogador) throws RemoteException {
		System.out.println("Sua nova carta Ž: "
				+ jogador.getCartas().get(jogador.getCartas().size() - 1)
						.getPontuacao());
	}
}
