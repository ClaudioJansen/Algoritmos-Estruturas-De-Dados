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
    public Personagem (final String nome, final int altura, final double peso, final String corDoCabelo,
        final String codDaPele, final String corDosOlhos, final String anoNascimento, final String genero, 
        final String homeworld){
        
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
    public void setNome(final String nome){
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }

    public void setAltura(final int altura){
        this.altura = altura;
    }

    public int getAltura(){
        return this.altura;
    }

    public void setPeso(final double peso){
        this.peso = peso;
    }

    public double getPeso(){
        return this.peso;
    }

    public void setCorDoCabelo(final String corDoCabelo){
        this.corDoCabelo = corDoCabelo;
    }

    public String getCorDoCabelo(){
        return this.corDoCabelo;
    }

    public void setCodDaPele(final String codDaPele){
        this.codDaPele = codDaPele;
    }

    public String getCodDaPele(){
        return this.codDaPele;
    }

    public void setCorDosOlhos(final String corDosOlhos){
        this.corDosOlhos = corDosOlhos;
    }

    public String getCorDosOlhos(){
        return this.corDosOlhos;
    }

    public void setAnoNascimento(final String anoNascimento){
        this.anoNascimento = anoNascimento;
    }

    public String getAnoNascimento(){
        return this.anoNascimento;
    }

    public void setGenero(final String genero){
        this.genero = genero;
    }

    public String getGenero(){
        return this.genero;
    }

    public void setHomeworld(final String homeworld){
        this.homeworld = homeworld;
    }

    public String getHomeworld(){
        return this.homeworld;
    }

    public static String ISO88591toUTF8(final String strISO) throws Exception {
        final byte[] isoBytes = strISO.getBytes("ISO-8859-1");
		return new String(isoBytes, "UTF-8");
    }

    public void lerPersonagem(final String nomeArquivo) throws Exception{
        try{
            final FileReader arq = new FileReader(ISO88591toUTF8(nomeArquivo));
            final BufferedReader lerArq = new BufferedReader(arq);
    
            String linha = lerArq.readLine();

            while (linha != null) {
                tratarPersonagem(linha);
                
                linha = lerArq.readLine();
            }
    
            arq.close();
        } 
        catch (final IOException e){
            System.err.printf("Erro na abertura do arquivo: %s.\n",
            e.getMessage());
        }

    }
    public void tratarPersonagem (final String linha){
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
            System.out.print (" ## " + getNome() + " ## " + getAltura() + " ## " + (int)getPeso() + " ## " +
                getCorDoCabelo() + " ## " + getCodDaPele() + " ## " + getCorDosOlhos() + " ## " +
                getAnoNascimento() + " ## " + getGenero() + " ## " + getHomeworld() + " ## ");
        }
        else{
            System.out.print (" ## " + getNome() + " ## " + getAltura() + " ## " + getPeso() + " ## " +
                getCorDoCabelo() + " ## " + getCodDaPele() + " ## " + getCorDosOlhos() + " ## " +
                getAnoNascimento() + " ## " + getGenero() + " ## " + getHomeworld() + " ## ");
        }
    }

    public Personagem clone (){
        return new Personagem (this.nome, this.altura, this.peso, this.corDoCabelo,
        this.codDaPele, this.corDosOlhos, this.anoNascimento, this.genero, 
        this.homeworld);
    }

    public int pegarAlturas(final Personagem personagem){
        return this.altura;
    }
    public String pegarNome(final Personagem personagem){
        return this.nome;
    }
}

class Heapsort{
    private Personagem[] array;
    private int[] arrayAltura;
    private String[] arrayNome;
    private int n;

    public Heapsort (final int tamanho){
        this.array = new Personagem[tamanho];
        this.n = 0;
        this.arrayAltura = new int[50];
        this.arrayNome = new String[50];
    }

    public void inserirFim(final Personagem personagem) throws Exception{
        if(n >= array.length) throw new Exception("ERRO!");

        array[n] = personagem;
        n++;
    }

    public void mostrar(){
        for(int i = 0 ; i < n; i++){
            this.array[i].imprimirPersonagem();
            System.out.println("");
        }
    }

    public void construirArrayAltura(final Heapsort lista){
        for(int i = 0; i < lista.n; i++){
            lista.arrayAltura[i] = lista.array[i].pegarAlturas(lista.array[i]);
        }
    }
    public void construirArrayNome(Heapsort lista){
        for(int i = 0; i < lista.n; i++){
            lista.arrayNome[i] = lista.array[i].pegarNome(lista.array[i]);
        }
    }

