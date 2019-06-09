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

int key(a *t, int *kt)
{
	if(t==NULL)
	{
		return 0;
	}
	else
	{
		int l=key(t->sx, kt);
		int kl=*kt;
		int r=key(t->dx, kt);
		int kr=*kt;

		if(l==0 && r==0)
		{
			*kt=t->k;
			return t->k;
		}

		if(l==r)
		{
			if(kl<=kr)
			{
				if(t->k<kl)
				{
					*kt=t->k;
				}
				else
				{
					*kt=kl;
				}
			}
			else
			{
				*kt=kr;
			}

			return l+(t->k);
		}
		else
		{
			if(l<r)
			{
				if(t->k<kr)
                {
                    *kt=t->k;
                }
                else
                {
                    *kt=kr;
                }

				return r+t->k;
			}
			else
			{
				if(t->k<kl)
                {
                	*kt=t->k;
                }
                else
                {
                    *kt=kl;
                }

                return l+t->k;
			}
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

	int k=0;

	key(t, &k);

	printf("%d", k);

	free_tree(t);

	return EXIT_SUCCESS;
}