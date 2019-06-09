#include <stdlib.h>
#include <stdio.h>

typedef struct
{
	int cod;
	int mese;
	int anno;
	int stipendio;
}dipendente;

void aumento(dipendente *d, int p)
{
	int i;

	for(i=0;i<2;i++)
	{
		if((d+i)->anno<2000 || ((d+i)->anno==2000 && (d+i)->mese<5))
		{
			(d+i)->stipendio+=((float)((d+i)->stipendio)/100)*p;
		}
	}
}

int main(int argc, char *argv[])
{
	int i,p;
	dipendente *d=(dipendente*) malloc(sizeof(dipendente)*2);

	for(i=0;i<2;i++)
	{
		scanf("%d", &(d+i)->cod);
		scanf("%d", &(d+i)->mese);
		scanf("%d", &(d+i)->anno);
		scanf("%d", &(d+i)->stipendio);
	}

	scanf("%d", &p);

	aumento(d,p);

	for(i=0;i<2;i++)
	{
		if((d+i)->stipendio>1200)
		{
			printf("%d %d\n", (d+i)->cod, (d+i)->stipendio);
		}
	}

	free(d);

	return EXIT_SUCCESS;
}
