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
void ins(pl *l, int n, int c);
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
	}while(v >= 0);


	scanf("%d", &n);

	ins(&l,n,4);

	stampa(l);

	return EXIT_SUCCESS;
}

void ins(pl *l, int n, int c)
{
	if(*l == NULL)
	{
		pl t=(pl)malloc(sizeof(e));
		t->info=n;
		t->next=NULL;
		*l=t;
	}
	else
	{
		if((*l)->next == NULL)
		{
			pl t=(pl)malloc(sizeof(e));
                	t->info=n;
                	t->next=NULL;
                	(*l)->next=t;
		}
		else
		{
			if(c==1)
			{
				pl t=(pl)malloc(sizeof(e));
               			t->info=n;
               			t->next=(*l)->next;
               			(*l)->next=t;
			}
			else
			{
				ins(&((*l)->next),n,--c);
			}
		}
	}
}

void stampa(pl l)
{
	if(l != NULL)
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

	if(*l == NULL)
	{
		*l=n;
	}
	else
	{
		pl t=*l;

		while(t->next != NULL)
		{
			t=t->next;
		}

		t->next=n;
	}
}
