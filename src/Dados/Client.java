package Dados;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.GregorianCalendar;

/**
 *class:Client 
 *Constructor parmeters:String cpfp,String namep,String telephonep,String emailp,
			GregorianCalendar dateofLogp,GregorianCalendar dateofUpdatep
 ***/
public class Client implements Serializable,Comparator {
	
	private String cpf;
	private String name;
	private String telephone;
	private String email;
	private GregorianCalendar dateofLog;
	private GregorianCalendar dateofUpdate;
	
	 /**
	 * Constructor for the class Client
	 * @param  cpf Cpf of the client
	 * @param  name name of the client
	 * @param  telephone telephone of the client
	 * @param  email of the client
	 * @param  dateofLogp date of the log
	 * @param  dateofUpdatep of the update
	 * 
	 **/	
	public Client(String cpfp,String namep,String telephonep,String emailp,
			GregorianCalendar dateofLogp,GregorianCalendar dateofUpdatep){
		
		this.cpf  	      = cpfp;
		this.name 	      = namep;
		this.telephone    = telephonep;
		this.email        = emailp;
		this.dateofLog 	  = dateofLogp;
		this.dateofUpdate = dateofUpdatep;
	}
	
	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public GregorianCalendar getDateofLog() {
		return dateofLog;
	}


	public void setDateofLog(GregorianCalendar dateofLog) {
		this.dateofLog = dateofLog;
	}


	public GregorianCalendar getDateofUpdate() {
		return dateofUpdate;
	}


	public void setDateofUpdate(GregorianCalendar dateofUpdate) {
		this.dateofUpdate = dateofUpdate;
	}
	
	public String toString(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String objectString;
		
		return "Client�s CPF: "+this.cpf+"\n" + " Client�s name: "+ this.name +"\n" +
				"Client's Telephone: "+this.telephone + "\n Client�s email: "+this.email+
				"\nClient�s date of log: "+sdf.format(this.dateofLog.getTime())+"\n" +
				"Client�s date of update: "+ (this.dateofUpdate == null ? "" : sdf.format(this.dateofLog.getTime()));
	}

	@Override
	 public  int compare(Object o1, Object o2) {
		Client client = (Client)o1;
		String name   = (String)o2;
		return client.getName().compareTo(name);
		
		
	}


	
	

}
