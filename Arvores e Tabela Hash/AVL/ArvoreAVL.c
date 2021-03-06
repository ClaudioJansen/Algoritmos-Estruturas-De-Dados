#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#define MAX 1000

typedef struct Personagem{
    char nome[1000];
    int altura;
    double peso;
    char corDoCabelo[1000];
    char codDaPele[1000];
    char corDosOlhos[1000];
    char anoNascimento[1000];
    char genero[1000];
    char homeworld[1000];
}Personagem;

typedef struct No {
    Personagem elemento;
	struct No *esq, *dir;
    int nivel;
    
}No;
No* raiz;

No* novoNo(Personagem elemento);
struct Personagem lerPersonagem(char* nomeArquivo);
struct Personagem tratarPersonagem (char* linha, char* nome, char* corDoCabelo, char* codDaPele, char* corDosOlhos,
                                    char* anoNascimento, char* genero, char* homeworld, char* aux, char* str);
void imprimirPersonagem(struct Personagem personagem);
char* str_substring(char str[], int start, int end);
char* str_replace(char* string, const char* substr, const char* replacement);
bool pesquisar2(No *no, char *x);

struct Personagem lerPersonagem(char* nomeArquivo){
    char* linha = (char *) malloc(sizeof(char) * 10000);
    FILE *file = fopen(nomeArquivo, "rt");
    size_t len = 0;
    char* arquivo = (char *) malloc(sizeof(char) * 1000);
    arquivo = nomeArquivo;

    fseek(file, 0L, SEEK_END);
    long tam = ftell(file);
    rewind(file);

    if (file == NULL){
        printf ("ERROR");
        exit(1);
    }
    else{
        getline(&linha, &len, file);  
    }
    return(tratarPersonagem(linha, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL));
}

