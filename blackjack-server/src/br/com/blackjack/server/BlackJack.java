package br.com.blackjack.server;

import java.rmi.RemoteException;
import java.util.LinkedList;

import br.com.blackjack.dominio.IBlackJack;
import br.com.blackjack.dominio.IJogador;
import br.com.blackjack.dominio.IJogadorListener;
import br.com.blackjack.server.dominio.Baralho;
import br.com.blackjack.server.dominio.Jogador;

public class BlackJack implements IBlackJack {

	LinkedList<IJogador> jogadores;

	LinkedList<IJogador> jogadoresTurno;

	IJogador croupier;

	LinkedList<IJogadorListener> listeners = new LinkedList<IJogadorListener>();

	private IJogador jogadorAtual;

	private Baralho baralho;

	public BlackJack() {
		jogadores = new LinkedList<IJogador>();
	}

	public IJogador adicionarJogador(String nome, IJogadorListener listener)
			throws RemoteException {
		Jogador j = new Jogador(nome);

		this.jogadores.add(j);
		this.adicionaJogadorListener(j, listener);

		notificaEntradaJogador(j);

		if (jogadores.size() >= 2) {
			iniciar();
		}

		return j;
	}

	private void notificaEntradaJogador(IJogador jogador) {
		for (IJogadorListener listener : this.listeners) {
			try {
				listener.notificarEntradaJogador(jogador);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void iniciar() {
		croupier = new Jogador("croupier");

		baralho = new Baralho();
		baralho.embaralhar();

		this.darCartaAoCroupier();

		this.jogadoresTurno = new LinkedList<IJogador>(this.jogadores);

		// inicialmente d‡ 2 cartas
		this.darCartaParaCadaJogador();
		this.darCartaParaCadaJogador();

		this.proximoTurno();
	}

	public void pedirCarta() {
		pedirCarta(jogadorAtual);
	}

	public void passarTurno() {
		this.proximoTurno();
	}

	private void pedirCarta(IJogador jogador) {
		jogador.adicionarCarta(this.baralho.proximaCarta());

		for (IJogadorListener listener : this.listeners) {
			try {
				if (jogador.getPontuacaoCartas() > 21) {
					listener.notificaEstouroPontuacao(jogador);
				} else {
					if (jogador.getPontuacaoCartas() == 21) {
						listener.notificaVencedorPorBlackJack(jogador);
					} else {
						notificarCartaRetirada(jogador, listener);
					}
				}

			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (jogador.getPontuacaoCartas() > 21) {
			this.proximoTurno();
		}
	}

	private void notificarCartaRetirada(IJogador jogador,
			IJogadorListener listener) throws RemoteException {
		if (jogadorAtual != null) {
			listener.notificaCartaRetirada(jogador);
		} else {
			listener.notificaInicioJogo(jogador);
		}
	}

	private void darCartaAoCroupier() {
		croupier.adicionarCarta(baralho.proximaCartaViradaParaBaixo());
		croupier.adicionarCarta(baralho.proximaCarta());
	}

	private void pegarCartaCroupier() {
		// desvirar carta
		croupier.getCartas().get(0).setViradaParaBaixo(Boolean.FALSE);

		do {
			pedirCarta(croupier);
		} while (croupier.getPontuacaoCartas() < 17);
	}

	private void darCartaParaCadaJogador() {
		for (IJogador j : jogadores) {
			pedirCarta(j);
		}
	}

	public void proximoTurno() {
		try {
			jogadorAtual = this.jogadoresTurno.poll();
			if (jogadorAtual != null) {
				listeners.peek().notificaTurno(jogadorAtual);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IJogador getJogador(Long id) {
		return null;
	}

	@Override
	public IJogador getCroupier() {
		return this.croupier;
	}

	private void adicionaJogadorListener(IJogador j, IJogadorListener listener)
			throws RemoteException {
		listener.setJogadorAtual(j);
		listeners.add(listener);
	}
}