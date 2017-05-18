package Cadastro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Dados.Client;
import Dados.ItemVenda;
import Dados.Venda;
import errors.SisVendaException;

public class cadVendas {
	public static HashMap<Integer, Venda> recordSell = new HashMap<>();
	
	public static void insertSell(Venda sell){
		recordSell.put(sell.getNumVenda(), sell);
	}
	public static void deleteSell(Venda sell){
		recordSell.remove(sell);
	}
	
	public static Venda searchSellByCode(int Code) throws SisVendaException{
		Venda sell = recordSell.get(Code);
		
		if(sell == null)
			throw new SisVendaException("Não existe Venda para o código");
		
		return sell;
	}
	
	public static ArrayList<Venda> searchByPeriod(GregorianCalendar gregorian,
												  GregorianCalendar gregorianc) throws Exception{
		ArrayList<Venda> between = new ArrayList<>();
		between                  = getPeriod(gregorian,gregorianc);
				
		
		if(between.size() <=0 ){
			throw new Exception("there is no sell for this period");
		}
		Collections.sort(between,new Comparator<Venda>(){

			@Override
			public int compare(Venda sell1, Venda sell2) {
				int name =  sell1.getClient().getName().compareTo(sell2.getClient().getName());
				int date = new DateDescComparator().compare(sell1,sell2); //sell2.getDateofSell().compareTo(sell1.getDateofSell());
				
				if (name != 0)
					return name;
				
				
					return date;
			}
		 });
		return between;
	}
	
	private static ArrayList<Venda> getPeriod(GregorianCalendar gregorian, GregorianCalendar gregorianc) {
		ArrayList<Venda> between = new ArrayList<>();
		
		for(int key:recordSell.keySet()){
			if(recordSell.get(key).getDateofSell().compareTo(gregorian) > 0 
				&& recordSell.get(key).getDateofSell().compareTo(gregorianc) <= 0)
				between.add(recordSell.get(key));
		}
		
		return between;
	}
	public static ArrayList<Venda> listDescedingOrder(Client client) throws SisVendaException{
		ArrayList<Venda> alv = new ArrayList<Venda>();
		Iterator it = recordSell.entrySet().iterator();	
		Venda sell;
		
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry) it.next();
			sell = (Venda) pair.getValue();
			
			if(sell.getClient().equals(client)){
				alv.add(sell);
			}
			it.remove();
		}
		
		if(alv.size() <= 0)
			throw new SisVendaException("There was no sell for this client");
		
		Collections.sort(alv,new DateDescComparator());
		
		return alv;
		
	} 
	public static void printClientSumary(GregorianCalendar gcalendar, 
										GregorianCalendar gcalendar2){
		ArrayList<Venda> between = new ArrayList<>();
		between                  = getPeriod(gcalendar,gcalendar2);
		
		int quantity_sell = 0;
		double total_of_period =0;
		
		Collections.sort(between,new Comparator<Venda>(){

			@Override
			public int compare(Venda sell1, Venda sell2) {
				return sell1.getClient().getName().compareTo(sell2.getClient().getName());
			}
		});
		
		for(Venda sell:between){
			Client client = sell.getClient();
			for(Venda sellint:between){
				if(client.equals(sellint.getClient())){
					quantity_sell++;
					for(ItemVenda sellItem : sellint.getSellItens()){
						total_of_period +=sellItem.getValorVenda();
					}
					
				}
			}
			System.out.println("Client's Name :"+client.getName()+ "\n");
			System.out.println("Quantity of purchases :"+quantity_sell+ "\n");
			System.out.println("Amount for clients :"+total_of_period+ "\n");
		}
		
	}
	
 
}
class DateDescComparator implements Comparator<Venda>{

	@Override
	public int compare(Venda o1, Venda o2) {
		return o2.getDateofSell().compareTo(o1.getDateofSell());
	}
	  
 }
