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

struct Lista{
    Personagem array[MAX];
    int arrayNascimento[50];
    int n;
    int k;
};

struct Personagem lerPersonagem(char* nomeArquivo);
struct Personagem tratarPersonagem (char* linha, char* nome, char* corDoCabelo, char* codDaPele, char* corDosOlhos,
                                    char* anoNascimento, char* genero, char* homeworld, char* aux, char* str);
void imprimirPersonagem(struct Personagem personagem);
char* str_substring(char str[], int start, int end);
char* str_replace(char* string, const char* substr, const char* replacement);
bool str_contains(const char c, const char* str);

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
        printf(" ## %s ## %d ## %d ## %s ## %s ## %s ## %s ## %s ## %s ##\n", personagem.nome, personagem.altura, 
            (int)personagem.peso, personagem.corDoCabelo, personagem.codDaPele, personagem.corDosOlhos, 
            personagem.anoNascimento, personagem.genero, personagem.homeworld);
    }
    else{
        printf(" ## %s ## %d ## %.1f ## %s ## %s ## %s ## %s ## %s ## %s ##\n", personagem.nome, personagem.altura, 
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

bool str_contains(const char c, const char* str){//metodo contains
    bool contains = false;

    for (int i = 0; i < strlen(str); i++){
        if (str[i] == c){
            contains = true;
            i = strlen(str);
        }
    }

    return contains;
}

void swap(int a, int b, struct Lista* lista){//trocar posicao a com posicao b
    Personagem tmp;
    int nascTmp;

    tmp = lista->array[a];
    lista->array[a] = lista->array[b];
    lista->array[b] = tmp;

    nascTmp = lista->arrayNascimento[a];
    lista->arrayNascimento[a] = lista->arrayNascimento[b];
    lista->arrayNascimento[b] = nascTmp;
}

void inserirFim(Personagem personagem, struct Lista* lista){
    if(lista->n < MAX){
        lista->array[lista->n] = personagem;
        lista->n++;
    }
}

void mostrar(struct Lista* lista){
    for(int i = 0 ; i < lista->k; i++){
        imprimirPersonagem(lista->array[i]);
    }
    printf (" \n");
}

void construirArrayNascimento(struct Lista* lista){
    for(int i = 0; i < lista->n; i++){
        Personagem personagem = lista->array[i];
        char aux[100];
        aux[0] = personagem.anoNascimento[0];
        aux[1] = personagem.anoNascimento[1];
        if(aux[0] == 'u' && aux[1] == 'n'){
            lista->arrayNascimento[i] = 99;
        }
        else{
            lista->arrayNascimento[i] = atoi(aux);
        }
    }
}

void insercaoParcial(struct Lista* lista){
    int i, j;
    //mover 1 posiçao do array pra frente
    for(int q = lista->n; q >= 0; q--){
        lista->arrayNascimento[q] = lista->arrayNascimento[q-1];
    }
    for(int q = lista->n; q >= 0; q--){
        lista->array[q] = lista->array[q-1];
    }
    //insercao parcial
    for(i = 2; i <= lista->n; i++){
        int x = lista->arrayNascimento[i];
        Personagem tmp = lista->array[i];
        if(i > lista->k) 
            j = lista->k; 
        else 
            j = i -1;
            lista->arrayNascimento[0] = x;
            lista->array[0] = tmp;
        while(j > 0 && x < lista->arrayNascimento[j]){
            lista->arrayNascimento[j+1] = lista->arrayNascimento[j];
            lista->array[j+1] = lista->array[j];
            j--;
        }
        lista->arrayNascimento[j+1] = x;
        lista->array[j+1] = tmp;
    }
    //voltar com o array para a posicao 0
    for (int q = 0; q < lista->n; q++){
        lista->arrayNascimento[q] = lista->arrayNascimento[q+1];
    }
    for (int q = 0; q < lista->n; q++){
        lista->array[q] = lista->array[q+1];
    }
}

void QuickSort(int esq, int dir, struct Lista* lista){
    int i = esq;
    int j = dir;
    int aux = 0;
    int pivo = lista->arrayNascimento[(dir+esq)/2];

    while(i <= j){
        while (lista->arrayNascimento[i] < pivo){
            i++;
        }
        while (lista->arrayNascimento[j] > pivo){
            j--;
        }
        if (i <= j){
            swap(i, j, lista);
            i++;
            j--;
        }
    }
    if (esq < j){
        QuickSort(esq, j, lista);
    }
    if (i < dir){
        QuickSort(i, dir, lista);
    }
}

void verificarAnoNascimentoIgual(struct Lista* lista){
    int inicio = 0;
    int fim = 0;
    int i = 0;

    while (i < lista->k){
        if (lista->arrayNascimento[i] == lista->arrayNascimento[i+1]){
            inicio = i;
            while (lista->arrayNascimento[i] == lista->arrayNascimento[i+1]){
                i++;
            }
            fim = i;
            QuickSort(inicio, fim, lista);
        }
        else{
            i++;
        }
    }
}

int main (){
    struct Personagem personagem;
    struct Lista lista;
    lista.n = 0;
    lista.k = 10;
    char nomeArquivo[500];
    char movimentacoes[500];
    int aux = 0;
    int n = 0;
    int pos = 0;

    while (aux < 1){
        scanf ("%s", nomeArquivo);
        scanf("%*[^\n]"); scanf("%*c");//limpar o buffer

        if (nomeArquivo[0] == 'F' && nomeArquivo[1] == 'I' && nomeArquivo[2] == 'M'){
            aux = 1;
        }
        else{
            personagem = lerPersonagem(nomeArquivo);
            inserirFim(personagem, &lista);
        }
    }
    //construir um array com os anos de nascimento
    construirArrayNascimento(&lista);
    //ordenar por insercao parcial
    insercaoParcial(&lista);
    //verificar se tem algum ano de nascimento igual e ordena atraves do quicksort usando o atributo "nome"
    verificarAnoNascimentoIgual(&lista);
    mostrar(&lista);
    return 0;
}