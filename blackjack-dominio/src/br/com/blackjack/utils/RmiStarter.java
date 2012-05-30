package br.com.blackjack.utils;

/**
 * 
 * @author leandro
 * 
 */
public abstract class RmiStarter {

	/**
	 * 
	 * @param clazz
	 *            a class that should be in the java.rmi.server.codebase
	 *            property.
	 */
	public RmiStarter(Class<?> clazz) {

		System.setProperty("java.rmi.server.codebase",
				clazz.getProtectionDomain()
						.getCodeSource().getLocation().toString());

		System.out.println(clazz.getProtectionDomain()
				.getCodeSource().getLocation().toString());

		System.setProperty("java.security.policy",
				PolicyFileLocator.getLocationOfPolicyFile());

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		doCustomRmiHandling();
	}

	/**
	 * extend this class and do RMI handling here
	 */
	public abstract void doCustomRmiHandling();

}