struct Personagem tratarPersonagem (char* linha, char* nome, char* corDoCabelo, char* codDaPele, char* corDosOlhos,
                                    char* anoNascimento, char* genero, char* homeworld, char* aux, char* str){
    struct Personagem personagem;
    int altura = 0;
    double peso = 0.0;
    int a = 0;//a = indexOf da posição do "name", "height", etc, para dar um substring e cortar a linha
    int b = 0;//b = indexOf da posição da primeira apariçao dos caracteres "',", para dar substring na outra parte da linha
    //aux = String auxiliar para realizaçao dos metodos indexOf e substring
    //str = String auxiliar para ajudar a tratar as exeções do peso dos personagens
    for(int i = 0; i < strlen(linha); i++){
        if(linha[i] == 'n' && linha[i+1] == 'a' && linha[i+2] == 'm' && linha[i+3] == 'e'){
            a = i + 8;
            i = strlen(linha);
        }
    }
    for(int i = a; i < strlen(linha); i++){
        if(linha[i] == '\'' && linha[i+1] == ','){
            b = i;
            i = strlen(linha);
        }
    }
    nome = str_substring(linha, a, b);
    strcpy(personagem.nome, nome);

    for(int i = 0; i < strlen(linha); i++){
        if(linha[i] == 'h' && linha[i+1] == 'e' && linha[i+2] == 'i' && linha[i+3] == 'g'
            && linha[i+4] == 'h' && linha[i+5] == 't'){
            a = i + 10;
            i = strlen(linha);
        }
    }
    for(int i = a; i < strlen(linha); i++){
        if(linha[i] == '\'' && linha[i+1] == ','){
            b = i;
            i = strlen(linha);
        }
    }    
    altura = atoi(str_substring(linha, a, b));
    personagem.altura = altura;

    for(int i = 0; i < strlen(linha); i++){
        if(linha[i] == 'm' && linha[i+1] == 'a' && linha[i+2] == 's' && linha[i+3] == 's'){
            a = i + 8;
            i = strlen(linha);
        }
    }
    for(int i = a; i < strlen(linha); i++){
        if(linha[i] == '\'' && linha[i+1] == ','){
            b = i;
            i = strlen(linha);
        }
    }
    str = str_substring(linha, a, b);


    if (strstr(str, ",")){
        peso = atoi(str_replace(str, ",", ""));
        personagem.peso = peso;
    }
    else if (strstr(str, ".")){
        int inteiro = 0;
        int frac = 0;
        char q[100];//q & w = 2 strings auxiliares para se caso tiver um "."
        char w[100];//Exemplo: 21.456, q = 21 e w = 456, assim posso particionar o inteiro da fraçao e tirar o "."
        int pos = 0;
        int j;

        j = 0;
        for(int i = 0; i < strlen(str); i++){
            if(str[i] == '.'){
                pos = i;
                i = strlen(str);
            }
            else{
                q[j] = str[i];//q = parte inteira
                j++;
            }
        }

        j = 0;
        for(int i = pos+1; i < strlen(str); i++){
            w[j] = str[i];//w = parte fracionaria
            j++;
        }
        inteiro = atoi(q);
        frac = atoi(w);

        if(strlen(w) == 1){
            peso = inteiro + frac * 0.1;
        }
        else if(strlen(w) == 2){
            peso = inteiro + frac * 0.01;
        }
        else if(strlen(w) == 3){
            peso = inteiro + frac * 0.001;
        }
        personagem.peso = peso;
    }
    else{
        if(strstr(str, "unknown")){
            personagem.peso = 0.0;
        }
        else{
            peso = atoi(str);
            personagem.peso = peso;
        }
    }

    for(int i = 0; i < strlen(linha); i++){
        if(linha[i] == 'h' && linha[i+1] == 'a' && linha[i+2] == 'i' && linha[i+3] == 'r' && linha[i+4] == '_'){
            a = i + 14;
            i = strlen(linha);
        }
    }
    for(int i = a; i < strlen(linha); i++){
        if(linha[i] == '\'' && linha[i+1] == ','){
            b = i;
            i = strlen(linha);
        }
    }
    corDoCabelo = str_substring(linha, a, b);
    strcpy(personagem.corDoCabelo, corDoCabelo);

    for(int i = 0; i < strlen(linha); i++){
        if(linha[i] == 's' && linha[i+1] == 'k' && linha[i+2] == 'i' && linha[i+3] == 'n' && linha[i+4] == '_'){
            a = i + 14;
            i = strlen(linha);
        }
    }
    for(int i = a; i < strlen(linha); i++){
        if(linha[i] == '\'' && linha[i+1] == ','){
            b = i;
            i = strlen(linha);
        }
    }
    codDaPele = str_substring(linha, a, b);
    strcpy(personagem.codDaPele, codDaPele);

    for(int i = 0; i < strlen(linha); i++){
        if(linha[i] == 'e' && linha[i+1] == 'y' && linha[i+2] == 'e' && linha[i+3] == '_' && linha[i+4] == 'c'){
            a = i + 13;
            i = strlen(linha);
        }
    }
    for(int i = a; i < strlen(linha); i++){
        if(linha[i] == '\'' && linha[i+1] == ','){
            b = i;
            i = strlen(linha);
        }
    }
    corDosOlhos = str_substring(linha, a, b);
    strcpy(personagem.corDosOlhos, corDosOlhos);

    for(int i = 0; i < strlen(linha); i++){
        if(linha[i] == 'b' && linha[i+1] == 'i' && linha[i+2] == 'r' && linha[i+3] == 't' && linha[i+4] == 'h' && linha[i+5] == '_'){
            a = i + 14;
            i = strlen(linha);
        }
    }
    for(int i = a; i < strlen(linha); i++){
        if(linha[i] == '\'' && linha[i+1] == ','){
            b = i;
            i = strlen(linha);
        }
    }
    anoNascimento = str_substring(linha, a, b);
    strcpy(personagem.anoNascimento, anoNascimento);

    for(int i = 0; i < strlen(linha); i++){
        if(linha[i] == 'g' && linha[i+1] == 'e' && linha[i+2] == 'n' && 
            linha[i+3] == 'd' && linha[i+4] == 'e' && linha[i+5] == 'r'){
            a = i + 10;
            i = strlen(linha);
        }
    }
    for(int i = a; i < strlen(linha); i++){
        if(linha[i] == '\'' && linha[i+1] == ','){
            b = i;
            i = strlen(linha);
        }
    }
    genero = str_substring(linha, a, b);
    strcpy(personagem.genero, genero);

    for(int i = 0; i < strlen(linha); i++){
        if(linha[i] == 'h' && linha[i+1] == 'o' && linha[i+2] == 'm' && 
            linha[i+3] == 'e' && linha[i+4] == 'w' && linha[i+5] == 'o'){
            a = i + 13;
            i = strlen(linha);
        }
    }
    for(int i = a; i < strlen(linha); i++){
        if(linha[i] == '\'' && linha[i+1] == ','){
            b = i;
            i = strlen(linha);
        }
    }
    homeworld = str_substring(linha, a, b);
    strcpy(personagem.homeworld, homeworld);

    return personagem;
}

