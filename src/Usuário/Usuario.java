package Usuário;

import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.management.ClassLoadingMXBean;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Scanner;

import Cadastro.cadClientes;
import Cadastro.cadProdutos;
import Cadastro.cadVendas;
import Dados.Client;
import Dados.ItemVenda;
import Dados.Produto;
import Dados.Venda;
import errors.SisVendaException;
import utilitarios.Console;
import utilitarios.LtpUtil;

public class Usuario {

	public enum Table {CLIENT,PRODUCT,SELLS}
	Table table;
	
	public static void main(String[] args){
		
		int opt = 0;
		checkDatabase();
		do{
			printMenu();
			opt = readMenu();
			callAction(opt);
			
			
			
		}while(opt > 0);
		
				
		
		/*Client client4 = new Client("55150685488","Alan",
				  "031993533417","dlbtar@gmail.com",
				   new GregorianCalendar(),null);
		
		Client client = new Client("08328729628","Diego Leibnz",
								  "031993533417","dlbtar@gmail.com",
								   new GregorianCalendar(),null);
		Client client2 = new Client("89385111973","Jace Beleren",
				  "031994533417","JaceBeleren@gmail.com",
				   new GregorianCalendar(),null);
		Client client3 = new Client("00846588277","Liliana Vess",
				  "031995533417","LilianaVess@gmail.com",
				   new GregorianCalendar(),null);
		
		cadClientes.insertClient(client4);
		cadClientes.insertClient(client);
		cadClientes.insertClient(client2);
		cadClientes.insertClient(client3);
		
		try {
			for(Client client5 : cadClientes.getListOrderByName("Jace")){
				//System.out.println(client5);
			}
		} catch (SisVendaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Produto product = new Produto("pc windows", 2500.00, new GregorianCalendar(), 
				null);
		Produto product1 = new Produto("mini mac", 3000.00, new GregorianCalendar(), 
				null);
		Produto product2 = new Produto("ps4", 2500.00, new GregorianCalendar(), 
				null);
	
		
		ItemVenda iv = new ItemVenda(product,1);
		ItemVenda iv1 = new ItemVenda(product1,1);
		
		ArrayList<ItemVenda> al = new ArrayList<ItemVenda>();
		al.add(iv);
		al.add(iv1);
		
		ArrayList<ItemVenda> al2 = new ArrayList<ItemVenda>();
		al2.add(iv);
		
		
		Venda venda = new Venda(client4, new GregorianCalendar(2016,02,14),al);
		Venda venda1 = new Venda(client4, new GregorianCalendar(2016,02,16),al2);
		Venda venda2 = new Venda(client4, new GregorianCalendar(2016,02,15),al);
		
		cadVendas.insertSell(venda);
		cadVendas.insertSell(venda1);
		cadVendas.insertSell(venda2);
		
		try {
			for(Venda sell:cadVendas.searchByPeriod(new GregorianCalendar(2016,02,01),
													new GregorianCalendar(2016,02,30))){
				System.out.println(sell);
			}
			
		} catch (SisVendaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		//cadVendas.printClientSumary(new GregorianCalendar(2016,02,01),
				//new GregorianCalendar(2016,02,30));
		//al.add(iv);
		
		
		//System.out.println(venda.toString());*/
		
		
	}

	private static void checkDatabase() {
		if (new File("CLIENT.obj").exists()) {
			loadFile();
		}
	}

