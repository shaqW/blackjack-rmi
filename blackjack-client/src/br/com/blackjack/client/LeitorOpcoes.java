package br.com.blackjack.client;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.blackjack.dominio.IBlackJack;

/**
 * Representa um leitor das opcoes que o jogador pode escolher durante o jogo O
 * leitor implementa Runnable porque ele � executado por uma Thread para que n�o
 * bloqueie os demais jogadores ao realizar a leitura dos dados do teclado
 * 
 * @author fernando
 * 
 */
public class LeitorOpcoes implements Runnable {

	IBlackJack jogo;

	static Scanner scanner = new Scanner(System.in);

	public LeitorOpcoes(IBlackJack jogo) {
		this.jogo = jogo;
	}

	/**
	 * mostra as opcoes que os usuarios podem escolher
	 */
	public void mostrarOpcoes() {
		System.out.println("Escolha uma das opções abaixo:");
		System.out.println("[P]Pedir Mais 1 Carta  ou [T]Terminar a Jogada");
	}

	/**
	 * Fica lendo a entrado do teclado esperando a escolha do jogador
	 */
	public void verificar() {
		Pattern p = Pattern.compile("[PpDdTt]");
		mostrarOpcoes();
		while (scanner.hasNext()) {
			String opcao = scanner.next();
			Matcher m = p.matcher(opcao);
			if (!m.matches()) {
				System.out.println("Op��o inv�lida!");
				mostrarOpcoes();
			} else {
				if (opcao.equals("p") || opcao.equals("P")) {
					try {
						jogo.pedirCarta();
						break;
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				} else if (opcao.equals("t") || opcao.equals("T")) {
					try {
						jogo.passarTurno();
						break;
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void run() {
		this.verificar();
	}

}
