package br.com.blackjack.dominio;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface remota para o jogo de Blackjack
 * 
 * @author fernando
 * 
 */
public interface IBlackJack extends Remote {

	// id do jogo para localiza-lo no Registry
	static final String ID = "BLACKJACK";

	/**
	 * Adiciona um novo jogado ao jogo atual e cadastro o listener do jogado
	 * para que o mesmo possa receber as atualizacoes do jogo
	 * 
	 * @param nome
	 * @param listener
	 * @return
	 * @throws RemoteException
	 */
	IJogador adicionarJogador(String nome, IJogadorListener listener)
			throws RemoteException;

	/**
	 * Recupera o jogador da vez
	 * 
	 * @return
	 * @throws RemoteException
	 */
	IJogador getJogadorAtual() throws RemoteException;

	/**
	 * Recupera o croupier do jogo
	 * 
	 * @return
	 * @throws RemoteException
	 */
	IJogador getCroupier() throws RemoteException;

	/**
	 * Solicita uma nova carta para o jogador da vez
	 * 
	 * @throws RemoteException
	 */
	void pedirCarta() throws RemoteException;

	/**
	 * Recupera a lista de jogadores que estão participando do jogo
	 * 
	 * @return
	 * @throws RemoteException
	 */
	List<IJogador> getJogadores() throws RemoteException;

	/**
	 * Avança o turno do jogo. Exemplo. O jogador da vez é o JOGADOR_1, ao
	 * chamar passarTurno(), o jogador da vez será o JOGADOR_2
	 * 
	 * @throws RemoteException
	 */
	public void passarTurno() throws RemoteException;

}
