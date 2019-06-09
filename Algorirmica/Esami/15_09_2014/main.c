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

int visit(a *t, int c, int *p, int *i)
{
	if(t==NULL)
	{
		if(!c)
		{
			(*i)--;
		}

		return 0;
	}
	else
	{
		int l=visit(t->sx, 1, p, i);

		int j=*i;
		(*i)++;

		int r=visit(t->dx, 0, p, i);

		if(l>r)
		{
			p[j]=t->k;

			if(j==*i)
			{
				(*i)++;
			}
		}

		if(c)
		{
			return l+1;
		}
		else
		{
			return r+1;
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

	int r=0, i=0, *p=(int *) malloc(sizeof(int)*n);

	for(int j=0; j<n; j++)
	{
		p[j]=-1;
	}

	visit(t, 0, p, &i);

	for(int j=0; j<i; j++)
	{
		if(p[j]>0)
		{
			printf("%d\n", p[j]);
		}
	}

	free(p);
	free_tree(t);

	return EXIT_SUCCESS;
}