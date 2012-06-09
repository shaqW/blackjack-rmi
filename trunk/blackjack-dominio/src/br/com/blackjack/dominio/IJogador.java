package br.com.blackjack.dominio;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.List;

/**
 * Representa um jogador do jogo de blackjack
 * 
 * @author fernando
 * 
 */
public interface IJogador extends Remote, Serializable {

	/**
	 * nome do jogador. O nome identifica o jogador!
	 * 
	 * @return
	 */
	String getNome();

	void setNome(String nome);

	/**
	 * adiciona um nova carta para a m‹o do jogador
	 * 
	 * @param c
	 */
	void adicionarCarta(ICarta c);

	/**
	 * returna as cart‹o que o jogador tem na m‹o
	 * 
	 * @return
	 */
	List<ICarta> getCartas();

	/**
	 * Recupera o total de pontos que o jogador tem com as cartas na m‹o
	 * 
	 * @return
	 */
	int getPontuacaoCartas();

	/**
	 * limpa as cartas que o jogador tem na m‹o
	 */
	void limparCartas();

}
