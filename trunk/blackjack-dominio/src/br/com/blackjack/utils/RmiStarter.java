package br.com.blackjack.utils;

/**
 * 
 * @author fernando
 * 
 */
public abstract class RmiStarter {

	/**
	 * 
	 * @param clazz
	 *            - a classe que deve ser colocada na propriedade
	 *            java.rmi.server.codebase
	 * 
	 */
	public RmiStarter(Class<?> clazz) {

		System.setProperty("java.rmi.server.codebase", clazz
				.getProtectionDomain().getCodeSource().getLocation().toString());

		System.out.println(clazz.getProtectionDomain().getCodeSource()
				.getLocation().toString());

		System.setProperty("java.security.policy",
				PolicyFileLocator.getLocationOfPolicyFile());

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		trataInicioRmi();
	}

	/**
	 * ponto de extensão da class
	 */
	public abstract void trataInicioRmi();

}
