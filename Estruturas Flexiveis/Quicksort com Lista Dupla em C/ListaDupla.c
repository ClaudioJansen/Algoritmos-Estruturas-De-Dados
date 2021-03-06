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

typedef struct Celula {
	Personagem elemento;
	struct Celula* prox;
	struct Celula* ant;
} Celula;
Celula* primeiro;
Celula* ultimo;

struct Personagem lerPersonagem(char* nomeArquivo);
struct Personagem tratarPersonagem (char* linha, char* nome, char* corDoCabelo, char* codDaPele, char* corDosOlhos,
                                    char* anoNascimento, char* genero, char* homeworld, char* aux, char* str);
void imprimirPersonagem(struct Personagem personagem);
char* str_substring(char str[], int start, int end);
char* str_replace(char* string, const char* substr, const char* replacement);
Personagem remover();

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
        printf(" ## %s ## %d ## %.1f ## %s ## %s ## %s ## %s ## %s ## %s ## \n", personagem.nome, personagem.altura, 
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

bool str_contains(const char c, const char * str){
    bool contains = false;

    for (int i = 0; i < strlen(str); i++){
        if (str[i] == c){
            contains = true;
            i = strlen(str);
        }
    }
    return contains;
}

int tamanho(){
    Celula* i;
    int tamanho = 0;
    for(i = primeiro; i != ultimo; i = i->prox, tamanho++);
    return tamanho;
}

Celula* novaCelula(Personagem elemento){
    Celula* nova = (Celula*) malloc(sizeof(Celula));
    nova->elemento = elemento;
    nova->prox = NULL;
    return nova;
}

void inserir(Personagem x) {
    ultimo->prox = novaCelula(x);
    ultimo->prox->ant = ultimo;
    ultimo = ultimo->prox;
}

void mostrar(){
    int j = 0;
    Celula* i;
	for (i = primeiro->prox; i != NULL; i = i->prox, j++) {
        imprimirPersonagem(i->elemento);
	}
}

void swap(int a, int b){
        Personagem tmp;
        Celula* i;
        Celula* j;
        int aux1 = 0;
        int aux2 = 0;
        for(i = primeiro; aux1 < a; i = i->prox, aux1++);
        for(j = primeiro; aux2 < b; j = j->prox, aux2++);
        tmp = i->elemento;
        i->elemento = j->elemento;
        j->elemento = tmp;
    }

void QuickSortCabelo(int esq, int dir){
    Celula* aux;
    int i = esq;
    int j = dir;
    int x;
    char pivo[100];
    for(aux = primeiro; x < (esq+dir) / 2; aux = aux->prox, x++);
    strcpy(pivo, aux->elemento.corDoCabelo);

    while(i <= j){
        Celula* a;
        Celula* b;
        int cont1 = 1;
        int cont2 = tamanho();
        for(a = primeiro->prox; cont1 < i; a = a->prox, cont1++);
        while (strcmp(a->elemento.corDoCabelo, pivo) < 0){
            i++;
            a = a->prox;
        }
        for(b = ultimo; cont2 > j; b = b->ant, cont2--);
        while (strcmp(b->elemento.corDoCabelo, pivo) > 0){
            j--;
            b = b->ant;
        }
        if (i <= j){
            swap(i, j);
            i++;
            a = a->prox;
            j--;
            b = b->ant;
        }
    }
    if (esq < j){
        QuickSortCabelo(esq, j);
    }
    if (i < dir){
        QuickSortCabelo(i, dir);
    }
}

void QuickSortNome(int esq, int dir){
    Celula* aux;
    int i = esq;
    int j = dir;
    int x;
    char pivo[100];
    for(aux = primeiro; x < (esq+dir) / 2; aux = aux->prox, x++);
    strcpy(pivo, aux->elemento.nome);

    while(i <= j){
        Celula* a;
        Celula* b;
        int cont1 = 1;
        int cont2 = tamanho();
        for(a = primeiro->prox; cont1 < i; a = a->prox, cont1++);
        while (strcmp(a->elemento.nome, pivo) < 0){
            i++;
            a = a->prox;
        }
        for(b = ultimo; cont2 > j; b = b->ant, cont2--);
        while (strcmp(b->elemento.nome, pivo) > 0){
            j--;
            b = b->ant;
        }
        if (i <= j){
            swap(i, j);
            i++;
            a = a->prox;
            j--;
            b = b->ant;
        }
    }
    if (esq < j){
        QuickSortNome(esq, j);
    }
    if (i < dir){
        QuickSortNome(i, dir);
    }
}

void verificarCabelosIguais(){
        int inicio;
        int fim;
        int i = 1;
        int aux = 0;
        Celula* I;
        I = primeiro->prox;

        while (aux < 1){
            if (strcmp(I->elemento.corDoCabelo, I->prox->elemento.corDoCabelo) == 0){
                inicio = i;
                char cabelo[100];
                strcpy(cabelo, I->elemento.corDoCabelo);
                Celula* J;
                for(J = I->prox; J != NULL; J = J->prox){
                    if(strcmp(J->elemento.corDoCabelo, cabelo) == 0){
                        i++;
                        I = I->prox;
                    }
                }
                fim = i;
                QuickSortNome(inicio, fim);
            }
            else{
                i++;
                I = I->prox;
            }
            if (i == tamanho()){
                aux = 1;
            }
        }
    }

int main(){
    struct Personagem personagem;
    primeiro = novaCelula(personagem);
    ultimo = primeiro;
    char nomeArquivo[500];
    char movimentacoes[500];
    int aux = 0;
    int n = 0;

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
    QuickSortCabelo(1, tamanho());
    verificarCabelosIguais();
    mostrar();
    return 0;
}