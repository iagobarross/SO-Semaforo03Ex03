package view;

import controller.ThreadBanco;

public class Principal {

	public static void main(String[] args) {
		double saldoInicial = 1000.0;
		String tipoTransacao = "";

		for (int i = 0; i < 20; i++) {
			if (Math.random() > 0.5) {
				tipoTransacao = "saque";
			} else {
				tipoTransacao = "deposito";
			}
			double valorTransacao = (Math.random() * 500) + 1;
			Thread transacao = new ThreadBanco(tipoTransacao, saldoInicial, valorTransacao);
			transacao.start();
		}
	}
}