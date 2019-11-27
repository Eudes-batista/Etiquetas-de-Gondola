package tiquetas.teste;

import etiquetas.controle.ProdutoControle;
import etiquetas.modelo.Produto;
import java.util.List;

public class ProdutoTeste {

    public static void main(String[] args) {
        
        
        ProdutoControle produtoControle = new ProdutoControle();
        
        List<Produto> listarProdutos = produtoControle.listarProdutos("","COM");
        listarProdutos.forEach(System.out::println);
        
        Produto produto = produtoControle.buscarProduto("243","COM");
        System.out.println("produto = " + produto);
    }
    
}
