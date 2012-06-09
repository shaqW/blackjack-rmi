package br.com.blackjack.client;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.blackjack.dominio.IBlackJack;

public class LeitorOpcoes implements Runnable {

	IBlackJack jogo;
	private InputStream input;
	
	static Scanner scanner = new Scanner(System.in);

	public LeitorOpcoes(IBlackJack jogo, InputStream input) {
		this.jogo = jogo;
		this.input = input;
	}

	public void mostrarOpcoes() {
		System.out.println("Escolha uma das opções abaixo:");
		System.out
				.println("[P]Pedir Mais 1 Carta  [D]Desafiar o Croupier [T]Terminar a Jogada");
	}

	public void verificar() {
		Pattern p = Pattern.compile("[PpDdTt]");
		mostrarOpcoes();
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
