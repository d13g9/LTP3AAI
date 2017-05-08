package Cadastro;

import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import Dados.Client;
import errors.SisVendaException;

public class cadClientes{
	
	public static HashMap<String, Client> clientMap = new HashMap<>(); 

	public static void  insertClient(Client client){
		clientMap.put(client.getCpf(), client);
	}
	
	public static void deleteClient(Client client){
		clientMap.remove(client.getCpf());
	}
	public static Client searchByCPf(String cpf) throws SisVendaException{
		Client client =  clientMap.get(cpf);
		
		if(client == null){
			throw new SisVendaException("Não Existe cliente para o cpf "+cpf);
		}
		
		return client;
	}
	public static ArrayList<Client> getListOrderByName(String name) 
												throws SisVendaException{
		ArrayList<Client> client =  new ArrayList<Client>();
		
		for(String key :clientMap.keySet()){
			if(clientMap.get(key).getName().contains(name)){
				client.add(clientMap.get(key));				
			}
		}
		
		if (client.size() <= 0){
			throw new SisVendaException("Não Existe nenhum cliente para o nome");
		}else{
				Collections.sort(client, new Comparator<Client>(){
		
				@Override
				public int compare(Client o1, Client o2) {
					return o1.getName().compareTo(o2.getName());
				}
				
			});
				return client;
		}
		
		
		
			
		
		
	}


}
