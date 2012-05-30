package br.com.blackjack.dominio;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.List;

public interface IJogador extends Remote, Serializable {

	void setId(Long id);
	
	Long getId();
	
	String getNome();

	void setNome(String nome);

	void adicionarCarta(ICarta c);

	List<ICarta> getCartas();
	
	int getPontuacaoCartas();

}
