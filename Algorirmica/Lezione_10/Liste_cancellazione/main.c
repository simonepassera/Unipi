#include <stdio.h>
#include <stdlib.h>

typedef struct s_e
{
	int k;
	struct s_e *next;
}e;

void insert(e **l, int x)
{
	e *n=(e*) malloc(sizeof(e));

	n->k=x;

	if(*l==NULL)
	{
		n->next=NULL;

		*l=n;
	}
	else
	{
		n->next=*l;
		*l=n;
	}
}

void free_list(e *l)
{
	if(l!=NULL)
	{
		free_list(l->next);
		free(l);
	}
}

void print_list(e *l)
{
	if(l!=NULL)
	{
		print_list(l->next);
		printf("%d ", l->k);
	}
}

e * delete_list(e *l, int m)
{
	if(l!=NULL)
	{
		if(l->k<=m)
		{
			e *t=l->next;
			free(l);

			t=delete_list(t, m);

			return t;
		}
		else
		{
			l->next=delete_list(l->next, m);

			return l;
		}
	}
	else
	{
		return NULL;
	}
}

int avg(e *l, int n)
{
	int s=0;

	while(l!=NULL)
	{
		s+=l->k;
		l=l->next;
	}

	return s/n;
}

int main(int argc, char **argv)
{
	int n, x;

	scanf("%d", &n);

	e *l=NULL;

	for(int i=0; i<n; i++)
	{
		scanf("%d", &x);

		insert(&l, x);
	}

	int m=avg(l, n);

	printf("%d\n", m);

	print_list(l);

	l=delete_list(l, m);

	printf("\n");

	print_list(l);

	free_list(l);

	return EXIT_SUCCESS;
}
