import java.io.*;

class Personagem{
    private String nome;
    private int altura;
    private double peso;
    private String corDoCabelo;
    private String codDaPele;
    private String corDosOlhos;
    private String anoNascimento;
    private String genero;
    private String homeworld;

    public Personagem (){
        this.setNome("");
        this.setAltura(0);
        this.setPeso(0.0);
        this.setCorDoCabelo("");
        this.setCodDaPele("");
        this.setCorDosOlhos("");
        this.setAnoNascimento("");
        this.setGenero("");
        this.setHomeworld("");

    }
    public Personagem (String nome, int altura, double peso, String corDoCabelo,
        String codDaPele, String corDosOlhos, String anoNascimento, String genero, 
        String homeworld){
        
            this.setNome(nome);
            this.setAltura(altura);
            this.setPeso(peso);
            this.setCorDoCabelo(corDoCabelo);
            this.setCodDaPele(codDaPele);
            this.setCorDosOlhos(corDosOlhos);
            this.setAnoNascimento(anoNascimento);
            this.setGenero(genero);
            this.setHomeworld(homeworld);
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }

    public void setAltura(int altura){
        this.altura = altura;
    }

    public int getAltura(){
        return this.altura;
    }

    public void setPeso(double peso){
        this.peso = peso;
    }

    public double getPeso(){
        return this.peso;
    }

    public void setCorDoCabelo(String corDoCabelo){
        this.corDoCabelo = corDoCabelo;
    }

    public String getCorDoCabelo(){
        return this.corDoCabelo;
    }

    public void setCodDaPele(String codDaPele){
        this.codDaPele = codDaPele;
    }

    public String getCodDaPele(){
        return this.codDaPele;
    }

    public void setCorDosOlhos(String corDosOlhos){
        this.corDosOlhos = corDosOlhos;
    }

    public String getCorDosOlhos(){
        return this.corDosOlhos;
    }

    public void setAnoNascimento(String anoNascimento){
        this.anoNascimento = anoNascimento;
    }

    public String getAnoNascimento(){
        return this.anoNascimento;
    }

    public void setGenero(String genero){
        this.genero = genero;
    }

    public String getGenero(){
        return this.genero;
    }

    public void setHomeworld(String homeworld){
        this.homeworld = homeworld;
    }

    public String getHomeworld(){
        return this.homeworld;
    }

    public static String ISO88591toUTF8(String strISO) throws Exception {
        byte[] isoBytes = strISO.getBytes("ISO-8859-1");
		return new String(isoBytes, "UTF-8");
    }

