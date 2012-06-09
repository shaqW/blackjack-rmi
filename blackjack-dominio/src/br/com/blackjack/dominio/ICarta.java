package br.com.blackjack.dominio;

import java.io.Serializable;

import br.com.blackjack.dominio.Figura;

/**
 * Representa uma carta do baralho
 * 
 * @author fernando
 * 
 */
public interface ICarta extends Serializable {

	/**
	 * pontuacao da carta
	 * 
	 * @return
	 */
	int getPontuacao();

	void setPontuacao(int pontuacao);

	/**
	 * naipe da carta
	 * 
	 * @return
	 */
	Figura getFigura();

	void setFigura(Figura figura);

	/**
	 * retorna <code>TRUE</code> se Ž uma carta figura
	 * 
	 * @return
	 */
	boolean isFigura();

	/**
	 * indica se a carta est‡ virada para baixo
	 * 
	 * @return
	 */
	boolean isViradaParaBaixo();

	boolean setViradaParaBaixo(boolean paraBaixo);
}