package br.com.blackjack.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import br.com.blackjack.dominio.IBlackJack;
import br.com.blackjack.utils.RmiStarter;

/**
 * Representa um cliente para o jogo de blackjack
 * 
 * @author fernando
 * 
 */
public class BlackjackClient extends RmiStarter {

	public BlackjackClient() {
		super(BlackjackClient.class);
	}

	@Override
	public void trataInicioRmi() {
		try {
			// recupera o registry, onde fica localizado os dados do servidor
			Registry r = LocateRegistry.getRegistry(2005);

			// recupera uma instancia do jogo
			IBlackJack jogo = (IBlackJack) r.lookup(IBlackJack.ID);
			JogadorListener listener = new JogadorListener(jogo);
			jogo.adicionarJogador("Jogador", listener);

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new BlackjackClient();
	}
}
