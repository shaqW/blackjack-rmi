package br.com.blackjack.server.dominio;

import br.com.blackjack.dominio.Figura;
import br.com.blackjack.dominio.ICarta;

public class Carta implements ICarta {

	private int pontuacao;

	private Figura figura;

	private boolean viradaParaBaixo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.blackjack.server.dominio.ICarta#getPontuacao()
	 */
	@Override
	public int getPontuacao() {
		return pontuacao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.blackjack.server.dominio.ICarta#setPontuacao(int)
	 */
	@Override
	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.blackjack.server.dominio.ICarta#getFigura()
	 */
	@Override
	public Figura getFigura() {
		return figura;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.blackjack.server.dominio.ICarta#setFigura(br.com.blackjack.dominio
	 * .Figura)
	 */
	@Override
	public void setFigura(Figura figura) {
		this.figura = figura;
	}

	public Carta(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public Carta(int pontuacao, Figura figura) {
		this(pontuacao);
		this.figura = figura;
		this.viradaParaBaixo = Boolean.FALSE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.blackjack.server.dominio.ICarta#isFigura()
	 */
	@Override
	public boolean isFigura() {
		return this.figura != null;
	}

	@Override
	public boolean isViradaParaBaixo() {
		return this.viradaParaBaixo;
	}

	@Override
	public boolean setViradaParaBaixo(boolean paraBaixo) {
		return this.viradaParaBaixo = paraBaixo;
	}

}
