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
                getAnoNascimento() + " ## " + getGenero() + " ## " + getHomeworld() + " ##");
        }
        else{
            System.out.print (" ## " + getNome() + " ## " + getAltura() + " ## " + getPeso() + " ## " +
                getCorDoCabelo() + " ## " + getCodDaPele() + " ## " + getCorDosOlhos() + " ## " +
                getAnoNascimento() + " ## " + getGenero() + " ## " + getHomeworld() + " ##");
        }
    }

    public Personagem clone (){
        return new Personagem (this.nome, this.altura, this.peso, this.corDoCabelo,
        this.codDaPele, this.corDosOlhos, this.anoNascimento, this.genero, 
        this.homeworld);
    }

    public String pegarCabelo(final Personagem personagem){
        return this.corDoCabelo;
    }

    public String pegarNome(final Personagem personagem){
        return this.nome;
    }
}

class QuicksortPARCIAL{
    private Personagem[] array;
    private String[] arrayCabelo;
    private String[] arrayNome;
    private int n;
    private int k;

    public QuicksortPARCIAL (final int tamanho){
        this.array = new Personagem[tamanho];
        this.n = 0;
        this.k = 10;
        this.arrayCabelo = new String[50];
        this.arrayNome = new String[50];
    }

    public void inserirFim(final Personagem personagem) throws Exception{
        if(n >= array.length) throw new Exception("ERRO!");

        array[n] = personagem;
        n++;
    }

    public void mostrar(){
        for(int i = 0 ; i < k; i++){
            this.array[i].imprimirPersonagem();
            System.out.println("");
        }
    }

    public void construirArrayCabelo(QuicksortPARCIAL lista){
        for(int i = 0; i < lista.n; i++){
            lista.arrayCabelo[i] = lista.array[i].pegarCabelo(lista.array[i]);
        }
    }

    public void construirArrayNome(QuicksortPARCIAL lista){
        for(int i = 0; i < lista.n; i++){
            lista.arrayNome[i] = lista.array[i].pegarNome(lista.array[i]);
        }
    }

    public void swap(int a,int b,QuicksortPARCIAL lista){
        Personagem tmp;
        String cabeloTmp;
        String nomeTmp;

        tmp = lista.array[a];
        lista.array[a] = lista.array[b];
        lista.array[b] = tmp;

        cabeloTmp = lista.arrayCabelo[a];
        lista.arrayCabelo[a] = lista.arrayCabelo[b];
        lista.arrayCabelo[b] = cabeloTmp;

        nomeTmp = lista.arrayNome[a];
        lista.arrayNome[a] = lista.arrayNome[b];
        lista.arrayNome[b] = nomeTmp;
        
    }

    public void QuickSortParcial(int esq, int dir, QuicksortPARCIAL lista){//quicksort parcial
        int i = esq;
        int j = dir;
        String pivo = lista.arrayCabelo[(dir+esq)/2];
    
        while(i <= j){
            while (lista.arrayCabelo[i].compareTo(pivo) < 0){
                i++;
            }
            while (lista.arrayCabelo[j].compareTo(pivo) > 0){
                j--;
            }
            if (i <= j){
                lista.swap(i, j, lista);
                i++;
                j--;
            }
        }
        if (i - esq >= lista.k - 1){ 
            if (esq < j){ 
                lista.QuickSortParcial(esq, j, lista);
                return;
            }
        }
        if (esq < j){
            lista.QuickSortParcial(esq, j, lista);
        }
        if (i < dir){
            lista.QuickSortParcial(i, dir, lista);
        }
    }

    public void QuickSort(int esq, int dir, QuicksortPARCIAL lista){//quicksort normal
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

    public void verificarCabelosIguais(QuicksortPARCIAL lista){
        int inicio = 0;
        int fim = 0;
        int i = 0;
        int aux = 0;
    
        while (aux < 1){
            if (lista.arrayCabelo[i].equals(lista.arrayCabelo[i+1])){
                inicio = i;
                while (lista.arrayCabelo[i].equals(lista.arrayCabelo[i+1])){
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
        QuicksortPARCIAL lista = new QuicksortPARCIAL(100);

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
        //constroi um array so com os cabelos]
        lista.construirArrayCabelo(lista);
        //ordena parcialmente atraves do quicksort parcial
        lista.QuickSortParcial(0, lista.n-1, lista);
        //constroi denovo o array com os nomes agora ja ordenado atraves dos cabelos
        lista.construirArrayNome(lista);
        //verifica os cabelos iguais e ordena atraves do quicksort verificando o atributo "nome"
        lista.verificarCabelosIguais(lista);
        lista.mostrar();
    }
}