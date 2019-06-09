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
int cerca(pl l, int v);
pl intersezione(pl l1, pl l2);
void stampa(pl l);

int  main(int argc, char *argv[])
{
	pl l1=NULL, l2=NULL;
	int v;

	do
	{
		scanf("%d", &v);

		if(v>=0)
		{
			inserisci(&l1,v);
		}
	}while(v>=0);

	do
        {
                scanf("%d", &v);

                if(v>=0)
                {
                        inserisci(&l2,v);
                }
        }while(v>=0);

	stampa(intersezione(l1,l2));

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

pl intersezione(pl l1, pl l2)
{
	pl l=NULL;

	if(l1!=NULL && l2!=NULL)
	{
		while(l1!=NULL)
		{
			if(cerca(l2, l1->info))
			{
				if(!cerca(l, l1->info))
				{
					inserisci(&l, l1->info);
				}
			}

			l1=l1->next;
		}
	}

	return l;
}

int cerca(pl l, int v)
{
	int b=0;

	if(l!=NULL)
	{
		while(l!=NULL && !b)
		{
			if(l->info==v)
			{
				b=1;
			}
			else
			{
				l=l->next;
			}
		}
	}

	return b;
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
