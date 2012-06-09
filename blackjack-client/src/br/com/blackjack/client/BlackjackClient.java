package br.com.blackjack.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import br.com.blackjack.dominio.IBlackJack;
import br.com.blackjack.utils.RmiStarter;

public class BlackjackClient extends RmiStarter {

	static int COUNT = 1;
	
	public BlackjackClient() {
		super(BlackjackClient.class);
	}

	@Override
	public void doCustomRmiHandling() {
		try {
			Registry r = LocateRegistry.getRegistry(2005);

			IBlackJack jogo = (IBlackJack) r.lookup(IBlackJack.ID);
			TurnoJogadorListener listener = new TurnoJogadorListener(jogo);
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
