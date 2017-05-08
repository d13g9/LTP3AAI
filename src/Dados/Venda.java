package Dados;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Venda {
	
	static int generate_id = 0;
	
	private int numVenda;
	private Client client;
	private GregorianCalendar dateofSell;
	private ArrayList<ItemVenda> sellItens;
	
	public Venda(Client clientp,GregorianCalendar dateofSellp,ArrayList<ItemVenda> sellItens){
		this.numVenda 	= ++generate_id;
		this.client 	= clientp;
		this.dateofSell = dateofSellp;
		this.sellItens  = sellItens; 
	}


	public int getNumVenda() {
		return numVenda;
	}

	public void setNumVenda(int numVenda) {
		this.numVenda = numVenda;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public GregorianCalendar getDateofSell() {
		return dateofSell;
	}

	public void setDateofSell(GregorianCalendar dateofSell) {
		this.dateofSell = dateofSell;
	}

	public ArrayList<ItemVenda> getSellItens() {
		return sellItens;
	}

	public void setSellItens(ArrayList<ItemVenda> sellItens) {
		this.sellItens = sellItens;
	}
	
	public String toString(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		double total = 0;
		String objectString;
		
		objectString =  "Sell code: "+this.getNumVenda()+"\n" + 
						"Client's name: "+ this.client.getName()+"\n" +
						"Date of Sell: "+
						sdf.format(this.getDateofSell().getTime())+ 
						"\n Sell´s Products: \n";
						for(ItemVenda itemvenda : this.getSellItens()){
							objectString += itemvenda.toString()+"\n";
							total        += itemvenda.getValorVenda();
						}
						objectString += "Total of Sell: "+total; 
		return objectString;
					
	}
	
	
}
