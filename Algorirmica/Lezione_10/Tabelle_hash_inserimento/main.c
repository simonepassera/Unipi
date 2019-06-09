#include <stdio.h>
#include <stdlib.h>

typedef struct s_e
{
	int x;
	struct s_e *next;
}e;

int hash(int x, int a, int b, int n)
{
	return ((a*x+b)%999149)%(2*n);
}

int insert(e **t, int x, int a, int b, int l)
{
	int i=hash(x, a, b, l), c=0;

	e *n=(e*) malloc(sizeof(e));

	n->x=x;

	if(t[i]==NULL)
	{
		n->next=NULL;

		t[i]=n;
	}
	else
	{
		c=1;
		n->next=t[i];
		t[i]=n;
	}
	
	return c;
}

int max(e **t, int n)
{
	int m=0, l=0;
	e *p=NULL;

	for(int i=0;i<(2*n);i++)
	{
		p=t[i];
		l=0;

		while(p!=NULL)
		{
			l++;
			p=p->next;
		}

		if(l>m)
		{
			m=l;
		}
	}

	return m;
}

int main(int argc, char **argv)
{
	int n, a, b, x, c=0;

	scanf("%d\n%d\n%d", &n, &a, &b);

	e **t=(e**) malloc(sizeof(e*)*2*n);


	for(int i=0; i<n; i++)
	{
		scanf("%d", &x);
		c+=insert(t, x, a, b, n);
	}

	printf("%d\n%d\n", max(t, n), c);

	for(int i=0;i<(2*n);i++)
	{
		free(t[i]);
	}

	free(t);

	return EXIT_SUCCESS;
}