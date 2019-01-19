/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CorridaKart.DAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author User
 */
public class CorridaDAO extends DecodificadorLog {
    
    
    String[][] numeros;
    String volta;
    int posVencedor;
    int qtdCorredoresFinalizaram=0;
    private int posVoltasInicial=0;
    String[][] corredoresFinalizaram;
    String[][] corredoresNaoFinalizaram;
    String[][] tempos;
    
    public String[][] classificacaoGeral;
    
    private void verificaQtdVoltas(){
        int v1,v2,maior=0;
        for(int i=0;i<(this.qtdLinha)-1;i++){
            
            v1=Integer.parseInt(this.tabela[i][3]);
            v2=Integer.parseInt(this.tabela[i+1][3]);
            if(i==0){
               maior=v1;
            }
            if(maior<v1){
                if(v2<v1){
                        
                this.qtdCorredoresFinalizaram=1;
                maior=v1;
                }
            }
            else if(maior<v2){
                if(v1<v2){
                        
                this.qtdCorredoresFinalizaram=1;
                maior=v2;
                }
            }
            else if(v1==v2){
                if(v1==maior){
                   qtdCorredoresFinalizaram+=1;
                
                    
            }
                
        }                      
    }
        this.volta=String.valueOf(maior);
        
    }
    
    public long tempoFormatado(String tempo){
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
    Date data = null;
    try {
        data = sdf.parse(tempo);
    } catch (ParseException e) {
        e.printStackTrace();
    }
    long tempoFormatado =data.getTime();
    
    return tempoFormatado;
    }
    
    public long tempoFormatadoMinuto(String tempo){
    SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS");
    Date data = null;
    try {
        data = sdf.parse(tempo);
    } catch (ParseException e) {
        e.printStackTrace();
    }
    long tempoFormatado =data.getTime();
    
    return tempoFormatado;
    }
    
    public String unixTimeParaString(long segundosUnix){   
        SimpleDateFormat sdf;
        Date data = new java.util.Date(segundosUnix); 
        if(segundosUnix<=3600000){
           sdf = new java.text.SimpleDateFormat("mm:ss.SSS"); 
        }
        else{
           sdf = new java.text.SimpleDateFormat("HH:mm:ss.SSS");
        }
        
        String tempoFormatado = sdf.format(data);
        
        return tempoFormatado;
        
    }
       
    private void verificaVencedor(){
        
        int countLinha=0;
        this.verificaQtdVoltas();
        long menortempo=0;
        String[][] temp;
        this.corredoresFinalizaram=new String[qtdCorredoresFinalizaram][this.qtdItems/this.qtdLinha];

        for(int i=0;i<this.qtdLinha;i++){
            
                
            if(this.tabela[i][3].equals(volta)){
                
                
                
                for(int j=0;j<this.qtdItems/this.qtdLinha;j++){
                    
                    this.corredoresFinalizaram[countLinha][j]=this.tabela[i][j];
                    
                }
                
                countLinha+=1;
                
                
            }
        }
        

        
        temp=new String[this.corredoresFinalizaram.length][this.qtdItems/this.qtdLinha];
        String temp3;

       
        String aux;
        long temp1,temp2;
        
		for(int i = 0; i < this.corredoresFinalizaram.length; i++) {
			for(int linha = 0; linha <this.corredoresFinalizaram.length; linha++) {
                            temp1=this.tempoFormatado(this.corredoresFinalizaram[i][0]);
                            temp2=this.tempoFormatado(this.corredoresFinalizaram[linha][0]);
                            if (temp1<temp2){
                                for(int j=0;j<this.qtdItems/qtdLinha;j++){
                                                        
                                    aux = this.corredoresFinalizaram[linha][j];
                                    this.corredoresFinalizaram[linha][j] = this.corredoresFinalizaram[i][j];
                                    this.corredoresFinalizaram[i][j] = aux;
                                                        
                                }
							
                            }
                            
                    }
				
		}
        
    
    }
    
