package br.com.blackjack.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import br.com.blackjack.dominio.IBlackJack;
import br.com.blackjack.utils.RmiStarter;

public class BlackJackServer extends RmiStarter {

	public BlackJackServer() {
		super(BlackJackServer.class);
	}

	@Override
	public void doCustomRmiHandling() {
		try {
			BlackJack bj = new BlackJack();
			IBlackJack bjStub = (IBlackJack) UnicastRemoteObject.exportObject(
					bj, 0);

			Registry r = LocateRegistry.createRegistry(2005);
			r.rebind(BlackJack.ID, bjStub);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new BlackJackServer();
	}
}
