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
void canc(pl *l, int n);
void stampa(pl l);

int  main(int argc, char *argv[])
{
	pl l=NULL;
	int v,n;

	do
	{
		scanf("%d", &v);

		if(v>=0)
		{
			inserisci(&l,v);
		}
	}while(v>=0);


	scanf("%d", &n);

	canc(&l,n);

	stampa(l);

	return EXIT_SUCCESS;
}

void canc(pl *l, int n)
{
	if(n!=0)
	{
		if((*l)!=NULL)
		{
			pl t=*l;
			*l=(*l)->next;

			free(t);

			canc(&(*l), --n);
		}
	}
}

void stampa(pl l)
{
	if(l!=NULL)
	{
		printf("%d -> ", l->info);

		stampa(l->next);
	}
	else
	{
		printf("NULL");
	}
}

void inserisci(pl *l, int v)
{
	pl n=(pl)malloc(sizeof(e));
	n->info=v;
	n->next=NULL;

	if(*l==NULL)
	{
		*l=n;
	}
	else
	{
		pl t=*l;

		while(t->next!=NULL)
		{
			t=t->next;
		}

		t->next=n;
	}
}
