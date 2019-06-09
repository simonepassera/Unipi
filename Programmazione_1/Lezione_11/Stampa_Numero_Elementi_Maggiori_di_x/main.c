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
int CalcoloSomma(pl l);
int CalcoloNElementi(pl);

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

	printf("%d\n%d", CalcoloSomma(l), CalcoloNElementi(l));

	return EXIT_SUCCESS;
}

int CalcoloNElementi(pl l)
{
	int v=CalcoloSomma(l),s=0;

	while(l!=NULL)
	{
		if(l->info>(v/(float)4))
		{
			s++;
		}

		l=l->next;
	}

	return s;
}

int CalcoloSomma(pl l)
{
	if(l!=NULL)
	{
		return l->info+CalcoloSomma(l->next);
	}
	else
	{
		return 0;
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
