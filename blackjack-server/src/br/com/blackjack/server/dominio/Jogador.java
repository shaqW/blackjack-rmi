package br.com.blackjack.server.dominio;

import java.util.ArrayList;
import java.util.List;

import br.com.blackjack.dominio.ICarta;
import br.com.blackjack.dominio.IJogador;

public class Jogador implements IJogador {

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String nome;

	private List<ICarta> cartas;

	private int quantiaDinheiro;

	private int quantiaApostada;

	private boolean viradaParaBaixo;

	public Jogador(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<ICarta> getCartas() {
		return cartas;
	}

	public int getQuantiaDinheiro() {
		return quantiaDinheiro;
	}

	public void setQuantiaDinheiro(int quantiaDinheiro) {
		this.quantiaDinheiro = quantiaDinheiro;
	}

	public int getQuantiaApostada() {
		return quantiaApostada;
	}

	public void setQuantiaApostada(int quantiaApostada) {
		this.quantiaApostada = quantiaApostada;
	}

	public boolean isViradaParaBaixo() {
		return viradaParaBaixo;
	}

	public void setViradaParaBaixo(boolean viradaParaBaixo) {
		this.viradaParaBaixo = viradaParaBaixo;
	}

	@Override
	public void adicionarCarta(ICarta c) {
		if (cartas == null) {
			this.cartas = new ArrayList<ICarta>();
		}

		this.cartas.add(c);
	}

	@Override
	public int getPontuacaoCartas() {
		int pontos = 0;
		if (this.cartas != null) {
			for (ICarta c : this.cartas) {
				if (!c.isViradaParaBaixo()) {
					pontos += c.getPontuacao();
				}
			}
		}

		return pontos;
	}

	@Override
	public String toString() {
		return this.nome;
	}
}
