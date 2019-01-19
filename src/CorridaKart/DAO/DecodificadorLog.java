package CorridaKart.DAO;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

class DecodificadorLog {
    
    String[][] tabela;
    int qtdLinha;
    int qtdItems;
    
    public void decodifica(){
        try { 
            
            FileInputStream log= new FileInputStream(".\\src\\LogsCorrida\\log.txt");
            InputStreamReader fileread= new InputStreamReader(log);
            BufferedReader br=new BufferedReader(fileread);
            
            String[] palavras;
            String frase="";
            String linha;
            char[] letra;
            int qtdLinhas=0;
            int qtdItems=0;
            int countItems=0;
            boolean espaco=false;
            int pos=0;
            linha=br.readLine();
            boolean run=true;
            
            do{     
                linha=br.readLine();
                qtdLinhas+=1;
                if(linha!=null){
                    letra =linha.toCharArray();
                    try {
                        for(int i=0;i<=letra.length;i++){
                            if(letra[i]!=' ' && letra[i]!='	' && letra[i]!='�'){
                                if(i==0){
                                    qtdItems+=1;
                                }
                                frase+=letra[i];
                                espaco=false;
                            }
                            else if(espaco==false){
                                if(letra[i]==' ' || letra[i]=='	' || letra[i]=='�'){
                                    qtdItems+=1;
                                    frase+="#";
                                    espaco=true;
                                }

                            }
                        }
                    } catch (Exception e) {
                        frase+="#";
                    }
                }
                
            }while(linha!=null);
            
            qtdLinhas-=1;
            this.qtdLinha=qtdLinhas;
            this.qtdItems=qtdItems;
            letra=frase.toCharArray();
            palavras= new String[qtdItems];
            this.tabela=new String[qtdLinhas][qtdItems/qtdLinhas];
            
            
            
            for(int i=0; i<letra.length;i++){
                if(letra[i]!='#'){
                    if(palavras[countItems]==null){
                        palavras[countItems]="";
                    }
                    palavras[countItems]+=letra[i];
                }
                else if(letra[i]=='#'){
                    countItems+=1;
                }
            }
            
            countItems=0;
            for(int i=0;i<qtdLinhas;i++){
                for(int j=0;j<(qtdItems/qtdLinhas);j++){
                    
                    this.tabela[i][j]=palavras[countItems];
                    countItems+=1;
                }
            }
            
            
                
            
            
            
        } catch (Exception e) {
            
            System.out.println(e);
            
        }
    }
}
