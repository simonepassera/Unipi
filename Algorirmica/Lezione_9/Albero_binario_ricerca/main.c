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
		a *n=(a*) malloc(sizeof(a));
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

int search(a *t, int x)
{
	if(t==NULL)
	{
		return -1;
	}

	if(x==t->k)
	{
		return 0;
	}

	int f=-1;

	if(x>t->k)
	{
		f=search(t->dx, x);
	}
	else
	{
		f=search(t->sx, x);
	}

	if(f>=0)
	{
		return 1+f;
	}
	else
	{
		return -1;
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

	int c=0, m=10, *s=(int*) malloc(sizeof(int)*10);

	do
	{
		scanf("%d", &x);

		if(x>=0)
		{

			if(c==m)
			{
				m+=10;
				s=(int*) realloc(s, sizeof(int)*m);
			}

			s[c]=search(t, x);
			c++;
		}
	}while(x>=0);

	for(int i=0; i<c; i++)
	{
		if(s[i]!=-1)
		{
			printf("%d\n", s[i]);
		}
		else
		{
			printf("NO\n");
		}
	}

	free_tree(t);
	free(s);

	return EXIT_SUCCESS;
}
