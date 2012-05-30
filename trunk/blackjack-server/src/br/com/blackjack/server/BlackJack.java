package br.com.blackjack.server;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Random;

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

	public IJogador adicionarJogador(String nome) throws RemoteException {
		Jogador j = new Jogador(nome);

		this.jogadores.add(j);

		if (jogadores.size() >= 3) {
			iniciar();
		}

		return j;
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
			listeners.peek().notificaTurno(jogadorAtual);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private Long gerarId() {
		Random r = new Random(System.currentTimeMillis());
		Long jogadorId = r.nextLong();
		return jogadorId;
	}

	@Override
	public IJogador getJogador(Long id) {
		return null;
	}

	@Override
	public IJogador getCroupier() {
		return this.croupier;
	}

	@Override
	public void adicionaJogadorListener(IJogadorListener listener)
			throws RemoteException {
		listeners.add(listener);
	}
}