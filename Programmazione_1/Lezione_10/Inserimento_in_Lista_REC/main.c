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
void ins(pl *l);

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

	ins(&l);

	RecStampa(l);

	return EXIT_SUCCESS;
}

void ins(pl *l)
{
	if(*l!=NULL)
	{
		if((*l)->info%2 == 0)
		{
			pl n=(pl)malloc(sizeof(e));
			n->info=-1;
			n->next=*l;

			*l=n;

			ins(&((*l)->next->next));
		}
		else
		{
			ins(&((*l)->next));
		}
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
