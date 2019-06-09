#include <stdlib.h>
#include <stdio.h>

struct elemento
{
	int info;
	struct elemento *next;
};

typedef struct elemento e;
typedef e *pl;

void inserisci(pl *l, int v);
void cancella(pl *l);
void stampa(pl l);

int  main(int argc, char *argv[])
{
	pl l=NULL;
	int v;

	do
	{
		scanf("%d", &v);

		if(v>0)
		{
			inserisci(&l, v);
		}
		else
		{
			if(v==0)
			{
				cancella(&l);
			}
		}
	}while(v>=0);

	stampa(l);

	return EXIT_SUCCESS;
}

void inserisci(pl *l, int v)
{
	pl n=(pl)malloc(sizeof(e));

	n->info=v;
	n->next=*l;
	*l=n;
}

void cancella(pl *l)
{
	if(*l!=NULL)
	{
		pl t=*l;
		*l=(*l)->next;
		free(t);
	}
}

void stampa(pl l)
{
	if(l!=NULL)
	{
		while(l!=NULL)
		{
			printf("%d\n", l->info);
			l=l->next;
		}
	}
}