	private static void loadFile() {
		try{
			ObjectInputStream inp = new ObjectInputStream
									(new FileInputStream("CLIENT.obj"));
			cadClientes.clientMap = (HashMap<String, Client>)inp.readObject();
			inp.close();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	private static void callAction(int opt) {
		switch(opt){
		case 1:
		    insertClient();
			break;
		case 2:
			updateClient();
			break;
		case 3:
			deleteClient();
			break;
		}
		
	}

	private static void deleteClient() {
		String cpf;
		Client client = null;
		
		cpf = readCPF();
		try {
			client = loadClient(cpf);
		} catch (SisVendaException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		if(client == null)
			return;
		
		cadClientes.deleteClient(client);
	
	}
	

	private static Client loadClient(String cpf) throws SisVendaException {
		Client client;
		
		client = cadClientes.searchByCPf(cpf);
		return client;
	}

	private static void updateClient() {
		String cpf;
		Client client = null;
				
		cpf = readCPF();
		try {
			client = cadClientes.searchByCPf(cpf);
		} catch (SisVendaException e) {
			 Console.printPrompt(e.getMessage());
			 return;
		}
				
		client.setName(readName());
		client.setDateofUpdate(new GregorianCalendar());
		client.setEmail(readEmail());
		client.setTelephone(readString("Type the new Telephone"));
		cadClientes.insertClient(client);
	
	}

	private static void insertClient() {
	   String cpf = "",name = "",telefone = "",email = "";
	   boolean flagClientExists = false;
	   Client client = null;
	  
	   do{
		   cpf  			= readCPF();
		   flagClientExists = clientExists(cpf,false);
		   
		   if(flagClientExists)
			  Console.printPrompt("We already have a client with this CPF");
	   }while(flagClientExists);
	  
	   name 	= readName();
	   telefone = readString("Telefone(cannot be null):");
	   email    = readEmail();
	   
	  client = new Client(cpf,name,telefone,email,
			   new GregorianCalendar(),null);
	   
	   cadClientes.insertClient(client);
	   saveData(Table.CLIENT);
	
	}

	private static boolean clientExists(String cpf, boolean b) {
		Client client;
		
		try {
			client = loadClient(cpf);
			return true;
		} catch (SisVendaException e) {
			if(b)
				Console.printPrompt(e.getMessage());
			
			return false;
		}
	}

	private static String readName() {
		String name;
		
		do{
			name = readString("Please type the name(cannot be empyt)");
		}while(LtpUtil.contarPalavras(name) < 2);
		
		return name;
	}

	private static void saveData(Table tablep) {
		ObjectOutputStream out;
		try {
			switch(tablep){
			case CLIENT:
				out = new ObjectOutputStream(new FileOutputStream("CLIENT.obj"));
				out.writeObject(cadClientes.clientMap);
				out.close();
				break;
			case PRODUCT:
				out = new ObjectOutputStream(new FileOutputStream("PRODUCT.obj"));
				out.writeObject(cadProdutos.produtosMap);
				out.close();
				break;
			case SELLS:
				out = new ObjectOutputStream(new FileOutputStream("SELLS.obj"));
				out.writeObject(cadVendas.recordSell);
				out.close();
				break;
				
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static String readEmail() {
		String email = "";
		boolean flag = false;
		
		 do{
			 email = Console.readLine("E-mail(Valid)");  
			 flag = (!LtpUtil.validarEmail(email)) ? true : false;
		  }while(flag);
		 
		  return email;
	}

	private static String readString(String message) {
		String string  = "";
		boolean flag = false;
		
		 do{
			 string = Console.readLine(message);
			 flag = string.isEmpty() ? true : false;
		  }while(flag);
		 
		 
		 return string;
	}
	private static String readCPF() {
		String cpf = "";
		boolean flag = false;
		
		
		 do{
			 cpf = readString("CPF(cannot be empty and must be valid):");  
			 flag = (!LtpUtil.validarCPF(cpf)) ? true : false;
		 }while(flag);
		 
		 return cpf;
	}

	private static int readMenu() {
		Scanner sc = new Scanner(System.in);
		return sc.nextInt();
	}

	private static void printMenu() {
		
			System.out.println("Type your action:");
			System.out.println("1 to create new account for a client");
			System.out.println("2 to update account for a client");
			System.out.println("3 to delete account for a client");
			System.out.println("4 to insert new product");
			System.out.println("5 to update account for a client");
			System.out.println("6 to delete account for a client");
			System.out.println("7 check client's account information");
			System.out.println("8 check accounts information order by client's name ");
			System.out.println("9 check accounts information order by client's name ");
	
	}
	
	
}
