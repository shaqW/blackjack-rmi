package br.com.blackjack.test;

import java.rmi.RemoteException;
import java.util.LinkedList;

import org.junit.Test;

import br.com.blackjack.client.TurnoJogadorListener;
import br.com.blackjack.server.BlackJack;
import br.com.blackjack.server.dominio.Jogador;

public class BlackjackUnitTest {
	BlackJack jogo = new BlackJack();

	@Test
	public void deveriaInicarJogo() {
		Thread t1 = new Thread(new EntrarNoJogo("leandro"), "leandro");
		Thread t2 = new Thread(new EntrarNoJogo("leandro"), "leandro");
		Thread t3 = new Thread(new EntrarNoJogo("leandro"), "leandro");
	}

	class EntrarNoJogo implements Runnable {

		private String nome;

		public EntrarNoJogo(String nome) {
			this.nome = nome;
		}

		@Override
		public void run() {
			try {
				jogo.adicionarJogador(nome, new TurnoJogadorListener(jogo));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
