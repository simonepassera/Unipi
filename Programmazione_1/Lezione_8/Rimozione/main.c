#include <stdlib.h>
#include <stdio.h>
#include <math.h>

struct elemento
{
	int info;
	struct elemento *next;
};

typedef struct elemento e;
typedef e *pl;

void inserisci(pl *l, int v);
void coda(pl *l, int v);
void cancella(pl *l, int v);
void stampa(pl l);

int  main(int argc, char *argv[])
{
	pl l=NULL;
	int v;

	do
	{
		scanf("%d", &v);

		if(v!=0)
		{
			if(v<0)
			{
				cancella(&l, abs(v));
			}
			else
			{
				if(v%2==0)
				{
					inserisci(&l,v);
				}
				else
				{
					coda(&l,v);
				}
			}
		}
	}while(v!=0);

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

void coda(pl *l, int v)
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

void cancella(pl *l, int v)
{
	if(*l!=NULL)
	{
		pl t=*l;

		if((*l)->info==v)
		{
			*l=(*l)->next;
			free(t);
		}
		else
		{
			int b=0;

			while(!b && t->next!=NULL)
			{
				if((t->next)->info==v)
				{
					b=1;
				}
				else
				{
					t=t->next;
				}
			}

			if(b)
			{
				pl c=t->next;
				t->next=c->next;
				free(c);
			}
		}
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
