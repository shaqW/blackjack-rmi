package br.com.blackjack.dominio;

import java.io.Serializable;

import br.com.blackjack.dominio.Figura;

public interface ICarta extends Serializable {

	int getPontuacao();

	void setPontuacao(int pontuacao);

	Figura getFigura();

	void setFigura(Figura figura);

	boolean isFigura();

	boolean isViradaParaBaixo();

	boolean setViradaParaBaixo(boolean paraBaixo);
}