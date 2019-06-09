#include <stdio.h>
#include <stdlib.h>

typedef struct nodo
{
	int k;
	struct nodo *dx;
	struct nodo *m;
	struct nodo *sx;
}a;

a * insert(a *t, int x)
{
	if(t==NULL)
	{
		a* n=(a*) malloc(sizeof(a));
		n->k=x;
		n->sx=NULL;
		n->m=NULL;
		n->dx=NULL;

		return n;
	}

	if(x<t->k)
	{
		t->sx=insert(t->sx, x);
	}
	else
	{
		if(x%t->k==0)
		{
			t->m=insert(t->m, x);
		}
		else
		{
			t->dx=insert(t->dx, x);
		}
	}

	return t;
}

int figli(a *t)
{
	if(t==NULL)
	{
		return 0;
	}
	else
	{
		if(t->dx!=NULL && t->sx!=NULL && t->m!=NULL)
		{
			return 1+figli(t->sx)+figli(t->dx)+figli(t->m);
		}
		else
		{
			return figli(t->sx)+figli(t->dx)+figli(t->m);
		}
	}
}


void free_tree(a *t)
{
	if(t!=NULL)
	{
		free_tree(t->sx);
		free_tree(t->dx);

		free(t);
	}
}

int main(int argc, char **argv)
{
	int n, x;
	a *t=NULL;

	scanf("%d", &n);

	for(int i=0; i<n; i++)
	{
		scanf("%d", &x);
		t=insert(t, x);
	}

	printf("%d", figli(t));

	free_tree(t);

	return EXIT_SUCCESS;
}