    public void swap(final int a, final int b, final Heapsort lista){
        Personagem tmp;
        int alturaTmp;
        String nomeTmp;

        tmp = lista.array[a];
        lista.array[a] = lista.array[b];
        lista.array[b] = tmp;

        nomeTmp = lista.arrayNome[a];
        lista.arrayNome[a] = lista.arrayNome[b];
        lista.arrayNome[b] = nomeTmp;

        alturaTmp = lista.arrayAltura[a];
        lista.arrayAltura[a] = lista.arrayAltura[b];
        lista.arrayAltura[b] = alturaTmp;
        
    }

    public void heapsort(final Heapsort lista){
        int[] tmp = new int[lista.n+1];
        Personagem[] Ptmp = new Personagem[lista.n+1];

        //mudar o array de altura pra posiçao 1
        for(int i = 0; i < lista.n; i++){
            tmp[i+1] = lista.arrayAltura[i];
        }
        lista.arrayAltura = tmp;
        //mudar o array de personagem tambem pra posiçao 1
        for(int i = 0; i < lista.n; i++){
            Ptmp[i+1] = lista.array[i];
        }
        lista.array = Ptmp;
        //construir o heap
        for(int tamHeap = 2; tamHeap <= lista.n; tamHeap++){
            lista.constroi(tamHeap, lista);
        }

        int tamHeap = n;
        //reconstruir o heap
        while(tamHeap > 1){
            lista.swap(1, tamHeap--, lista);
            lista.reconstroi(tamHeap, lista);
        }

        tmp = lista.arrayAltura;
        lista.arrayAltura = new int[lista.n];
        Ptmp = lista.array;
        lista.array = new Personagem[lista.n];
        //voltar com os arrays pra posiçao 0
        for(int i = 0; i < n; i++){
            lista.arrayAltura[i] = tmp[i+1];
        }
        for(int i = 0; i < n; i++){
            lista.array[i] = Ptmp[i+1];
        }
    }
    
    public void constroi(final int tamHeap, final Heapsort lista){
        for(int i = tamHeap; i > 1 && lista.arrayAltura[i] > lista.arrayAltura[i/2]; i /= 2){
            lista.swap(i, i/2, lista);
        }
    }

    public void reconstroi(final int tamHeap, final Heapsort lista){
        int i = 1, filho;
        while(i <= (tamHeap/2)){
  
            if (lista.arrayAltura[2*i] > lista.arrayAltura[2*i+1] || 2*i == tamHeap){
                filho = 2*i;
            } 
            else {
                filho = 2*i + 1;
            }
  
            if(lista.arrayAltura[i] < lista.arrayAltura[filho]){
                lista.swap(i, filho, lista);
                i = filho;
            }
            else{
                i = tamHeap;
            }
        }
    }

    public void QuickSort(int esq, int dir, Heapsort lista){
        int i = esq;
        int j = dir;
        String pivo = "";
        pivo = lista.arrayNome[(dir+esq)/2];
    
        while(i <= j){
            while (lista.arrayNome[i].compareTo(pivo) < 0){
                i++;
            }
            while (lista.arrayNome[j].compareTo(pivo) > 0){
                j--;
            }
            if (i <= j){
                lista.swap(i, j, lista);
                i++;
                j--;
            }
        }
        if (esq < j){
            lista.QuickSort(esq, j, lista);
        }
        if (i < dir){
            lista.QuickSort(i, dir, lista);
        }
    }

    public void verificarAlturasIguais(Heapsort lista){
        int inicio = 0;
        int fim = 0;
        int i = 0;
        int aux = 0;
    
        while (aux < 1){
            if (lista.arrayAltura[i] == lista.arrayAltura[i+1]){
                inicio = i;
                while (lista.arrayAltura[i] == lista.arrayAltura[i+1]){
                    i++;
                }
                fim = i;
                lista.QuickSort(inicio, fim, lista);
            }
            else{
                i++;
            }
            if (i == lista.n-1){
                aux = 1;
            }
        }
    }
    public static void main (String[] args) throws Exception{
        String nomeArquivo = "";
        int aux = 0;
        Heapsort lista = new Heapsort(100);

        while (aux < 1){
            nomeArquivo = MyIO.readLine();

            if (nomeArquivo.charAt(0) == 'F' && nomeArquivo.charAt(1) == 'I' && nomeArquivo.charAt(2) == 'M'){
                aux = 1;
            }
            else{
                Personagem personagem = new Personagem();
                personagem.lerPersonagem(nomeArquivo);
                lista.inserirFim(personagem);
            }
        }
        lista.construirArrayAltura(lista);
        lista.construirArrayNome(lista);
        //ordenar atraves do Heapsort
        lista.heapsort(lista);
        //construir array de nome pra ordenar as alturas iguais atraves do nome
        lista.construirArrayNome(lista);
        //verifica a posicao dos pesos iguais chama o quicksort para ordenar apenas a parte igual
        lista.verificarAlturasIguais(lista);
        lista.mostrar();
    }
}