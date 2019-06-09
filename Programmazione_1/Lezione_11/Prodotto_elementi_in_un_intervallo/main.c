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
int prod(pl l, int n, int m);

int  main(int argc, char *argv[])
{
	pl l=NULL;
	int v,n,m;

	scanf("%d", &n);

	do
	{
		scanf("%d", &m);
	}while(m<=n);

	do
	{
		scanf("%d", &v);

		if(v>=0)
		{
			inserisci(&l,v);
		}
	}while(v >= 0);

	printf("%d", prod(l, n, m));

	return EXIT_SUCCESS;
}

int prod(pl l, int n, int m)
{
	if(l!=NULL)
	{
		if(n<0)
		{
			if(l->info!=m && l->info<m)
			{
				if(l->next!=NULL)
				{
					return l->info * prod(l->next, n, m);
				}
				else
				{
					return 1;
				}
			}
			else
			{
				return 1;
			}
		}
		else
		{
			if(l->info!=n)
			{
				if(l->next!=NULL)
				{
					return prod(l->next, n, m);
				}
				else
				{
					return 1;
				}
			}
			else
			{
				return prod(l->next, -1, m);
			}
		}
	}
	else
	{
		return -1;
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
