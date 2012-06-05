package br.com.blackjack.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.blackjack.dominio.IBlackJack;
import br.com.blackjack.dominio.ICarta;
import br.com.blackjack.dominio.IJogador;
import br.com.blackjack.dominio.IJogadorListener;

public class TurnoJogadorListener extends UnicastRemoteObject implements
		IJogadorListener {

	IBlackJack jogo;

	IJogador jogadorAtual;

	Scanner scanner = new Scanner(System.in);

	public TurnoJogadorListener(IBlackJack jogo) throws RemoteException {
		super();
		this.jogo = jogo;
	}

	public void setJogadorAtual(IJogador jogadorAtual) {
		this.jogadorAtual = jogadorAtual;
	}

	@Override
	public void notificaTurno(IJogador jogador) {
		mostrarCartas(jogador);

		if (seEhSuaVez(jogador)) {
			System.out.println(jogador.getNome() + " � sua vez de jogar!");
			verificaOpcoesDeJogo();
		}
	}

	private void mostrarCartas(IJogador jogador) {
		List<ICarta> cartas = jogador.getCartas();
		if (seEhSuaVez(jogador)) {
			System.out.println("Voc� tem agora as seguintes cartas:");
		} else {
			System.out.println("O jogador " + jogador.getNome()
					+ " tem agora as cartas:");
		}

		for (ICarta c : cartas) {
			System.out.println(c.getPontuacao());
		}

		if (seEhSuaVez(jogador)) {
			System.out.println("Sua pontuacao atual � "
					+ jogador.getPontuacaoCartas());
		}
	}

	@Override
	public void notificaCartaRetirada(IJogador jogador) throws RemoteException {
		mostrarCartas(jogador);

		if (seEhSuaVez(jogador)) {
			verificaOpcoesDeJogo();
		}
	}

	@Override
	public void notificaInicioJogo(IJogador jogador) throws RemoteException {
		mostrarCartas(jogador);
	}

	private void verificaOpcoesDeJogo() {
		mostrarOpcoes();

		Pattern p = Pattern.compile("[PpDdTt]");
		while (scanner.hasNext()) {
			String opcao = scanner.next();
			Matcher m = p.matcher(opcao);
			if (!m.matches()) {
				System.out.println("Opção inválida!");
				mostrarOpcoes();
			} else {
				if (opcao.equals("p") || opcao.equals("P")) {
					try {
						jogo.pedirCarta();
						break;
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void mostrarOpcoes() {
		System.out.println("Escolha uma das opções abaixo:");
		System.out
				.println("[P]Pedir Mais 1 Carta  [D]Desafiar o Croupier [T]Terminar a Jogada");
	}

	@Override
	public void notificarEntradaJogador(IJogador jogador)
			throws RemoteException {
		if (seEhSuaVez(jogador)) {
			System.out.println(jogador.getNome() + " entrou no jogo");
		}
	}

	private boolean seEhSuaVez(IJogador jogador) {
		return jogador.getNome().equalsIgnoreCase(jogadorAtual.getNome());
	}

	@Override
	public void notificaEstouroPontuacao(IJogador jogador)
			throws RemoteException {
		mostrarCartas(jogador);
		if (seEhSuaVez(jogador)) {
			System.out.println("Que pena voc� estourou a pontua��o! Voc� fez "
					+ jogador.getPontuacaoCartas() + " pontos");
		} else {
			System.out.println("O jogador " + jogador.getNome()
					+ "estourou a pontua��o! Ele fez"
					+ jogador.getPontuacaoCartas() + " pontos");
		}
	}

	@Override
	public void notificaVencedorPorBlackJack(IJogador jogador)
			throws RemoteException {
		mostrarCartas(jogador);
		if (seEhSuaVez(jogador)) {
			System.out.println("Voc� ganhou o jogo! Parab�ns!");
		} else {
			System.out.println("O jogador " + jogador.getNome()
					+ "venceu o jogo por fazer um BlackJack!");
		}
	}
}