    private void corredoresNaoFinalizou(){
        
        ArrayList<String[]> corredores=new ArrayList<>();
        boolean existe=false;
        String[][] corredor=new String[1][this.qtdItems/qtdLinha];
        int igual=0;
        int poslinha=0;
        
        for(int i=this.tabela.length-1;i>=0;i--){
            igual=0;
            for(int linha=this.corredoresFinalizaram.length-1;linha>=0;linha--){
                existe=false;
                
                
                
                if(this.tabela[i][1].equals(this.corredoresFinalizaram[linha][1])){
                    igual+=1;
                    }
                
                        
                    }

            if(igual==0){
                        if(corredores.size()>0){
                        
                        for(int linhalista=0;linhalista<corredores.size();linhalista++){
                        
                            if(this.tabela[i][1].equals(corredores.get(linhalista)[1])){
                                
                                existe=true;
                                
                                }
                        
                            }    
                        }
                        if(existe==false){
                            for(int coluna=0;coluna<this.qtdItems/this.qtdLinha;coluna++){
                                corredor[0][coluna]=this.tabela[i][coluna];
                            }
                            corredores.add(poslinha,corredor[0]);
                            poslinha+=1;
                        }
                    
            }
               
        }
        
        
        
        
        this.corredoresNaoFinalizaram=new String[corredores.size()][this.qtdItems/this.qtdLinha];
        
        for(int i=0;i<this.corredoresNaoFinalizaram.length;i++){
            for(int j=0;j<this.qtdItems/qtdLinha;j++){
                this.corredoresNaoFinalizaram[i][j]=corredores.get(0)[j];
            }
        }
        
        
        
        
    }
    
    private void pegaTempos(){
        int qtdCorredores=this.corredoresFinalizaram.length+this.corredoresNaoFinalizaram.length;
        int volta=Integer.parseInt(this.volta);
        long[] tempo=new long[volta];
        int igual=0;
        int poslinha=0;
        int colunaVetor=0;
        boolean existe=false;
        this.tempos=new String[qtdCorredores][volta+1];
        String[][] corredor=new String[1][volta+1];
        
        
        
        
        for(int i=0;i<tabela.length;i++){
            existe=false;
            colunaVetor=0;
            for(int zerar=0;zerar<volta;zerar++){
                tempo[zerar]=0;
            }
            
            for(int linha=0;linha<tabela.length;linha++){
                
                if(this.tabela[i][1].equals(this.tabela[linha][1])){
                    
                    tempo[colunaVetor]=tempoFormatado(tabela[linha][0]);
                    colunaVetor+=1;
                     
                }
      
            }
            if(poslinha>0){
                
                        for(int listLinha=0;listLinha<poslinha;listLinha++){
                           
                            if(this.tabela[i][1].equals(this.tempos[listLinha][0])){
                                
                                existe=true;
                            }
                        }
                    }
            if(existe==false){
                this.tempos[poslinha][0]=this.tabela[i][1];
                for (int j=1;j<volta+1;j++){;
                    this.tempos[poslinha][j]=""+tempo[j-1];
                }
             poslinha+=1;
                
                    
            }
            
        }
        
    
    }
    
    public String[][] pegaValorNumericoTabela(int posIdentificador,int posColunaTabela){
        
        int qtdCorredores=this.corredoresFinalizaram.length+this.corredoresNaoFinalizaram.length;
        int volta=Integer.parseInt(this.volta);
        String[] valor=new String[volta];
        int igual=0;
        int poslinha=0;
        int colunaVetor=0;
        boolean existe=false;
        String[][] valores=new String[qtdCorredores][volta+1];
        String[][] corredor=new String[1][volta+1];
        
        
        
        
        for(int i=0;i<tabela.length;i++){
            existe=false;
            colunaVetor=0;
            for(int zerar=0;zerar<volta;zerar++){
                valor[zerar]="0";
            }
            
            for(int linha=0;linha<tabela.length;linha++){
                
                if(this.tabela[i][posIdentificador].equals(this.tabela[linha][posIdentificador])){
                    
                    valor[colunaVetor]=tabela[linha][posColunaTabela];
                    colunaVetor+=1;
                    
                    
                    
                }
      
            }
            if(poslinha>0){
                
                        for(int listLinha=0;listLinha<poslinha;listLinha++){
                            if(this.tabela[i][posIdentificador].equals(valores[listLinha][0])){
                                
                                existe=true;
                            }
                        }
                    }
            if(existe==false){
                valores[poslinha][0]=this.tabela[i][posIdentificador];
                for (int j=1;j<volta+1;j++){;
                    valores[poslinha][j]=""+valor[j-1];
                }
             poslinha+=1;
            }
        }

        return valores;
        
    }
    
