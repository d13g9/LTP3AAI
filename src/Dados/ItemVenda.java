package Dados;

import java.text.SimpleDateFormat;

public class ItemVenda {
		
	private Produto produto;
	private double unit_price;
	private double quantity_sell;
	private double valorVenda;
	
	public ItemVenda(Produto produtop,double quantity_sellp){
		this.produto       = produtop;
		this.unit_price    = produtop.getPreco_unit();
		this.quantity_sell = quantity_sellp;
		this.setValorVenda();
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public double getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}

	public double getQuantity_sell() {
		return quantity_sell;
	}
	
	public void setQuantity_sell(double quantity_sell) {
		this.quantity_sell = quantity_sell;
	}

	public double getValorVenda() {
		return this.valorVenda;
	}
	
	/**
	 * This will calculate total amount of sell and to instantiate
	 * this class will already pass as parameters unity price and quantity
	 * so this method will only multiply both values.
	 * <p> 
	 * To enforce enscapsulation this method is private 
	 * **/
	private void setValorVenda() {
		this.valorVenda = getQuantity_sell() * getUnit_price();
	}
	
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String objectString;
		
		return 	"Product's name: "+ this.produto.getNome() + "\n"+
				"Unity Price: "+this.getUnit_price() + "\n"+
				"Product Quantity: "+this.getQuantity_sell();
				
				
						
				
	}
	
}	
