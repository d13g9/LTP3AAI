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
		//insertTestData();
		checkDatabase();
		do{
			printMenu();
			opt = readMenu();
			callAction(opt);
		}while(opt > 0);
	}
	
	/**
	 * <p>
	 * Method that will insert test data
	 * </p>
	 * 
	 */
	private static void insertTestData() {
		Client client4 = new Client("55150685488","Alan",
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
			
			cadProdutos.insertProduct(product);
			cadProdutos.insertProduct(product1);
			cadProdutos.insertProduct(product2);
			
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
			
			//cadVendas.insertSell(venda);
			//cadVendas.insertSell(venda1);
			//cadVendas.insertSell(venda2);
			
			//saveData(Table.SELLS);
			saveData(Table.PRODUCT);
			saveData(Table.CLIENT);
			
			try {
				for(Venda sell:cadVendas.searchByPeriod(new GregorianCalendar(2016,02,01),
														new GregorianCalendar(2016,02,30))){
					//System.out.println(sell);
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
		
	

	/**
	 * <p>
	 * 	Load tables to memory
	 * </p>
	 */
	private static void checkDatabase() {
		if (new File("CLIENT.obj").exists()) {
			loadFile(Table.CLIENT);
		}
		if(new File("SELLS.obj").exists()){
			loadFile(Table.SELLS);
		}
		if(new File("PRODUCT.obj").exists()){
			loadFile(Table.PRODUCT);
		}
	}

	/**
	 * <p>
	 * Load data from tables
	 * </p>
	 * @param table enumerations for different tables
	 */
	private static void loadFile(Table table) {
		switch(table){
		case CLIENT:
			try{
				ObjectInputStream inp = new ObjectInputStream
										(new FileInputStream("CLIENT.obj"));
				cadClientes.clientMap = (HashMap<String, Client>)inp.readObject();
				inp.close();
			}catch(Exception ex){
				System.out.println("We couldn`t load client's data");
			}
			break;
		case SELLS:
			try{
				ObjectInputStream inp = new ObjectInputStream
										(new FileInputStream("SELLS.obj"));
				cadVendas.recordSell = (HashMap<Integer, Venda>)inp.readObject();
				inp.close();
				//System.out.println("Size of hashmap: "+cadVendas.recordSell.size());
				Venda.generate_id = cadVendas.recordSell.get(cadVendas.recordSell.size()).getNumVenda();
			}catch(Exception ex){
				System.out.println("We couldn`t load sells data");
			}
			break;
		case PRODUCT:
			try{
				ObjectInputStream inp = new ObjectInputStream
										(new FileInputStream("PRODUCT.obj"));
				cadProdutos.produtosMap = (HashMap<Integer, Produto>)inp.readObject();
				inp.close();
				Produto.codigogerador = cadProdutos.produtosMap.get(cadProdutos.produtosMap.size()).getCodigo();
			}catch(Exception ex){
				System.out.println("We couldn`t load product's data");
			}
			break;
		}
		
	}

	/**
	 * <p>
	 * 	Check which action user want to do take
	 * </p>
	 * @param opt option entered by user
	 */
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
		case 4:
			printClientsInfo();
			break;
		case 5:
			searchClientsByName();
			break;
		case 6:
			insertProduct();
			break;
		case 7:
			updateProduct();
			break;
		case 8: 
			deleteProduct();
			break;
		case 9:
			searchProductByName();
			break;
		case 10:
			sell();
			break;
		case 11:
			deleteSell();
			break;
		case 12:
			sellsByDescendingOrder();
			break;
		case 13:
			printSellSumary();
			break;
		} 
		
	}

	private static void printSellSumary() {
		GregorianCalendar gcdate_initial = new GregorianCalendar();
		GregorianCalendar gcdate_final = new GregorianCalendar();
		
		getPeriod(gcdate_initial,gcdate_final);
		
		cadVendas.printClientSumary(gcdate_initial, gcdate_final);
	}

	private static void sellsByDescendingOrder() {
		GregorianCalendar gcdate_initial = new GregorianCalendar();
		GregorianCalendar gcdate_final = new GregorianCalendar();
		ArrayList<Venda> alv = new ArrayList<>();
				
		getPeriod(gcdate_initial,gcdate_final);
		
		//Console.printPrompt("after readDate initial date : "+ new java.text.SimpleDateFormat("dd/MM/yyy").format(gcdate_initial.getTime())+"\n");
		//Console.printPrompt("after readDate final date : "+ new java.text.SimpleDateFormat("dd/MM/yyy").format(gcdate_final.getTime())+"\n");
		
		try {
			alv = cadVendas.searchByPeriod(gcdate_initial, gcdate_final);
		} catch (Exception e) {
			Console.printPrompt(e.getMessage());
			return;
		}
		
		for(Venda venda: alv){
			Console.printPrompt(venda.toString());
		}
		
		
	}

	/**
	 * <p>
	 * Get Period for reports
	 * </p>
	 * @param gcdate_initial initial date
	 * @param gcdate_final   final date
	 */
	private static void getPeriod(GregorianCalendar gcdate_initial, GregorianCalendar gcdate_final) {
		do{
			readDate("Please type the first date of the period (must be valid and cannot be after today)",gcdate_initial);
			readDate("Please type the second date of the period "
										+ " (must be valid and cannot be after today and must be bigger than first date)",gcdate_final);
		}while(LtpUtil.subtrairDatas(gcdate_initial, gcdate_final) < 0);
		
	}
	
	/**
	 * <p>
	 * 	Delete Sell
	 * </p>
	 */
	private static void deleteSell() {
		Venda sell = null;
		
		try {
			sell = cadVendas.searchSellByCode(Console.readInt("please type the sell Code"));
		} catch (SisVendaException e) {
			Console.printPrompt(e.getMessage());
			return;
		}
		Console.printPrompt(sell.toString());
		
		if(getConfirmation("are you sure you want to delete this sell ? y = yes / n = no")){
			cadVendas.deleteSell(sell);
		}
		
		saveData(Table.SELLS);
	}

	private static void sell() {
		Client cliente = null;
		GregorianCalendar gcdate = new GregorianCalendar();
		
		try {
			cliente = cadClientes.searchByCPf(readCPF());
		} catch (SisVendaException e) {
			Console.printPrompt(e.getMessage());
			return;
		}
		
		readDate("Please type date of sell(must be a valid date and cannot be after than todays date)",gcdate);
	
		ArrayList<ItemVenda> itv = new ArrayList<>();
		
		fillItems(itv);
		if(itv.size() > 0){
			Venda sell = new Venda(cliente,gcdate,itv);
			Console.printPrompt(sell.toString());
			cadVendas.insertSell(sell);
			saveData(Table.SELLS);
		}
		else
			Console.printPrompt("No product inserted into shop card");
		
		
	}

	private static void readDate(String string,GregorianCalendar gcp) {
		String date;
					
		do{
			date = readString(string);
		}while(!LtpUtil.validarData(date, gcp) && gcp.after(new GregorianCalendar()));
		
		//Console.printPrompt("Inside read Date: "+ new java.text.SimpleDateFormat("dd/MM/yyy").format(gcp.getTime()));
	}
	
	
	/**
	 * <p>
	 * 	Fill shop cart
	 * </p>
	 * @param itv list of items purchased
	 */
	private static void fillItems(ArrayList<ItemVenda> itv) {
			int opt = 0,quantity = 0;
			Produto product = null;
			boolean found = false;
			
		do{
			opt = Console.readInt("Type 1 to search by products name \n Type 2 insert a product in your shop card(will need product code)"
									+ " \n Type 0 to leave");
			switch(opt){
			case 1:
				searchProductByName();
				break;
			case 2:
				try {
					product = cadProdutos.searchByCode(Console.readInt("Type the code of the product"));
				} catch (SisVendaException e) {
					Console.printPrompt(e.getMessage());
					break;
				}
				if(itv.size() > 0){
					for(ItemVenda itvo : itv){
						if(itvo.getProduto().getCodigo() == product.getCodigo()){
							if(getConfirmation("You already have this product on your shopping cart, "
									+ "would you like to alter quantity ? y = yes / n =  no ")){
							    quantity = readQuantity();
								itvo.setQuantity_sell(quantity);
								itvo.setValorVenda();
								found = true;
							}
						}
								
					}
					
				}
				if(!found){
					quantity = readQuantity();
					itv.add(new ItemVenda(product, quantity));
				}
				found = false;
				break;
			}
		}while(opt > 0);
		
	}

	private static int readQuantity() {
		int quantity = 0;
		
		do{
			quantity = Console.readInt("Please type the quantity(must be bigger than zero)");
		}while(quantity <= 0);
		
		return quantity;
	}



	private static void searchProductByName() {
		String prodname = readString("Please type the name of the product or the letters that the product's name contains");
		ArrayList<Produto> alp = new ArrayList<>();
		
		try {
			alp = cadProdutos.getListOrderByName(prodname);
		} catch (SisVendaException e) {
			Console.printPrompt(e.getMessage());
			return;
		}
		
		for(Produto product : alp){
			Console.printPrompt(product.toString());
		}
	}





	/**
	 * <p>
	 * 	Delete product
	 * </p>
	 */
	private static void deleteProduct() {
		Produto produto = null;
		
		try {
			produto = cadProdutos.searchByCode(Console.readInt("Please type the code of the product"));
		} catch (SisVendaException e) {
			Console.printPrompt(e.getMessage());
			return;
		}
		
		for(int vendacode : cadVendas.recordSell.keySet()){
			for(ItemVenda item : cadVendas.recordSell.get(vendacode).getSellItens()){
				if(item.getProduto().getCodigo() == produto.getCodigo()){
					Console.printPrompt("Cannot delete this product there is records of sells with this product");
					return;
				}				
			}
		}
		
		Console.printPrompt(produto.toString());
		if(getConfirmation("Are you sure you want to delete this product ? y = yes / n = no")){
			cadProdutos.deleteProduct(produto);
		}
		saveData(Table.PRODUCT);
		
	}

	private static void updateProduct() {
		int productCode = 0;
		Produto product = null;
		
		productCode = Console.readInt("Please type the code of the product");
		
		try {
			product = cadProdutos.searchByCode(productCode);
		} catch (SisVendaException e) {
			Console.printPrompt(e.getMessage());
			return;
		}
		
		Console.printPrompt(product.toString());
		
		if(getConfirmation("are you sure you want to update products information ? y = yes / n = no")){
			product.setNome(readString("type the new name of the product:"));
			product.setDateOfUpdate(new GregorianCalendar());
			product.setPreco_unit(readPrice());
		}
		
		saveData(Table.PRODUCT);
		
	}

	/**
	 * <p>
	 * To log in a new Product
	 * </p>
	 */
	private static void insertProduct() {
		Produto produto = null;
		String prodName = null;
		double price = 0;
		
		prodName = readString("Please type the products name");
		price    = readPrice(); 
		
		produto = new Produto(prodName,price,new GregorianCalendar(),null);
		Console.printPrompt(produto.toString());
		cadProdutos.insertProduct(produto);
		
		saveData(Table.PRODUCT);
	}





	private static double readPrice() {
		double price = 0;
		
		do{
			price = Console.readDouble("Please type the price(cannot be lesser than zero)");			
		}while(price <= 0);
		
		return price;
	}
	private static void searchClientsByName() {
		ArrayList<Client> alclient = new ArrayList<>();
		
		try {
			alclient = cadClientes.getListOrderByName(likeName());
		} catch (SisVendaException e) {
			Console.printPrompt(e.getMessage());
		}
		
		if(alclient.size() == 0){
			Console.printPrompt("No Clients names were matches");
			return;
		}
			
		for(Client client : alclient){
			Console.printPrompt(client.toString());
		}
		
		
	}

	private static void printClientsInfo() {
		Client client = null;
		
		try {
			client = cadClientes.searchByCPf(readCPF());
			Console.printPrompt(client.toString());
		} catch (SisVendaException e) {
			Console.printPrompt(e.getMessage());
		}

	}

	/**
	 * <p>
	 * Delete Client
	 * </p>
	 */
	private static void deleteClient() {
		String cpf;
		Client client = null;
		
		cpf = readCPF();
		try {
			client = loadClient(cpf);
		} catch (SisVendaException e) {
			Console.printPrompt((e.getMessage()));
			return;
		}
		
		try {
			if(cadVendas.listDescedingOrder(client).size() > 0)
				Console.printPrompt("You need to close account records first(sells)");
			return;
		} catch (SisVendaException e) {
			// TODO Auto-generated catch block
		}
		
		Console.printPrompt(client.toString());
		
		if(Console.readLine("are you sure you want to delete this client ? y= yes / other letter to leave").equals("y"))
			cadClientes.deleteClient(client);
	
	}
	

	/**
	 * <p>
	 * Load client from database
	 * </p>
	 * @param cpf cpf of the client
	 * @return client if found
	 * @throws SisVendaException exception
	 */
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
		
		Console.printPrompt(client.toString());	
		
		if(getConfirmation("Are you sure you want to update client's infor ? y = yes / n = no")){
			client.setName(readName());
			client.setDateofUpdate(new GregorianCalendar());
			client.setEmail(readEmail());
			client.setTelephone(readString("Type the new Telephone"));
			cadClientes.insertClient(client);
		}
		
	
	}

	
	/**
	 * <p>
	 * 	Get user Confirmation
	 * </p>
	 * @param string message
	 * @return boolean return of confirmation 
	 */
	private static boolean getConfirmation(String string) {
		char opt = Character.MIN_VALUE;
		
		opt = Console.readLine(string).charAt(0);
		
		switch(opt){
		case 'y':
			return true;
		case 'n':
			return false;
		default:
			Console.printPrompt("Unknown option");
			break;
		}
		
		return false;
	}

	/**
	 * <p>
	 * User actiont to log in a new client
	 * </p>
	 */
	private static void insertClient() {
	   String cpf = "",name = "",telefone = "",email = "";
	   Client client = null;
	  
	   cpf = readCPF();
	   try {
		   client = cadClientes.searchByCPf(cpf);
		   if(client != null){
			   Console.printPrompt("We already have a client with this CPF");
				return;
			}
		} catch (SisVendaException e) {
		}
		  
	   name 	= readName();
	   telefone = readString("Telefone(cannot be null):");
	   email    = readEmail();
	   
	  client = new Client(cpf,name,telefone,email,
			   new GregorianCalendar(),null);
	   
	   cadClientes.insertClient(client);
	   saveData(Table.CLIENT);
	
	}

	private static String readName() {
		String name;
		
		do{
			name = readString("Please type the name(cannot be empyt and must have at least a name and last name)");
		}while(LtpUtil.contarPalavras(name) < 2);
		
		return name;
	}
	/**
	 * <p>
	 * Read name or the letters that make part of the name
	 * </p>
	 * @return input form user name or letters that name contains
	 */
	private static String likeName(){
		String name = null;
		
		do{
			name = Console.readLine("Please type the name or letters that the name contains(cannot be empyt)");
		}while(name == null);
		
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
			e.printStackTrace();
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
			System.out.println("4 check client's account information");
			System.out.println("5 check accounts information order by client's name ");
			System.out.println("6 to insert new product");
			System.out.println("7 to update product record");
			System.out.println("8 to delete product record");
			System.out.println("9 to search product record");
			System.out.println("10 Record a sell ");
			System.out.println("11 Delete sell Record");
			System.out.println("12 Sells order by name and descending period");
			System.out.println("13 Client Summary");
	
	}
	
	
}
