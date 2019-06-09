#include <stdio.h>
#include <stdlib.h>

typedef struct nodo
{
	int k;
	struct nodo *dx;
	struct nodo *sx;
}a;

a * insert(a *t, int x)
{
	if(t==NULL)
	{
		a* n=(a*) malloc(sizeof(a));
		n->k=x;
		n->sx=NULL;
		n->dx=NULL;

		return n;
	}

	if(t->k<x)
	{
		t->dx=insert(t->dx, x);
	}
	else
	{
		t->sx=insert(t->sx, x);
	}

	return t;
}

int altezza(a *t)
{
	if(t==NULL)
	{
		return 0;
	}

	int adx, asx;

	asx=altezza(t->sx);
	adx=altezza(t->dx);

	if(adx>asx)
	{
		return adx+1;
	}
	else
	{
		return asx+1;
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

	printf("%d", altezza(t));

	free_tree(t);

	return EXIT_SUCCESS;
}
