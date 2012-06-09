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
		Jogador j = new Jogador(nome + "_" + (this.jogadores.size() + 1));

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

		for (IJogador j : jogadores) {
			j.limparCartas();
		}

		this.jogadoresTurno = new LinkedList<IJogador>(this.jogadores);

		// inicialmente d‡ 2 cartas
		this.darCartaParaCadaJogador();
		this.darCartaParaCadaJogador();

		// this.proximoTurno();
		jogadorAtual = this.jogadoresTurno.poll();

		try {
			this.notificarCartaRetirada();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pedirCarta() {
		pedirCarta(jogadorAtual);

		try {
			this.notificarCartaRetirada();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void passarTurno() {
		this.proximoTurno();
	}

	private void pedirCarta(IJogador jogador) {
		jogador.adicionarCarta(this.baralho.proximaCarta());

		int index = 0;
		for (IJogadorListener listener : this.listeners) {
			try {
				if (jogador.getPontuacaoCartas() > 21) {
					listener.notificaEstouroPontuacao(this.jogadores.get(index));
				} else {
					if (jogador.getPontuacaoCartas() == 21) {
						listener.notificaVencedorPorBlackJack(this.jogadores
								.get(index));
						listener.notificarFimJogo(this.jogadores.get(index));
					}
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			index++;
		}

		if (jogador.getPontuacaoCartas() > 21) {
			this.proximoTurno();
		}
	}

	private void notificarCartaRetirada() throws RemoteException {
		int index = 0;
		for (IJogadorListener listener : listeners) {
			if (jogadorAtual != null) {
				listener.notificaCartaRetirada(this.jogadores.get(index));
			} else {
				listener.notificaInicioJogo(this.jogadores.get(index));
			}

			index++;
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
			croupier.adicionarCarta(this.baralho.proximaCarta());
		} while (croupier.getPontuacaoCartas() <= 17);

		int i = 0;
		for (IJogadorListener listener : listeners) {
			try {
				listener.notificarFimJogo(jogadores.get(i));
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			i++;
		}

	}

	public LinkedList<IJogador> getJogadores() {
		return jogadores;
	}

	public void setJogadores(LinkedList<IJogador> jogadores) {
		this.jogadores = jogadores;
	}

	private void darCartaParaCadaJogador() {
		for (IJogador j : jogadores) {
			pedirCarta(j);
		}
	}

	public void proximoTurno() {
		jogadorAtual = this.jogadoresTurno.poll();
		int index = 0;
		for (IJogadorListener listener : listeners) {
			try {
				if (jogadorAtual != null) {
					listener.notificaTurno(this.jogadores.get(index));
				} else {
					this.jogadorAtual = croupier;
					this.pegarCartaCroupier();
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			index++;
		}
	}

	@Override
	public IJogador getCroupier() {
		return this.croupier;
	}

	private void adicionaJogadorListener(IJogador j, IJogadorListener listener)
			throws RemoteException {
		listeners.add(listener);
	}

	@Override
	public IJogador getJogadorAtual() throws RemoteException {
		return this.jogadorAtual;
	}
}