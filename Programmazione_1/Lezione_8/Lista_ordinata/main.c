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
			inserisci(&l,v);
		}
	}while(v>0);

	stampa(l);

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
		if((*l)->info>=v)
		{
			n->next=*l;
			*l=n;
		}
		else
		{
			pl t=*l;
			int b=1;

			while(t->next!=NULL && b)
			{
				if((t->next)->info>=v)
				{
					n->next=t->next;
					t->next=n;
					b=0;
				}
				else
				{
					t=t->next;
				}
			}

			if(b)
			{
				n->next=NULL;
				t->next=n;
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
