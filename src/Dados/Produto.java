package Dados;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * 
 * @author n226324633
 *
 *classe:Produto
 */
public class Produto implements Serializable{

	public static int codigogerador = 0;
	
	private int codigo;
	private double preco_unit;
	private GregorianCalendar dateOfLog;
	private GregorianCalendar dateOfUpdate;
	private String nome;
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public double getPreco_unit() {
		return preco_unit;
	}

	public void setPreco_unit(double preco_unit) {
		this.preco_unit = preco_unit;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public GregorianCalendar getDateOfLog() {
		return dateOfLog;
	}

	public void setDateOfLog(GregorianCalendar dateOfLog) {
		this.dateOfLog = dateOfLog;
	}

	public GregorianCalendar getDateOfUpdate() {
		return dateOfUpdate;
	}

	public void setDateOfUpdate(GregorianCalendar dateOfUpdate) {
		this.dateOfUpdate = dateOfUpdate;
	}
	
	
	public Produto(String name,double preco_unitp,GregorianCalendar dateOfLogp,
				  GregorianCalendar dateOfUpdate){
		setNome(name);
		setCodigo(++codigogerador);
		setPreco_unit(preco_unitp);
		setDateOfLog(dateOfLogp);
		setDateOfUpdate(dateOfUpdate);
		
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String objectString;
		
		return "Product's code: "+this.getCodigo()+"\n" + " Product's name: "+ this.getNome() 
				+"\n" +
				"Unity Price: "+this.getPreco_unit() + 
				"\n Product's date of log: "+
				(this.getDateOfLog() == null ? "":sdf.format(this.getDateOfLog().getTime()))+
				"\n" +
				"Product's date of update: "+ 
				(this.getDateOfUpdate() == null ? "" : 
						sdf.format(this.getDateOfUpdate().getTime())) + "\n";
	}
	
	
}
