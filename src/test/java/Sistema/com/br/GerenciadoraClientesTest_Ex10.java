package Sistema.com.br;

import java.util.ArrayList;

import junit.framework.TestCase;

public class GerenciadoraClientesTest_Ex10 extends TestCase {

	private GerenciadoraClientes gerClientes;
	private int idCLiente01 = 1;
	private	int idCLiente02 = 2;
	private GerenciadoraContas gerContas;
	
	public void setUp() {
	
		/* ========== Montagem do cenário ========== */
		
		// criando alguns clientes
		Cliente cliente01 = new Cliente(idCLiente01, "Gustavo Farias", 31, "gugafarias@gmail.com", 1, true);
		Cliente cliente02 = new Cliente(idCLiente02, "Felipe Augusto", 34, "felipeaugusto@gmail.com", 1, true);
		
		// inserindo os clientes criados na lista de clientes do banco
		ArrayList<Cliente> clientesDoBanco = new ArrayList<Cliente>();
		clientesDoBanco.add(cliente01);
		clientesDoBanco.add(cliente02);
		
		gerClientes = new GerenciadoraClientes(clientesDoBanco);
	}

	public void tearDown() {
		gerClientes.limpa();
	}
	
	public void testPesquisaCliente() {

		/* ========== Execução ========== */
		Cliente cliente = gerClientes.pesquisaCliente(idCLiente01);
		
		/* ========== Verificações ========== */
		assertEquals(cliente.getId(), idCLiente01);
		assertEquals(cliente.getNome(), "Gustavo Farias");
		
	}
	


	public void testPesquisaClienteInexistente() {

		/* ========== Execução ========== */
		Cliente cliente = gerClientes.pesquisaCliente(1001);
		
		/* ========== Verificações ========== */
		assertNull(cliente);
		
	}
	
	public void testRemoveCliente() {
		
		/* ========== Execução ========== */
		boolean clienteRemovido = gerClientes.removeCliente(idCLiente02);
		
		/* ========== Verificações ========== */
		assertTrue(clienteRemovido);
		assertEquals(gerClientes.getClientesDoBanco().size(),1);
		assertNull(gerClientes.pesquisaCliente(idCLiente02));
		
	}
	
	public void testRemoveClienteInexistente() {

	
		/* ========== Execução ========== */
		boolean clienteRemovido = gerClientes.removeCliente(1001);
		
		/* ========== Verificações ========== */
		assertEquals(clienteRemovido, false);
		assertEquals(gerClientes.getClientesDoBanco().size(), 2);
		
	}
	
	public void testClienteIdadeAceitavel() throws IdadeNaoPermitidaException {

		/* ========== Montagem do Cenário ========== */		
		Cliente cliente = new Cliente(1, "Gustavo", 25, "guga@gmail.com", 1, true);
		
		/* ========== Execução ========== */
		boolean idadeValida = gerClientes.validaIdade(cliente.getIdade());
		
		/* ========== Verificações ========== */
		assertTrue(idadeValida);	
	}
	
	public void testClienteIdadeAceitavel_02() throws IdadeNaoPermitidaException {

		/* ========== Montagem do Cenário ========== */		
		Cliente cliente = new Cliente(1, "Gustavo", 18, "guga@gmail.com", 1, true);
		
		/* ========== Execução ========== */
		boolean idadeValida = gerClientes.validaIdade(cliente.getIdade());
		
		/* ========== Verificações ========== */
		assertTrue(idadeValida);	
	}
	
	public void testClienteIdadeAceitavel_03() throws IdadeNaoPermitidaException {

		/* ========== Montagem do Cenário ========== */		
		Cliente cliente = new Cliente(1, "Gustavo", 65, "guga@gmail.com", 1, true);
		
		/* ========== Execução ========== */
		boolean idadeValida = gerClientes.validaIdade(cliente.getIdade());
		
		/* ========== Verificações ========== */
		assertTrue(idadeValida);	
	}

	public void testClienteIdadeAceitavel_04() throws IdadeNaoPermitidaException {

		/* ========== Montagem do Cenário ========== */		
		Cliente cliente = new Cliente(1, "Gustavo", 17, "guga@gmail.com", 1, true);

		/* ========== Execução ========== */
		try {
			gerClientes.validaIdade(cliente.getIdade());
			fail();
		} catch (Exception e) {
			/* ========== Verificações ========== */
			assertEquals(e.getMessage(), "A idade do cliente precisa estar entre 18 e 65 anos.");
		}	
	}
	