    private String[][] calculaVelocidadeMedia(){
        int volta=Integer.parseInt(this.volta);
        int descontoVolta=0;
        String auxMedia;
        float media=0;
        String[][] valores=this.pegaValorNumericoTabela(1, 5);
        String[][] velocidadeMedia=new String[valores.length][volta+2];
        
        for(int i=0;i<valores.length;i++){
            media=0;
            descontoVolta=0;
            for(int j=0;j<volta+1;j++){
                if(j==0){
                    velocidadeMedia[i][j]=valores[i][j];
                }
                else{
                    velocidadeMedia[i][j]=valores[i][j];
                    auxMedia=valores[i][j].replace(",",".");
                    media+=Float.parseFloat(auxMedia);
                    if(valores[i][j].equals("0")){
                        descontoVolta+=1;
                    }
                }
            }
            media=media/(volta-descontoVolta);
            velocidadeMedia[i][volta+1]=""+media;
            
        }
       
            
        return velocidadeMedia;
    }
    
    public void menorTempo(){
        int volta=Integer.parseInt(this.volta);
        String[][] voltasTempo=this.pegaValorNumericoTabela(2, 4);
        long menor=tempoFormatadoMinuto(voltasTempo[0][1]);
        String[][] menoresTemposCorredores=new String[voltasTempo.length][2];
        int l=0;
        int c=0;
        long valorTempo=0;
        String aux,aux2;
        for (int i = 0; i< voltasTempo.length; i++){
            long menorTempoCorredores=tempoFormatadoMinuto(voltasTempo[i][1]);
            menoresTemposCorredores[i][0]=voltasTempo[i][0];
            for (int j = 1; j<volta+1; j++){
                aux=voltasTempo[i][j];
                if(!aux.equals("0")){                
                    valorTempo=tempoFormatadoMinuto(aux);
                    if(valorTempo>0){
                        if (valorTempo < menor){
                            menor =valorTempo;
                            l = i;
                            c = j;
                        }
                        if(valorTempo<menorTempoCorredores){
                            menorTempoCorredores=valorTempo;
                            menoresTemposCorredores[i][1]=""+this.unixTimeParaString(menorTempoCorredores);
                            
                        }
                    }
                    
                }
           
            }
        }
        System.out.print("\n \n     A melhor volta é "+ this.unixTimeParaString(menor)+" volta numero "+c+" do corredor "+voltasTempo[l][0]);
        System.out.print("\n \n Menores tempo de cada corredor: ");
        for(int i=0;i<menoresTemposCorredores.length;i++){
            System.out.println(" ");
            for(int j=0;j<2;j++){
                System.out.print(" "+menoresTemposCorredores[i][j]);
            }
        }
        
    }
    
