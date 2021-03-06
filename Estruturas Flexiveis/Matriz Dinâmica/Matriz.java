class Celula{
    public int elemento;
    public Celula inf, sup, esq, dir;
 
    public Celula(){
        this(0, null, null, null, null);
    }
 
    public Celula(int elemento){
        this(elemento, null, null, null, null);
    }
 
    public Celula(int elemento, Celula inf, Celula sup, Celula esq, Celula dir){
        this.elemento = elemento;
        this.inf = inf;
        this.sup = sup;
        this.esq = esq;
        this.dir = dir;
    }
}
public class Matriz{
    int linha, coluna;
    Celula inicio;

    public Matriz(int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
        int lin, col;
        Celula i, j;
        int x = 1;

        inicio = new Celula(x++);

        for(j = inicio, col = 1; col < coluna; j = j.dir, col++){
            j.dir = new Celula(x++);
            j.dir.esq = j;
        }
        for(i = inicio, lin = 1; lin < linha; i = i.inf, lin++){
            i.inf = new Celula(x++);
            i.inf.sup = i;

            for(j = i.inf, col = 1; col < coluna; j = j.dir, col++){
                j.dir = new Celula(x++);
                j.dir.esq = j;
                j.dir.sup = j.sup.dir;
                j.sup.dir.inf = j.dir;
            }
        }
    }

    public boolean isQuadrada(){
        boolean resp = false;
        if(this.linha == this.coluna){
            resp = true;
        }
        return resp;
    }
    
    public void mostrar(){
        for(Celula i = inicio; i != null; i = i.inf){
            for(Celula j = i; j != null; j = j.dir){
                System.out.print(j.elemento + " ");
            }
            System.out.println("");
        }
    }

    public void mostrarDiagonalPrincipal(){
        if(this.isQuadrada() && this.linha > 0){
            Celula i = inicio;
            do{
                System.out.print(i.elemento + " ");
                i = i.dir;
                if(i != null){
                    i = i.inf;
                }
            }
            while(i != null);
            System.out.println("");
        }
    }

    public void mostrarDiagonalSecundaria(){
        if(this.isQuadrada() && this.linha > 0){
            Celula i, j;
            for(i = inicio; i.dir != null; i = i.dir);
            j = i;
            do{
                System.out.print(j.elemento + " ");
                j = j.esq;
                if(j != null){
                    j = j.inf;
                }
            }
            while(j != null);
            System.out.println("");
        }
    }

    public Matriz soma(Matriz m){
        return soma(this, m);
    }

    public Matriz soma(Matriz a, Matriz b){
        Matriz resp = null;

        if(a.linha == b.linha && a.coluna == b.coluna){
            resp = new Matriz(a.linha, a.coluna);
            
            Celula iResp = resp.inicio;
            Celula iA = a.inicio;
            Celula iB = b.inicio;

            for(int i = 0; i < linha; i++){
                Celula jResp = iResp;
                Celula jA    = iA;
                Celula jB    = iB;

                for(int j = 0; j < coluna; j++){
                    jResp.elemento = jA.elemento + jB.elemento;
                    jResp = jResp.dir;
                    jA = jA.dir;
                    jB = jB.dir;
                }
                iResp = iResp.inf;
                iA = iA.inf;
                iB = iB.inf;
            }
        }
        return resp;
    }

    public Matriz multiplicacao(Matriz m){
        return multiplicacao(this, m);
    }

    public Matriz multiplicacao(Matriz a, Matriz b){
        Matriz resp = null;

        if(a.linha == b.linha && a.coluna == b.coluna){
            resp = new Matriz(a.linha, a.coluna);
            Celula iA, iB, iResp, jResp;
            int i, j;
            
            for(iA = a.inicio, iResp = resp.inicio, i = 0; i < linha; iResp = iResp.inf, iA = iA.inf, i++){
                for(iB = b.inicio, jResp = iResp, j = 0; j < coluna; jResp = jResp.dir, iB = iB.dir, j++){
                    Celula jA = iA;
                    Celula jB = iB;
                    jResp.elemento = 0;
                    for(int k = 0; k < coluna; k++){
                        jResp.elemento = jResp.elemento + (jA.elemento * jB.elemento);
                        jA = jA.dir;
                        jB = jB.inf;
                    }
                }
            }
        }
        return resp;
    }

    public static void main(String[] args){
        int n = MyIO.readInt();

        for(int k = 0; k < n; k++){
            Celula iM, jM;
            int i, j;

            int linha1 = MyIO.readInt();
            int coluna1 = MyIO.readInt();
            Matriz m1 = new Matriz(linha1, coluna1);
            for(iM = m1.inicio, i = 0; i < linha1; iM = iM.inf, i++){
                for(jM = iM, j = 0; j < coluna1; jM = jM.dir, j++){
                    int num = MyIO.readInt();
                    jM.elemento = num;
                }
            }
            int linha2 = MyIO.readInt();
            int coluna2 = MyIO.readInt();
            Matriz m2 = new Matriz(linha2, coluna2);
            for(iM = m2.inicio, i = 0; i < linha2; iM = iM.inf, i++){
                for(jM = iM, j = 0; j < coluna2; jM = jM.dir, j++){
                    int num = MyIO.readInt();
                    jM.elemento = num;
                }
            }
            m1.mostrarDiagonalPrincipal();
            m1.mostrarDiagonalSecundaria();
            m1.soma(m2).mostrar();
            m1.multiplicacao(m1, m2).mostrar();
        }
    }
}