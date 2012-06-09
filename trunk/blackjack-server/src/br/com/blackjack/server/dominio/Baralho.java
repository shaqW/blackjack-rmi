package br.com.blackjack.server.dominio;

import java.util.Collections;
import java.util.LinkedList;

import br.com.blackjack.dominio.Figura;
import br.com.blackjack.dominio.ICarta;

/**
 * Representa um baralho
 * 
 * @author fernando
 * 
 */
public class Baralho {

	// cartas do baralho
	private LinkedList<ICarta> cartas;

	public Baralho() {
		this.cartas = new LinkedList<ICarta>();

		// monta o baralho com 52 cartas
		for (int naipeIndex = 0; naipeIndex < 4; naipeIndex++) {
			// cartas numeradas
			for (int i = 1; i <= 10; i++) {
				this.cartas.add(new Carta(i));
				this.cartas.add(new Carta(i));
				this.cartas.add(new Carta(i));
				this.cartas.add(new Carta(i));
			}

			// cartas figura
			this.cartas.add(new Carta(10, Figura.REI));
			this.cartas.add(new Carta(10, Figura.RAINHA));
			this.cartas.add(new Carta(10, Figura.VALETE));
			this.cartas.add(new Carta(11, Figura.AS));
		}
	}

	/**
	 * recupera a proxima carta do baralho
	 * 
	 * @return
	 */
	public ICarta proximaCarta() {
		return !this.cartas.isEmpty() ? this.cartas.pop() : null;
	}

	/**
	 * recupera um carta virada para baixo
	 * 
	 * @return
	 */
	public ICarta proximaCartaViradaParaBaixo() {
		ICarta carta = this.proximaCarta();
		if (carta != null) {
			carta.setViradaParaBaixo(Boolean.TRUE);
		}

		return carta;
	}

	/**
	 * Embaralha as cartas do baralho
	 */
	public void embaralhar() {
		Collections.shuffle(cartas);
	}
}