void imprimirPersonagem(struct Personagem personagem){  
    double peso = personagem.peso;
    int inteiro = (int)personagem.peso;
    double aux = peso - inteiro;

    if (aux == 0.0){
        printf(" ## %s ## %d ## %d ## %s ## %s ## %s ## %s ## %s ## %s ## \n", personagem.nome, personagem.altura, 
            (int)personagem.peso, personagem.corDoCabelo, personagem.codDaPele, personagem.corDosOlhos, 
            personagem.anoNascimento, personagem.genero, personagem.homeworld);
    }
    else{
        printf(" ## %s ## %d ## %.1lf ## %s ## %s ## %s ## %s ## %s ## %s ## \n", personagem.nome, personagem.altura, 
            personagem.peso, personagem.corDoCabelo, personagem.codDaPele, personagem.corDosOlhos, 
            personagem.anoNascimento, personagem.genero, personagem.homeworld);
    }
}

char* str_substring(char str[], int start, int end){//metodo substring
    int i, j;
    char *sub; 
     
    if(start >= end || end > strlen(str)) {
        return NULL;
    }
     
    sub = (char *) malloc(sizeof(char) * (end - start + 1));
     
    for(i = start, j = 0; i < end; i++, j++) {
        sub[j] = str[i];
    }
     
    return sub;
}

char* str_replace(char* string, const char* substr, const char* replacement){//metodo replace
	char* tok = NULL;
	char* newstr = NULL;
	char* oldstr = NULL;
	int   oldstr_len = 0;
	int   substr_len = 0;
	int   replacement_len = 0;

	newstr = strdup(string);
	substr_len = strlen(substr);
	replacement_len = strlen(replacement);

	if (substr == NULL || replacement == NULL) {
		return newstr;
	}

	while ((tok = strstr(newstr, substr))) {
		oldstr = newstr;
		oldstr_len = strlen(oldstr);
		newstr = (char*)malloc(sizeof(char) * (oldstr_len - substr_len + replacement_len + 1));

		if (newstr == NULL) {
			free(oldstr);
			return NULL;
		}

		memcpy(newstr, oldstr, tok - oldstr);
		memcpy(newstr + (tok - oldstr), replacement, replacement_len);
		memcpy(newstr + (tok - oldstr) + replacement_len, tok + substr_len, oldstr_len - substr_len - (tok - oldstr));
		memset(newstr + oldstr_len - substr_len + replacement_len, 0, 1);

		free(oldstr);
	}

	free(string);

	return newstr;
}

bool str_contains(const char c, const char* str){
    bool contains = false;

    for (int i = 0; i < strlen(str); i++){
        if (str[i] == c){
            contains = true;
            i = strlen(str);
        }
    }
    return contains;
}

void start(){
   raiz = NULL;
}

