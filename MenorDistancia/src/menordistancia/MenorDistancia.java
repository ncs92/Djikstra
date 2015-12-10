package menordistancia;

import classe.Aresta;
import classe.Grafo;
import classe.Vertice;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenorDistancia {

    static ArrayList<Vertice> verticesCriados = new ArrayList<>();
    static Grafo grafo = new Grafo();
    static ArrayList<Vertice> menorCaminho = new ArrayList<>();
    static ArrayList<Vertice> verticesAbertos = new ArrayList<>(); // lista dos vertices que tao aberto e nao sao infinito    

    public static void main(String[] args) throws IOException {
        ler();
        //grafo.printGrafo();
       
        System.out.println("\n Quantidade de Vertices :" + grafo.getVerticesGrafo().size());
        System.out.println("-------------------");
      
       // dijkstra(v1, v2);
        dkstra(1);
        achaCaminho(1,2017);
        int soma = 0;
       for(int i =menorCaminho.size()-1 ; i >= 0; i--){
            System.out.println("\n "+menorCaminho.get(i).getNumero());
           
        } 
        System.out.println("\n Distancia :"+menorCaminho.get(0).getPeso());
    }

    private static void ler() throws IOException {

        File f = new File("teste.txt");
        FileInputStream fis = new FileInputStream(f);
        FileChannel fc = fis.getChannel();
    
        MappedByteBuffer mmb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        int cont  = 0;
        byte[] buffer = new byte[(int) fc.size()];
        mmb.get(buffer);

        fis.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer)));

        for (String line = in.readLine(); line != null; line = in.readLine()) {
            insereGrafo(line);
            //  System.out.println("\n"+line);
      
       
        }

        in.close();
    }

    private static void insereGrafo(String linha) {

        Vertice v = null;
        Vertice u = null;
        Aresta a = null;
        String[] linhaAtual = linha.split(" ");
        if (grafo.getVertice(Integer.parseInt(linhaAtual[0])) == null) { //se o vertice não existe
            v = new Vertice();
            v.setNumero(Integer.parseInt(linhaAtual[0]));
            grafo.adicionaVertice(v);

        } else { //se o vertice já existe
            v = grafo.getVertice(Integer.parseInt(linhaAtual[0]));
        }

        if (grafo.getVertice(Integer.parseInt(linhaAtual[1])) == null) {
            u = new Vertice();
            u.setNumero(Integer.parseInt(linhaAtual[1]));
            grafo.adicionaVertice(u);
//                System.out.println(" numero u " + u.getNumero());

        } else {
            u = grafo.getVertice(Integer.parseInt(linhaAtual[1]));
        }
        a = new Aresta();
        a.setV1(v);
        a.setV2(u);
        a.setPeso(Integer.parseInt(linhaAtual[2]));
        v.setPeso(-1);
      //  System.out.println("\n"+a.toString());
        grafo.adicionaArestaPorVertice(v, a);

//            return;
    }
    
       static void achaCaminho(int inicio, int fim){
        Vertice atual = new Vertice();
        ArrayList<Vertice> v = new ArrayList<>();
        atual = grafo.getVertice(fim);
       if(atual.getPai()!= null){
          
        while(!atual.equals(grafo.getVertice(inicio))){
            menorCaminho.add(atual);
            atual = atual.getPai();
        }
       }
         
    }

    private static void dkstra(int inicio){
        Vertice origem = grafo.getVertice(inicio);
      
       System.out.println("\n inicio "+ grafo.getVertice(inicio));
        grafo.getVertice(inicio).setPeso(0);
        grafo.getVertice(inicio).setStatus(false);
        Vertice priori = new Vertice(); 
        ArrayList<Vertice> abertos = new ArrayList<>();
        ArrayList<Vertice> fechados = new ArrayList<>();
        ArrayList<Aresta> adjacente = new ArrayList<>();
        grafo.getVertice(inicio).setPai(null);
        priori = origem;     
        fechados.add(priori);
   //     priori.setPeso(0);
        while(fechados.size()!= grafo.getVerticesGrafo().size()){ //repita enquanto houver vertice aberto
            ////////////relaxo as adjacentes/////////////
            
            adjacente = grafo.getListaArestasAdjacentes(priori);
            for (Aresta v : adjacente) {
                if (v.getV2().isStatus()) {
                    if (v.getV2().getPeso() == -1) {
                        v.getV2().setPeso(priori.getPeso() + v.getPeso());
                        grafo.getVertice(v.getV2().getNumero()).setPai(priori);
                        abertos.add(v.getV2());
                    } else if (v.getV2().getPeso() > (priori.getPeso() + v.getPeso())) {
                        v.getV2().setPeso(priori.getPeso() + v.getPeso());
                        grafo.getVertice(v.getV2().getNumero()).setPai(priori);
                     //   abertos.add(v);
                    }
                }
            }
            ///////////////verifico a adjacente com menor peso //////////////
            Vertice proximo = new Vertice(); // proximo equivale ao menor
            proximo.setPeso(999999999);
            
            for(Vertice p : abertos){
                if(p.getPeso() < proximo.getPeso()){
                    proximo = p;                   
                }
            }
         
            if(grafo.getVerticesGrafo().contains(proximo)){
            grafo.getVertice(proximo.getNumero()).setStatus(false);
            fechados.add(proximo); // adiciono na lista de fechados e removo da dos abertos
                
            }else{
                break;
            }
       
            for(Vertice p: abertos){
                if(p.equals(proximo)){                  
                    abertos.remove(p);
                    break;
                }
            }       
           priori = proximo;
        }
    
        
    }
    
}
