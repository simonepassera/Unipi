#include <stdio.h>
#include <stdlib.h>

typedef struct s_e
{
	int k;
	struct s_e *next;
}e;

void insert(e **l, int x)
{
	if(*l==NULL)
	{
		e *n=(e*) malloc(sizeof(e));

		n->k=x;
		n->next=NULL;
		*l=n;
	}
	else
	{
		insert(&((*l)->next), x);
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
		printf("%d ", l->k);
		print_list(l->next);
	}
}

int somme(e *l)
{
	if(l!=NULL)
	{
		int t=l->k;
		l->k=somme(l->next);

		return (l->k)+t;
	}
	else
	{
		return 0;
	}
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

	print_list(l);

	somme(l);

	printf("\n");

	print_list(l);

	free_list(l);

	return EXIT_SUCCESS;
}