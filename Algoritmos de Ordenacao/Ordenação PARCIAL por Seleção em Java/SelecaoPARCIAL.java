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
        String aux = "";//String auxiliar para realiza??ao dos metodos indexOf e substring
        int a = 0;//a = indexOf da posi????o do "name", "height", etc, para dar um substring e cortar a linha
        int b = 0;//b = indexOf da posi????o da primeira apari??ao dos caracteres "',", para dar substring na outra parte da linha
        String str = "";//String auxiliar para ajudar a tratar as exe????es do peso dos personagens

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
            String w = "";//Exemplo: 21.456, q = 21 e w = 456, assim posso particionar o inteiro da fra??ao e tirar o "."
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

    public String pegarNomes(Personagem personagem){
        return this.nome;
    }
}

class SelecaoPARCIAL{
    private Personagem[] array;
    private String[] arrayNome;
    private int n;
    private int k;

    public SelecaoPARCIAL (int tamanho){
        this.array = new Personagem[tamanho];
        this.arrayNome = new String[50];
        this.n = 0;
        this.k = 10;
    }

    public void inserirFim(Personagem personagem) throws Exception{
        if(n >= array.length) throw new Exception("ERRO!");

        array[n] = personagem;
        n++;
    }

    public void mostrar(){
        for(int i = 0 ; i < k; i++){
            this.array[i].imprimirPersonagem();
            System.out.println("");
        }
        System.out.println(" ");
    }

    public void construirArrayNome(SelecaoPARCIAL lista){
        for(int i = 0; i < lista.n; i++){
            Personagem personagem = lista.array[i];
            lista.arrayNome[i] = lista.array[i].pegarNomes(personagem);
        }
    }

    public void selecaoParcial(SelecaoPARCIAL lista){
        int i, j, min;
        for(i = 0; i < lista.k; i++){
            min = i;
            for(j = i+1; j < lista.n; j++){
                if(lista.arrayNome[j].compareTo(lista.arrayNome[min]) < 0){
                    min = j;
                }
            }
            lista.swap(min, i, lista);
        }
    }

    public void swap(int a, int b, SelecaoPARCIAL lista){
        Personagem tmp;
        String nomeTmp;

        tmp = lista.array[a];
        lista.array[a] = lista.array[b];
        lista.array[b] = tmp;

        nomeTmp = lista.arrayNome[a];
        lista.arrayNome[a] = lista.arrayNome[b];
        lista.arrayNome[b] = nomeTmp;
        
    }

    public static void main (String[] args) throws Exception{
        String nomeArquivo = "";
        int aux = 0;
        SelecaoPARCIAL lista = new SelecaoPARCIAL(1000);

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
        lista.construirArrayNome(lista);
        lista.selecaoParcial(lista);
        lista.mostrar();
    }
}