    public void lerPersonagem(String nomeArquivo) throws Exception{
        try{
            FileReader arq = new FileReader(ISO88591toUTF8(nomeArquivo));
            BufferedReader lerArq = new BufferedReader(arq);
    
            String linha = lerArq.readLine();

            while (linha != null) {
                tratarPersonagem(linha);
                
                linha = lerArq.readLine();
            }
    
            arq.close();
        } 
        catch (IOException e){
            System.err.printf("Erro na abertura do arquivo: %s.\n",
            e.getMessage());
        }

    }
    public void tratarPersonagem (String linha){
        String nome = "";
        int altura = 0;
        double peso = 0.0;
        String corDoCabelo = "";
        String codDaPele = "";
        String corDosOlhos = "";
        String anoNascimento = "";
        String genero = "";
        String homeworld = "";
        String aux = "";//String auxiliar para realizaçao dos metodos indexOf e substring
        int a = 0;//a = indexOf da posição do "name", "height", etc, para dar um substring e cortar a linha
        int b = 0;//b = indexOf da posição da primeira apariçao dos caracteres "',", para dar substring na outra parte da linha
        String str = "";//String auxiliar para ajudar a tratar as exeções do peso dos personagens

        if (linha.contains("name")){
            a = linha.indexOf("name") + 8;
            nome = linha.substring(a);
            a = 0;
            b = nome.indexOf("',");
            nome = nome.substring(a, b);
        }
        setNome(nome);



        if (linha.contains("height")){
            a = linha.indexOf("height") + 10;
            aux = linha.substring(a);
            a = 0;
            b = aux.indexOf("',");
            str = aux.substring(a, b);
        }
        if (str.contains("unknown")){
            setAltura(0);
        }
        else{
            altura = Integer.parseInt(str);
            setAltura(altura);
        }



        if (linha.contains("mass")){
            a = linha.indexOf("mass") + 8;
            aux = linha.substring(a);
            a = 0;
            b = aux.indexOf("',");
            str = aux.substring(a, b);
        }

        if (str.contains(".")){
            int inteiro = 0;
            int frac = 0;
            String q = "";//q & w = 2 strings auxiliares para se caso tiver um "."
            String w = "";//Exemplo: 21.456, q = 21 e w = 456, assim posso particionar o inteiro da fraçao e tirar o "."
            int pos = 0;

            for(int i = 0; i < str.length(); i++){
                if(str.charAt(i) == '.'){
                    pos = i;
                    i = str.length();
                }
                else{
                    q = q + str.charAt(i);//q = parte inteira
                }
            }

            for(int i = pos+1; i < str.length(); i++){
                w = w + str.charAt(i);//w = parte fracionaria
            }
            inteiro = Integer.parseInt(q);
            frac = Integer.parseInt(w);

            if(w.length() == 1){
                peso = inteiro + frac * 0.1;
            }
            else if(w.length() == 2){
                peso = inteiro + frac * 0.01;
            }
            else if(w.length() == 3){
                peso = inteiro + frac * 0.001;
            }

            setPeso(peso);
        }
        else if (str.contains(",")){
            str = str.replace(",", "");
            setPeso(Integer.parseInt(str));
        }
        else{
            if(str.contains("unknown")){
                setPeso(0.0);
            }
            else{
                setPeso(Integer.parseInt(str));
            }
        }
        


        if (linha.contains("hair_color")){
            a = linha.indexOf("hair_color") + 14;
            corDoCabelo = linha.substring(a);
            a = 0;
            b = corDoCabelo.indexOf("',");
            corDoCabelo = corDoCabelo.substring(a, b);
        }
        setCorDoCabelo(corDoCabelo);



        if (linha.contains("skin_color")){
            a = linha.indexOf("skin_color") + 14;
            codDaPele = linha.substring(a);
            a = 0;
            b = codDaPele.indexOf("',");
            codDaPele = codDaPele.substring(a, b);
        }
        setCodDaPele(codDaPele);



        if (linha.contains("eye_color")){
            a = linha.indexOf("eye_color") + 13;
            corDosOlhos = linha.substring(a);
            a = 0;
            b = corDosOlhos.indexOf("',");
            corDosOlhos = corDosOlhos.substring(a, b);
        }
        setCorDosOlhos(corDosOlhos);



        if (linha.contains("birth_year")){
            a = linha.indexOf("birth_year") + 14;
            anoNascimento = linha.substring(a);
            a = 0;
            b = anoNascimento.indexOf("',");
            anoNascimento = anoNascimento.substring(a, b);
        }
        setAnoNascimento(anoNascimento);



        if (linha.contains("gender")){
            a = linha.indexOf("gender") + 10;
            genero = linha.substring(a);
            a = 0;
            b = genero.indexOf("',");
            genero = genero.substring(a, b);
        }
        setGenero(genero);



        if (linha.contains("homeworld")){
            a = linha.indexOf("homeworld") + 13;
            homeworld = linha.substring(a);
            a = 0;
            b = homeworld.indexOf("',");
            homeworld = homeworld.substring(a, b);
        }
        setHomeworld(homeworld);
        
    }

