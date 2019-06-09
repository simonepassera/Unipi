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
void Stampa(pl l);
void RecStampaInversa(pl l);

int  main(int argc, char *argv[])
{
	pl l=NULL;
	int v;

	do
	{
		scanf("%d", &v);

		if(v>=0)
		{
			inserisci(&l,v);
		}
	}while(v>=0);

	RecStampaInversa(l);

	return EXIT_SUCCESS;
}

void inserisci(pl *l, int v)
{
	pl n=(pl)malloc(sizeof(e));
	n->info=v;

	if(*l==NULL)
	{
		n->next=NULL;
		*l=n;
	}
	else
	{
		n->next=*l;
		*l=n;
	}
}

void RecStampaInversa(pl l)
{
	Stampa(l);

	printf("NULL");
}

void Stampa(pl l)
{
	if(l!=NULL)
	{
			Stampa(l->next);
			printf("%d -> ", l->info);
	}
}
