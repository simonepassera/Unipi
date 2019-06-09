#include <stdlib.h>
#include <stdio.h>

typedef struct s_e
{
	int x;
	struct s_e *next;
}e;

e * insert(e *end, int n)
{
	end->next=(e*) malloc(sizeof(e));
	end->next->x=n;
	end->next->next=NULL;

	return end->next;
}

e * create(int n)
{
	e *t=(e*)malloc(sizeof(e));
	t->x=n;
	t->next=NULL;

	return t;
}

int search(e *l, int n, int c)
{
	if(l!=NULL)
	{
		if(l->x==n)
		{
			return c;
		}
		else
		{
			return search(l->next, n, ++c);
		}
	}
	else
	{
		return -1;
	}
}

void mtf(e **l, int c)
{
	if(c>0)
	{
		e *t1=*l, *t2=NULL;

		for(int i=0; i<(c-1); i++)
		{
			t1=t1->next;
		}

		t2=t1->next;
		t1->next=t2->next;
		t2->next=*l;
		*l=t2;
	}
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
	int n, x, i=0;
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

		int c=0, m=n, *s=(int*) malloc(sizeof(int)*n);

		while(i!=-1)
		{
			scanf("%d", &x);

			i=search(l, x, 0);

			mtf(&l, i);

			if(c==m)
			{
				m+=10;
				s=(int*) realloc(s, sizeof(int)*m);
			}

			s[c]=i;
			c++;
		}
		
		free_list(l);
		
		for(int i=0; i<c; i++)
		{
			printf("%d\n", s[i]);
		}

		free(s);
	}

	return EXIT_SUCCESS;
}