    public void imprimirPersonagem(){
        
        double peso = getPeso();
        int inteiro = (int)getPeso();
        double aux = peso - inteiro;
        //nao printar a parte fracionaria caso ela seja igual a 0
        
        if (aux == 0.0){
            System.out.println(" ## " + getNome() + " ## " + getAltura() + " ## " + (int)getPeso() + " ## " +
                getCorDoCabelo() + " ## " + getCodDaPele() + " ## " + getCorDosOlhos() + " ## " +
                getAnoNascimento() + " ## " + getGenero() + " ## " + getHomeworld() + " ## ");
        }
        else{
            System.out.println(" ## " + getNome() + " ## " + getAltura() + " ## " + getPeso() + " ## " +
                getCorDoCabelo() + " ## " + getCodDaPele() + " ## " + getCorDosOlhos() + " ## " +
                getAnoNascimento() + " ## " + getGenero() + " ## " + getHomeworld() + " ## ");
        }
    }

    public Personagem clone (){
        return new Personagem (this.nome, this.altura, this.peso, this.corDoCabelo,
        this.codDaPele, this.corDosOlhos, this.anoNascimento, this.genero, 
        this.homeworld);
    }

    public String nome(Personagem p){
       return p.nome;
    }
}

class No{
    public boolean cor;
    public Personagem elemento;
    public No esq, dir;
    public No (){
        this(null);
    }
    public No (Personagem elemento){
        this(elemento, false, null, null);
    }
    public No (Personagem elemento, boolean cor){
        this(elemento, cor, null, null);
    }
    public No (Personagem elemento, boolean cor, No esq, No dir){
        this.cor = cor;
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

public class ArvoreAlvinegra{
    private No raiz;
    
	public ArvoreAlvinegra(){
		raiz = null;
    }

    public boolean pesquisar(String x){
        System.out.print("raiz ");
		return pesquisar(x, raiz);
	}
	private boolean pesquisar(String x, No i){
        boolean resp;
		if (i == null) {
            resp = false;
        } 
        else if (x.equals(i.elemento.nome(i.elemento))){
            resp = true;
        } 
        else if (x.compareTo(i.elemento.nome(i.elemento)) < 0){
            System.out.print("esq ");
            resp = pesquisar(x, i.esq);
        } 
        else {
            System.out.print("dir ");
            resp = pesquisar(x, i.dir);
        }
        return resp;
    }

    private No rotacaoDir(No no) {
        No noEsq = no.esq;
        No noEsqDir = noEsq.dir;
  
        noEsq.dir = no;
        no.esq = noEsqDir;
  
        return noEsq;
    }
  
    private No rotacaoEsq(No no){
        No noDir = no.dir;
        No noDirEsq = noDir.esq;
  
        noDir.esq = no;
        no.dir = noDirEsq;
        return noDir;
    }
  
    private No rotacaoDirEsq(No no){
        no.dir = rotacaoDir(no.dir);
        return rotacaoEsq(no);
    }
  
    private No rotacaoEsqDir(No no){
        no.esq = rotacaoEsq(no.esq);
        return rotacaoDir(no);
    }
    
    private void balancear(No bisavo, No avo, No pai, No i){
        if(pai.cor == true){
            if(pai.elemento.nome(pai.elemento).compareTo(avo.elemento.nome(avo.elemento)) > 0){
                if(i.elemento.nome(i.elemento).compareTo(pai.elemento.nome(pai.elemento)) > 0){
                    avo = rotacaoEsq(avo);
                } 
                else {
                    avo = rotacaoDirEsq(avo);
                }
            } 
            else{
                if(i.elemento.nome(i.elemento).compareTo(pai.elemento.nome(pai.elemento)) < 0){
                    avo = rotacaoDir(avo);
                } 
                else {
                    avo = rotacaoEsqDir(avo);
                }
            }
            if (bisavo == null){
                raiz = avo;
            } 
            else {
                if(avo.elemento.nome(avo.elemento).compareTo(bisavo.elemento.nome(bisavo.elemento)) < 0){
                    bisavo.esq = avo;
                } 
                else {
                    bisavo.dir = avo;
                }
            }
            avo.cor = false;
            avo.esq.cor = avo.dir.cor = true;
        }
    }

    public void inserir(Personagem elemento) throws Exception{
        if(raiz == null){
           raiz = new No(elemento, false);
        } 
        else if (raiz.esq == null && raiz.dir == null){
            if (raiz.elemento.nome(raiz.elemento).compareTo(elemento.nome(elemento)) > 0){
                raiz.esq = new No(elemento, true);
            } 
            else {
                raiz.dir = new No(elemento, true);
            }
        } 
        else if (raiz.esq == null){
            if(raiz.elemento.nome(raiz.elemento).compareTo(elemento.nome(elemento)) > 0){
                raiz.esq = new No(elemento);
            } 
            else if (raiz.dir.elemento.nome(raiz.dir.elemento).compareTo(elemento.nome(elemento)) > 0){
                raiz.esq = new No(raiz.elemento);
                raiz.elemento = elemento;
            } 
            else {
                raiz.esq = new No(raiz.elemento);
                raiz.elemento = raiz.dir.elemento;
                raiz.dir.elemento = elemento;
            }
            raiz.esq.cor = raiz.dir.cor = false;
        } 
        else if (raiz.dir == null){
           
            if(raiz.elemento.nome(raiz.elemento).compareTo(elemento.nome(elemento)) < 0){
                raiz.dir = new No(elemento);
            } 
            else if (raiz.esq.elemento.nome(raiz.esq.elemento).compareTo(elemento.nome(elemento)) < 0){
                raiz.dir = new No(raiz.elemento);
                raiz.elemento = elemento;
            }
            else {
                raiz.dir = new No(raiz.elemento);
                raiz.elemento = raiz.esq.elemento;
                raiz.esq.elemento = elemento;
            }
            raiz.esq.cor = raiz.dir.cor = false;
  
        } 
        else {
            inserir(elemento, null, null, null, raiz);
        }
        raiz.cor = false;
    }

    private void inserir(Personagem elemento, No bisavo, No avo, No pai, No i) throws Exception{
		if (i == null) {
            if(elemento.nome(elemento).compareTo(pai.elemento.nome(pai.elemento)) < 0){
                i = pai.esq = new No(elemento, true);
            } 
            else {
                i = pai.dir = new No(elemento, true);
            }
            if(pai.cor == true){
                balancear(bisavo, avo, pai, i);
            }
        } 
        else{
            if(i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true){
                i.cor = true;
                i.esq.cor = i.dir.cor = false;
                if(i == raiz){
                    i.cor = false;
                }
                else if(pai.cor == true){
                    balancear(bisavo, avo, pai, i);
                }
            }
            if (elemento.nome(elemento).compareTo(i.elemento.nome(i.elemento)) < 0){
                inserir(elemento, avo, pai, i, i.esq);
            } 
            else if (elemento.nome(elemento).compareTo(i.elemento.nome(i.elemento)) > 0){
                inserir(elemento, avo, pai, i, i.dir);
            } 
            else {
                throw new Exception("ERRO!");
            }
        }
	}

    public static void main(String[] args) throws Exception{
        ArvoreAlvinegra arvore = new ArvoreAlvinegra();
        String nomeArquivo = "";
        String nome = "";
        int aux = 0;

        while (aux < 1){
            nomeArquivo = MyIO.readLine();

            if (nomeArquivo.charAt(0) == 'F' && nomeArquivo.charAt(1) == 'I' && nomeArquivo.charAt(2) == 'M'){
                aux = 1;
            }
            else{
                Personagem personagem = new Personagem();
                personagem.lerPersonagem(nomeArquivo);
                arvore.inserir(personagem);
            }
        }
        aux = 0;
        while (aux < 1){
            nome = MyIO.readLine();

            if (nome.charAt(0) == 'F' && nome.charAt(1) == 'I' && nome.charAt(2) == 'M'){
                aux = 1;
            }
            else{
                System.out.print(nome + " ");
                if(arvore.pesquisar(nome)){
                    System.out.print("SIM");
                    System.out.println("");
                }
                else{
                    System.out.print("N" + (char)195 + "O");
                    System.out.println("");
                }
            }
        }
    }
}
