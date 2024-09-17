/*3. Um banco deve controlar Saques e Depósitos.
O sistema pode permitir um Saque e um Depósito Simultâneos, mas nunca 2 Saques ou 2
Depósitos Simultâneos. Para calcular a transação (Saque ou Depósito), o método deve
receber o código da conta, o saldo da conta e o valor a ser transacionado. Deve-se montar
um sistema que considera 20 transações simultâneas enviadas ao sistema (aleatoriamente,
essas transações podem ser qualquer uma das opções) e tratar todas as transações. Como
são transações aleatórias, podem ser 20 saques e 0 depósitos ou 19 saques e 1 depósito ou
18 saques e 2 depósitos ou .... ou 1 saque e 19 depósitos ou 0 saque e 20 depósitos.
*/
package controller;

import java.util.concurrent.Semaphore;

public class ThreadBanco extends Thread{

	 private static final Semaphore saque = new Semaphore(1);
	    private static final Semaphore deposito = new Semaphore(1);

	    private String tipoTransacao;
	    private int idConta;
	    private double saldoConta;
	    private double valorTransacao;

	    public ThreadBanco(String tipoTransacao, double saldoConta, double valorTransacao) {
	        this.tipoTransacao = tipoTransacao;
	        this.idConta = (int)threadId();
	        this.saldoConta = saldoConta;
	        this.valorTransacao = valorTransacao;
	    }

	    @Override
	    public void run() {
	        if (tipoTransacao.equals("saque")) {
	            realizarSaque();
	        } else if (tipoTransacao.equals("deposito")) {
	            realizarDeposito();
	        }
	    }

	    private void realizarSaque() {
	        try {
	            saque.acquire();
	            if (saldoConta >= valorTransacao) {
	                saldoConta -= valorTransacao;
	                System.out.println("Conta #" + idConta + ": Saque de R$ " + valorTransacao + " realizado. Saldo atual: R$ " + saldoConta);
	            } else {
	                System.out.println("Conta #" + idConta + ": Saldo insuficiente para saque de R$ " + valorTransacao);
	            }
	            
	            sleep(1000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } finally {
	            saque.release();
	        }
	    }

	    private void realizarDeposito() {
	        try {
	            deposito.acquire();
	            saldoConta += valorTransacao;
	            System.out.println("Conta #" + idConta + ": Depósito de R$ " + valorTransacao + " realizado. Saldo atual: R$ " + saldoConta);
	            sleep(1000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } finally {
	            deposito.release();
	        }
	    }
}