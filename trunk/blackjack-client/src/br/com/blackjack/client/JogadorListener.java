package br.com.blackjack.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.List;

import br.com.blackjack.dominio.IBlackJack;
import br.com.blackjack.dominio.ICarta;
import br.com.blackjack.dominio.IJogador;
import br.com.blackjack.dominio.IJogadorListener;

/**
 * Implementa um listener para os eventos do servidor. Nessa implementação, toda
 * a interação com o usuário é feita pelo terminal
 * 
 * @author fernando
 * 
 */
public class JogadorListener extends UnicastRemoteObject implements
		IJogadorListener {

	IBlackJack jogo;

	LeitorOpcoes leitor;

	public JogadorListener(IBlackJack jogo) throws RemoteException {
		super();
		this.jogo = jogo;

		try {
			leitor = new LeitorOpcoes(jogo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void notificaTurno(IJogador jogador) {
		mostrarCartas(jogador);

		if (seEhSuaVez(jogador)) {
			System.out.println(jogador.getNome() + " é sua vez de jogar!");
			verificaOpcoesDeJogo(jogador);
		}
	}

	private void mostrarCartas(IJogador jogador) {
		System.out.println("####################  MESA  #########################");
		mostarCartasCroupier();
		System.out.println("---------------------------------\n");

		mostrarCartasJogadores();

		System.out.println("\n###################################################");
	}

	private void mostrarCartasJogadores() {
		try {
			List<IJogador> jogadores = jogo.getJogadores();
			Iterator<IJogador> it = jogadores.iterator();
			while (it.hasNext()) {
				IJogador jog = it.next();
				String jogadorStr = "";
				jogadorStr += "### " + jog.getNome() + " => "
						+ montarStrCartas(jog) + " : "
						+ jog.getPontuacaoCartas() + " pontos ###";
				System.out.println(jogadorStr);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void mostarCartasCroupier() {
		System.out.println("#### Croupier ####");
		IJogador croupier = null;
		String cartasStr = "";
		try {
			croupier = jogo.getCroupier();

			cartasStr = montarStrCartas(croupier);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		System.out.println("###### " + cartasStr + " #######");
	}

	private String montarStrCartas(IJogador jogador) {
		List<ICarta> cartas = jogador.getCartas();
		Iterator<ICarta> it = cartas.iterator();
		String cartasStr = "";
		while (it.hasNext()) {
			ICarta c = it.next();
			cartasStr += c.isViradaParaBaixo() ? "??" : c.getPontuacao();
			if (it.hasNext()) {
				cartasStr += ", ";
			}
		}

		return cartasStr;
	}

	@Override
	public void notificaCartaRetirada(IJogador jogador) throws RemoteException {
		if (jogo.getJogadorAtual().getNome().equalsIgnoreCase("croupier")) {
			System.out.println("Todos os jogadores já pegaram suas cartas");
		}

		mostrarCartas(jogador);

		if (seEhSuaVez(jogador)) {
			verificaOpcoesDeJogo(jogador);
		}
	}

	@Override
	public void notificaInicioJogo(IJogador jogador) throws RemoteException {
		mostrarCartas(jogador);
	}

	private void verificaOpcoesDeJogo(IJogador jogador) {
		if (seEhSuaVez(jogador)) {
			Thread t = new Thread(leitor);
			t.start();
		}
	}

	@Override
	public void notificarEntradaJogador(IJogador jogador)
			throws RemoteException {
		System.out.println(jogador.getNome() + " entrou no jogo");
	}

	private boolean seEhSuaVez(IJogador jogador) {
		try {
			IJogador jogadorAtual = jogo.getJogadorAtual();
			if (jogadorAtual != null && jogador != null) {
				return jogador.getNome().equalsIgnoreCase(
						jogadorAtual.getNome());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public void notificaEstouroPontuacao(IJogador jogador)
			throws RemoteException {
		if (seEhSuaVez(jogador)) {
			System.out.println("Que pena você estourou a pontuação! Você fez "
					+ jogador.getPontuacaoCartas() + " pontos");
		} else {
			System.out.println("O jogador " + jogo.getJogadorAtual().getNome()
					+ " estourou a pontuação! Ele fez"
					+ jogo.getJogadorAtual().getPontuacaoCartas() + " pontos");
		}
	}

	@Override
	public void notificaVencedorPorBlackJack(IJogador jogador)
			throws RemoteException {
		mostrarCartas(jogador);
		if (seEhSuaVez(jogador)) {
			System.out.println("Você ganhou o jogo! Parabéns!");
		} else {
			System.out.println("O jogador " + jogo.getJogadorAtual().getNome()
					+ " venceu o jogo por fazer um BlackJack!");
		}
	}

	@Override
	public void notificarFimJogo(IJogador jogador) throws RemoteException {
		mostrarCartas(jogador);

		List<IJogador> jogadores = jogo.getJogadores();
		for (IJogador j : jogadores) {
			if (j.getPontuacaoCartas() > jogo.getCroupier()
					.getPontuacaoCartas()) {
				System.out.println("Jogador " + j.getNome()
						+ " venceu o Croupier! Parabéns!");
			} else {
				System.out
						.println("Jogador "
								+ j.getNome()
								+ " perdeu para o Croupier! Mais sorte da próxima vez!");
			}
		}

		System.out.println("####################################");
		System.out.println("####### Vamos recomeçar o jogo! #######");
		System.out.println("####################################");
	}
}