	public void testClienteIdadeAceitavel_05() throws IdadeNaoPermitidaException {
		
		/* ========== Montagem do Cenário ========== */		
		Cliente cliente = new Cliente(1, "Gustavo", 66, "guga@gmail.com", 1, true);
		/* ========== Execução ========== */
		try {
			gerClientes.validaIdade(cliente.getIdade());
			fail();
		} catch (Exception e) {
			/* ========== Verificações ========== */
			assertEquals(e.getMessage(), "A idade do cliente precisa estar entre 18 e 65 anos.");
		}	
	}
	
	
	public void testTransfereValor() {

		/* ========== Montagem do cenário ========== */
		
		// criando alguns clientes
		int idConta01 = 1;
		int idConta02 = 2;
		ContaCorrente conta01 = new ContaCorrente(idConta01, 200, true);
		ContaCorrente conta02 = new ContaCorrente(idConta02, 0, true);
		
		// inserindo os clientes criados na lista de clientes do banco
		ArrayList<ContaCorrente> contasDoBanco = new ArrayList<ContaCorrente>();
		contasDoBanco.add(conta01);
		contasDoBanco.add(conta02);
		
		gerContas = new GerenciadoraContas(contasDoBanco);

		/* ========== Execução ========== */
		boolean sucesso = gerContas.transfereValor(idConta01, 100, idConta02);
		
		/* ========== Verificações ========== */
		assertTrue(sucesso);
		assertEquals(conta02.getSaldo(), 100.0);
		assertEquals(conta01.getSaldo(), 100.0);
	}
	
	public void testTransfereValor_SaldoInsuficiente() {

		/* ========== Montagem do cenário ========== */
		
		// criando alguns clientes
		int idConta01 = 1;
		int idConta02 = 2;
		ContaCorrente conta01 = new ContaCorrente(idConta01, 100, true);
		ContaCorrente conta02 = new ContaCorrente(idConta02, 0, true);
		
		// inserindo os clientes criados na lista de clientes do banco
		ArrayList<ContaCorrente> contasDoBanco = new ArrayList<ContaCorrente>();
		contasDoBanco.add(conta01);
		contasDoBanco.add(conta02);
		
		gerContas = new GerenciadoraContas(contasDoBanco);

		/* ========== Execução ========== */
		boolean sucesso = gerContas.transfereValor(idConta01, 200, idConta02);
		
		/* ========== Verificações ========== */
		assertTrue(sucesso);
		assertEquals(conta01.getSaldo(), -100.0);
		assertEquals(conta02.getSaldo(), 200.0);
	}

	public void testTransfereValor_SaldoNegativo() {

		/* ========== Montagem do cenário ========== */
		
		// criando alguns clientes
		int idConta01 = 1;
		int idConta02 = 2;
		ContaCorrente conta01 = new ContaCorrente(idConta01, -100, true);
		ContaCorrente conta02 = new ContaCorrente(idConta02, 0, true);
		
		// inserindo os clientes criados na lista de clientes do banco
		ArrayList<ContaCorrente> contasDoBanco = new ArrayList<ContaCorrente>();
		contasDoBanco.add(conta01);
		contasDoBanco.add(conta02);
		
		gerContas = new GerenciadoraContas(contasDoBanco);

		/* ========== Execução ========== */
		boolean sucesso = gerContas.transfereValor(idConta01, 200, idConta02);
		
		/* ========== Verificações ========== */
		assertTrue(sucesso);
		assertEquals(conta01.getSaldo(), -300.0);
		assertEquals(conta02.getSaldo(), 200.0);
	}
	
	public void testTransfereValor_SaldoNegativoParaNegativo() {

		/* ========== Montagem do cenário ========== */
		
		// criando alguns clientes
		int idConta01 = 1;
		int idConta02 = 2;
		ContaCorrente conta01 = new ContaCorrente(idConta01, -100, true);
		ContaCorrente conta02 = new ContaCorrente(idConta02, -100, true);
		
		// inserindo os clientes criados na lista de clientes do banco
		ArrayList<ContaCorrente> contasDoBanco = new ArrayList<ContaCorrente>();
		contasDoBanco.add(conta01);
		contasDoBanco.add(conta02);
		
		gerContas = new GerenciadoraContas(contasDoBanco);

		/* ========== Execução ========== */
		boolean sucesso = gerContas.transfereValor(idConta01, 200, idConta02);
		
		/* ========== Verificações ========== */
		assertTrue(sucesso);
		assertEquals(conta01.getSaldo(), -300.0);
		assertEquals(conta02.getSaldo(), 100.0);
	}
	
	public void testTransfereValor_Nenhum() {

		/* ========== Montagem do cenário ========== */
		
		// criando alguns clientes
		int idConta01 = 1;
		int idConta02 = 2;
		ContaCorrente conta01 = new ContaCorrente(idConta01, -100, true);
		ContaCorrente conta02 = new ContaCorrente(idConta02, -100, true);
		
		// inserindo os clientes criados na lista de clientes do banco
		ArrayList<ContaCorrente> contasDoBanco = new ArrayList<ContaCorrente>();
		contasDoBanco.add(conta01);
		contasDoBanco.add(conta02);
		
		gerContas = new GerenciadoraContas(contasDoBanco);

		/* ========== Execução ========== */
		boolean sucesso = gerContas.transfereValor(idConta01, 2, idConta02);
		
		/* ========== Verificações ========== */
		assertTrue(sucesso);
		assertEquals(conta01.getSaldo(), -102.0);
		assertEquals(conta02.getSaldo(), -98.0);
	}

}
