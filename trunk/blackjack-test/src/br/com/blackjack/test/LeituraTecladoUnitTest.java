package br.com.blackjack.test;

import java.util.Scanner;
import java.util.regex.Pattern;

import org.junit.Test;

public class LeituraTecladoUnitTest {

	@Test
	public void deveriaLerTeclado() {
		Scanner scanner = new Scanner(System.in);
		Pattern p = Pattern.compile("[PpDdTt]");
		while (scanner.hasNext(p)) {
			System.out.println(scanner.nextLine());
		}
	}

}
