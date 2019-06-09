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
int RecLenght(pl l);

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

	printf("%d", RecLenght(l));

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

int RecLenght(pl l)
{
	if(l!=NULL)
	{
			return 1+RecLenght(l->next);
	}
	else
	{
		return 0;
	}
}
