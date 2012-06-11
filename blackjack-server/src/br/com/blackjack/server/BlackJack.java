package br.com.blackjack.server;

import java.rmi.RemoteException;
import java.util.LinkedList;

import br.com.blackjack.dominio.IBlackJack;
import br.com.blackjack.dominio.IJogador;
import br.com.blackjack.dominio.IJogadorListener;
import br.com.blackjack.server.dominio.Baralho;
import br.com.blackjack.server.dominio.Jogador;

/**
 * 
 * Essa classe implementa uma ver�o simplificada do jogo Blackjack(21). O
 * croupier recebe 2 cartas. 1 virada para baixo e outra virada para cima. Em
 * seguida, cada jogador recebe 2 cartas viradas para cima. Cada jogador pode
 * solicitar quantas cartas quiser ao croupier de modo que n�o ultrapasse 21
 * pontos. Quando todos os jogadores comprarem suas cartas, o croupier pega
 * novas carta para si. O vencedor ser� aquele que tiver mais pontos que o
 * Croupier ou se algu�m conseguir alcan�ar 21 pontos.
 * 
 */
public class BlackJack implements IBlackJack {

	// jogadores conectados ao jogo
	LinkedList<IJogador> jogadores;

	// turnos do jogo
	LinkedList<IJogador> jogadoresTurno;

	// croupier do jogo
	IJogador croupier;

	// Listeners do jogadores, para que os mesmos recebam atualizacoes do jogo
	LinkedList<IJogadorListener> listeners = new LinkedList<IJogadorListener>();

	// o jogo do turno atual
	private IJogador jogadorAtual;

	// o baralho que est� sendo usado
	private Baralho baralho;

	public BlackJack() {
		jogadores = new LinkedList<IJogador>();
	}

	/**
	 * @see IBlackJack#adicionarJogador(String, IJogadorListener)
	 */
	public IJogador adicionarJogador(String nome, IJogadorListener listener)
			throws RemoteException {
		Jogador j = new Jogador(nome + "_" + (this.jogadores.size() + 1));

		this.jogadores.add(j);
		this.adicionaJogadorListener(j, listener);

		notificaEntradaJogador(j);

		// limitei o jogo para iniciar com 3 jogadores
		if (jogadores.size() >= 3) {
			iniciar();
		}

		return j;
	}

	/**
	 * Notifica que um novo jogador se conectou ao jogo
	 * 
	 * @param jogador
	 */
	private void notificaEntradaJogador(IJogador jogador) {
		for (IJogadorListener listener : this.listeners) {
			try {
				listener.notificarEntradaJogador(jogador);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Inicializa o jogo
	 */
	public void iniciar() {
		croupier = new Jogador("croupier");

		baralho = new Baralho();
		baralho.embaralhar();

		this.darCartaAoCroupier();

		for (IJogador j : jogadores) {
			j.limparCartas();
		}

		this.jogadoresTurno = new LinkedList<IJogador>(this.jogadores);

		// inicialmente d� 2 cartas
		this.darCartaParaCadaJogador();
		this.darCartaParaCadaJogador();

		// pega o primeiro jogador para jogar
		jogadorAtual = this.jogadoresTurno.poll();

		try {
			this.notificarCartaRetirada();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see IBlackJack#pedirCarta()
	 */
	public void pedirCarta() {
		pedirCarta(jogadorAtual);

		try {
			this.notificarCartaRetirada();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see IBlackJack#passarTurno()
	 */
	public void passarTurno() {
		jogadorAtual = this.jogadoresTurno.poll();
		int index = 0;
		for (IJogadorListener listener : listeners) {
			try {
				if (this.jogadorAtual != null) {
					listener.notificaTurno(this.jogadores.get(index));
				} else {
					listener.notificaVezCroupier();
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			index++;
		}

		// ou seja, se nao tem mais jogadores.. é a vez do croupier
		if (jogadorAtual == null) {
			this.pegarCartaCroupier();
		}
	}

	/**
	 * Solicita uma carta para um determinado jogador
	 * 
	 * @param jogador
	 */
	private void pedirCarta(IJogador jogador) {
		jogador.adicionarCarta(this.baralho.proximaCarta());

		int index = 0;
		for (IJogadorListener listener : this.listeners) {
			try {
				if (jogador.getPontuacaoCartas() > 21) {
					listener.notificaEstouroPontuacao(this.jogadores.get(index));
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			index++;
		}

		if (jogador.getPontuacaoCartas() > 21) {
			this.passarTurno();
		}
	}

	/**
	 * Notifica para todos os listeners que uma carta foi retirada por algu�m
	 * 
	 * @throws RemoteException
	 */
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

	/**
	 * Tira uma nova carta para o Croupier para inicializar o jogo
	 */
	private void darCartaAoCroupier() {
		croupier.adicionarCarta(baralho.proximaCartaViradaParaBaixo());
		croupier.adicionarCarta(baralho.proximaCarta());
	}

	/**
	 * Pega uma nova carta para o Croupier quando for sua vez
	 */
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

	/**
	 * Tira um carta para cada jogador conectado ao jogo
	 */
	private void darCartaParaCadaJogador() {
		for (IJogador j : jogadores) {
			pedirCarta(j);
		}
	}

	@Override
	public IJogador getCroupier() {
		return this.croupier;
	}

	/**
	 * Adiciona um novo listener de eventos do jogador
	 * 
	 * @param j
	 * @param listener
	 * @throws RemoteException
	 */
	private void adicionaJogadorListener(IJogador j, IJogadorListener listener)
			throws RemoteException {
		listeners.add(listener);
	}

	@Override
	public IJogador getJogadorAtual() throws RemoteException {
		return this.jogadorAtual;
	}
}