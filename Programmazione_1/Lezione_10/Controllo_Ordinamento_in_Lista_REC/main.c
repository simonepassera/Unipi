#include <stdlib.h>
#include <stdio.h>

struct elemento
{
	int info;
	struct elemento *next;
};

typedef struct elemento e;
typedef e *pl;
typedef enum{false,true} boolean;

void inserisci(pl *l, int v);
boolean ordinata(pl l);

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

	if(ordinata(l))
	{
		printf("True");
	}
	else
	{
		printf("False");
	}

	return EXIT_SUCCESS;
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

boolean ordinata(pl l)
{
	if(l->next!=NULL)
	{
		if(l->info < l->next->info)
		{
			return ordinata(l->next);
		}
		else
		{
			return 0;
		}
	}
	else
	{
		return 1;
	}
}