No* novoNo(Personagem elemento){
    No* novo = (No*) malloc(sizeof(No));
    novo->elemento = elemento;
    novo->esq = NULL;
    novo->dir = NULL;
    novo->nivel = 1;
    return novo;
}

int max (int a, int b){
    if (a > b) {
        return a;
    } else {
        return b;
    }
}

int getNivel(No* no){
    return (no == NULL) ? 0 : no->nivel;
}

No* setNivel(No* no){
    no->nivel = 1 + max (getNivel(no->esq), getNivel(no->dir));
}

No* rotacionarDir (No* no){
    No* noEsq = no->esq;
    No* noEsqDir = noEsq->dir;
    noEsq->dir = no;
    no->esq = noEsqDir;
    setNivel(no);
    setNivel(noEsq);
    return noEsq;
}

No* rotacionarEsq (No* no){
    No* noDir = no->dir;
    No* noDirEsq = noDir->esq;
    noDir->esq = no;
    no->dir = noDirEsq;
    setNivel(no);
    setNivel(noDir);
    return noDir;
}

No* balancear(No* no){
    if (no != NULL) {
        int fator = getNivel(no->dir) - getNivel(no->esq);

        if (abs(fator) <= 1){
            setNivel(no);
        } 
        else if (fator == 2){
            int fatorfilhodir = getNivel(no->dir->dir) - getNivel(no->dir->esq);
            if (fatorfilhodir == -1) {
                no->dir = rotacionarDir(no->dir);
            }
            no = rotacionarEsq(no);
        } 
        else if (fator == -2){
            int fatorfilhoesq = getNivel(no->esq->dir) - getNivel(no->esq->esq);
            if (fatorfilhoesq == 1){
                no->esq = rotacionarEsq(no->esq);
            }
            no = rotacionarDir(no);
        } 
        else {
            printf("Erro ao balancear");
        }
    }
    return no;
}

No* inserirNo(No* no, Personagem elemento){
    if (no == NULL){
        no = novoNo(elemento);
    } 
    else if (strcmp(elemento.nome, no->elemento.nome) < 0){
        no->esq = inserirNo(no->esq, elemento);
    } 
    else if (strcmp(elemento.nome, no->elemento.nome) > 0){
        no->dir = inserirNo(no->dir, elemento);
    }
    no = balancear(no);
    return no;
}

void inserir(Personagem elemento){
    raiz = inserirNo(raiz, elemento);
}

bool pesq (No* no, char* x){
    bool resp;
    if (no == NULL){
        resp = false;
    } 
    else if (strcmp (x, no->elemento.nome) < 0){
        printf("esq ");
        resp = pesq(no->esq, x);
    } 
    else if (strcmp (x, no->elemento.nome) > 0){
        printf("dir ");
        resp = pesq(no->dir, x);
    } 
    else {
        resp = true;
    }
    return resp;
}

bool pesquisar(char* elemento){
    printf("raiz ");
    return pesq(raiz, elemento);
}

int main(){
    start();
    struct Personagem personagem;
    char nomeArquivo[500];
    char nome[500];
    int aux = 0;

    while (aux < 1){
        scanf ("%s", nomeArquivo);
        scanf("%*[^\n]"); scanf("%*c");//limpar o buffer

        if (nomeArquivo[0] == 'F' && nomeArquivo[1] == 'I' && nomeArquivo[2] == 'M'){
            aux = 1;
        }
        else{
            personagem = lerPersonagem(nomeArquivo);
            inserir(personagem);
        }
    }
    aux = 0;
    while (aux < 1){
        scanf ("%[^\n]", nome);
        scanf("%*[^\n]"); scanf("%*c");//limpar o buffer

        if (nome[0] == 'F' && nome[1] == 'I' && nome[2] == 'M'){
            aux = 1;
        }
        else{
            printf("%s ", nome);
            if (pesquisar(nome)){
                printf("SIM\n");
            }
            else{
                printf("NÃO\n");
            }
        }
    }
    return 0;
}
