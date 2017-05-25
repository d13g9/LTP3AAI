package Cadastro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import Dados.Client;
import Dados.Produto;
import errors.SisVendaException;

public class cadProdutos {
	public static HashMap<Integer,Produto> produtosMap  = new HashMap<>();
	
	public static void  insertProduct(Produto produto){
		produtosMap.put(produto.getCodigo(), produto);
	}
	
	public static void deleteProduct(Produto produto){
		produtosMap.remove(produto.getCodigo());
	}
	public static Produto searchByCode(Integer codigo) throws SisVendaException{
		Produto produto =  produtosMap.get(codigo);
		
		if(produto == null){
			throw new SisVendaException("There is no product with this code"
																	+codigo);
		}
		
		return produto;
	}
	public static ArrayList<Produto> getListOrderByName(String name) 
												throws SisVendaException{
		ArrayList<Produto> produtoList =  new ArrayList<Produto>();
		
		for(Integer key :produtosMap.keySet()){
			if(produtosMap.get(key).getNome().contains(name)){
				produtoList.add(produtosMap.get(key));				
			}
		}
		
		if (produtoList.size() <= 0){
			throw new SisVendaException("Não Existe nenhum produto para o nome"
										+name);
		}else{
				Collections.sort(produtoList, new Comparator<Produto>(){
					
				@Override
				public int compare(Produto arg0, Produto arg1) {
					return arg0.getNome().compareTo(arg1.getNome());
				}
				
			});
				return produtoList;
		}
		
		
		
			
		
		
	}

}
