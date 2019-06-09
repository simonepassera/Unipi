#include <stdlib.h>
#include <stdio.h>

typedef struct s_e
{
	int x;
	struct s_e *prec;
	struct s_e *next;
}e;

e * insert(e *end, int n)
{
	end->next=(e*) malloc(sizeof(e));
	end->next->x=n;
	end->next->prec=end;
	end->next->next=NULL;

	return end->next;
}

e * create(int n)
{
	e *t=(e*)malloc(sizeof(e));
	t->x=n;
	t->prec=NULL;
	t->next=NULL;

	return t;
}

void print_list(e *l)
{
	if(l!=NULL)
	{
		print_list(l->next);
		printf("%d\n", l->x);
	}
}

void free_list(e *l)
{
	if(l!=NULL)
	{
		e *t=l->next;
		free(l);
		free_list(t);
	}
}

int main(int argc, char **argv)
{
	int n, x;
	e *l=NULL, *end=NULL;

	scanf("%d", &n);

	if(n>0)
	{
		scanf("%d", &x);
		l=create(x);

		end=l;

		for(int i=1; i<n; i++)
		{
			scanf("%d", &x);
			end=insert(end, x);
		}

		print_list(l);

		free_list(l);
	}

	return EXIT_SUCCESS;
}

