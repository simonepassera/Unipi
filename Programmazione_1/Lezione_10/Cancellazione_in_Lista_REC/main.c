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
void RecStampa(pl l);
pl cancella(pl l, int n);

int  main(int argc, char *argv[])
{
	pl l=NULL;
	int v,n;

	scanf("%d", &n);

	do
	{
		scanf("%d", &v);

		if(v>=0)
		{
			inserisci(&l,v);
		}
	}while(v>=0);

	l=cancella(l, n);

	RecStampa(l);

	return EXIT_SUCCESS;
}

pl cancella(pl l, int n)
{
	if(l!=NULL)
	{
		if(l->info%n == 0)
		{
			pl t=l->next;
			free(l);
			return cancella(t, n);
		}
		else
		{
			l->next=cancella(l->next, n);
			return l;
		}
	}
	else
	{
		return NULL;
	}
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

void RecStampa(pl l)
{
	if(l!=NULL)
	{
			printf("%d -> ", l->info);
			RecStampa(l->next);
	}
	else
	{
		printf("NULL");
	}
}
