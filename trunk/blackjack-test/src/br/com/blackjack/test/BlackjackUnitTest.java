package br.com.blackjack.test;

import java.rmi.RemoteException;
import java.util.LinkedList;

import org.junit.Test;

import br.com.blackjack.client.TurnoJogadorListener;
import br.com.blackjack.server.BlackJack;
import br.com.blackjack.server.dominio.Jogador;

public class BlackjackUnitTest {

	@Test
	public void deveriaInicarJogo() {
		BlackJack jogo = new BlackJack();

		try {
			jogo.adicionarJogador("Leandro");
			jogo.adicionaJogadorListener(new TurnoJogadorListener());

			jogo.adicionarJogador("Aline");
			jogo.adicionaJogadorListener(new TurnoJogadorListener());

			jogo.adicionarJogador("Gustavo");
			jogo.adicionaJogadorListener(new TurnoJogadorListener());
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testPeek() {
		LinkedList<Jogador> js = new LinkedList<Jogador>();
		js.add(new Jogador("Leandro"));
		js.add(new Jogador("Aline"));
		js.add(new Jogador("Gustavo"));
	}

}
