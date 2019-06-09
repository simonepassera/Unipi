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

int match(a *t1, a *t2, int k)
{
	if(t1==NULL || t2==NULL)
	{
		return 0;
	}

	if(t1->k==t2->k)
	{
		if(t1->k==k)
		{
			return 1;
		}

		if(k>t1->k)
		{
			if(match(t1->dx, t2->dx, k))
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
		else
		{
			if(match(t1->sx, t2->sx, k))
            {
                return 1;
            }
            else
            {
                return 0;
            }
		}
	}
	else
	{
		return 0;
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

int main(int argc, char**argv)
{
	int n, x, k;
	a *t1=NULL, *t2=NULL;

	scanf("%d %d", &n, &k);

	for(int i=0; i<n; i++)
	{
		scanf("%d", &x);
		t1=insert(t1, x);
	}

	for(int i=0; i<n; i++)
    {
        scanf("%d", &x);
        t2=insert(t2, x);
    }

	printf("%d", match(t1, t2, k));

	free_tree(t1);
	free_tree(t2);

	return EXIT_SUCCESS;
}