    public void posicaoGeral(){
        
        this.verificaVencedor();
        this.corredoresNaoFinalizou();
        this.pegaTempos();
        int volta=Integer.parseInt(this.volta);
        int campoTempoTotal=1;
        int tamanhoLinha=this.corredoresFinalizaram.length+this.corredoresNaoFinalizaram.length;
        int tamanhoColuna=(this.qtdItems/this.qtdLinha+2);
        String[][] tabelaMesclada=new String[tamanhoLinha][tamanhoColuna+volta+campoTempoTotal];
        int posicao=1;
        int posicaocoluna=0;
        String dataFormatada;
        int colunasClassficacaoGeral=10;
        int posColunaClassificao=0;
        int qtdBonus=2;
        String[][] velocidadesMedia=this.calculaVelocidadeMedia();
        this.classificacaoGeral=new String[tamanhoLinha][colunasClassficacaoGeral+qtdBonus];
        
        for(int i=0;i<this.corredoresFinalizaram.length;i++){
            for(int j=0;j<tamanhoColuna;j++){
                if(j==0){
                    tabelaMesclada[i][j]=posicao+" lugar";
                    posicao+=1;
                }
                else if(j==tamanhoColuna-1){
                    tabelaMesclada[i][j]="finalizou";
                }
                else{
                    tabelaMesclada[i][j]=corredoresFinalizaram[i][j-1];
                }                
            }
        }
        
        for(int i=corredoresFinalizaram.length;i<tabelaMesclada.length;i++){
            for(int j=0;j<tamanhoColuna;j++){
                if(j==0){
                    tabelaMesclada[i][j]=posicao+" lugar";
                    posicao+=1;
                }
                else if(j==tamanhoColuna-1){
                    tabelaMesclada[i][j]="não finalizou";
                }
                else{
                    tabelaMesclada[i][j]=corredoresNaoFinalizaram[i-corredoresFinalizaram.length][j-1];
                }                
            }
        }
        
        for(int i=0;i<tabelaMesclada.length;i++){
            for(int linha=0;linha<this.tempos.length;linha++){
                if(tabelaMesclada[i][2].equals(this.tempos[linha][0])){
                    for(int j=1;j<volta+1;j++){
                        tabelaMesclada[i][(tamanhoColuna+j)-1]=this.tempos[linha][j];
                    }
                }
            }
        }
        
        for(int i=0;i<tabelaMesclada.length;i++){
            posicaocoluna=11;
            for(int j=(tamanhoColuna+volta)-1;j>(tamanhoColuna-volta)+1;j--){
                if(tabelaMesclada[i][j].equals("0")){
                 posicaocoluna-=1;
                 if(posicaocoluna<=tamanhoColuna){
                     posicaocoluna=tamanhoColuna+1;
                 }
                }
                
            }
            long v1=Long.parseLong(tabelaMesclada[i][8]);
            long v2=Long.parseLong(tabelaMesclada[i][posicaocoluna]);
            long t=v2-v1;
            dataFormatada=this.unixTimeParaString(t);
            tabelaMesclada[i][12]=""+dataFormatada;
        }
        

        String aux;
        for(int i=0;i<this.classificacaoGeral.length;i++){
            posColunaClassificao=0;
            for(int j=0;j<tamanhoColuna+volta+campoTempoTotal;j++){
                if(j!=1 && j!=5 && j!=6){
                    if(j>=8 && j<=11){
                        long v1=Long.parseLong(tabelaMesclada[i][j]);
                        aux=this.unixTimeParaString(v1);
                        if(j==11){
                            if(i==0){
                            this.classificacaoGeral[i][colunasClassficacaoGeral+1]=aux;
                             }
                            else if(i>0){
                            long tempoPrimeiroLugar=Long.parseLong(tabelaMesclada[0][j]);
                            this.classificacaoGeral[i][colunasClassficacaoGeral+1]="+ "+this.unixTimeParaString(v1-tempoPrimeiroLugar);
                            }
                        }             
                    }
                    else{
                        aux=tabelaMesclada[i][j];
                    }
                    this.classificacaoGeral[i][posColunaClassificao]=aux;
                    posColunaClassificao+=1;
                }
                
            }
        }
        for(int i=0;i<tabelaMesclada.length;i++){
            for(int linha=0;linha<this.tempos.length;linha++){
                if(tabelaMesclada[i][2].equals(this.tempos[linha][0])){
                    for(int j=1;j<volta+1;j++){
                        tabelaMesclada[i][(tamanhoColuna+j)-1]=this.tempos[linha][j];
                    }
                }
            }
        }
        
        for(int i=0;i<this.classificacaoGeral.length;i++){         
            for(int linha=0;linha<velocidadesMedia.length;linha++){             
                if(this.classificacaoGeral[i][1].equals(velocidadesMedia[linha][0])){
                    this.classificacaoGeral[i][colunasClassficacaoGeral]=velocidadesMedia[linha][5];
                    
                }
            }
        }
        
        
        for(int i=0;i<this.classificacaoGeral.length;i++){
            System.out.println(" ");
            for(int j=0;j<colunasClassficacaoGeral+qtdBonus;j++){
                System.out.print("    "+this.classificacaoGeral[i][j]);
            }
        }

        
        
        
    }
    
}
