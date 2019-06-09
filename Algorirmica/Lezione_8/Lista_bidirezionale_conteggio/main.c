#include <stdlib.h>
#include <stdio.h>

typedef struct el
{
	int x;
	int c;
	struct el *prec;
	struct el *next;
}e;

e * insert(e *end, int n)
{
	end->next=(e*) malloc(sizeof(e));
	end->next->x=n;
	end->next->c=0;
	end->next->prec=end;
	end->next->next=NULL;

	return end->next;
}

e * create(int n)
{
	e *t=(e*)malloc(sizeof(e));
	t->x=n;
	t->c=0;
	t->prec=NULL;
	t->next=NULL;

	return t;
}

int search(e *l, int n, int c, int *c0)
{
	if(l!=NULL)
	{
		if(l->x==n)
		{
			*c0=++l->c;
			return c;
		}
		else
		{
			return search(l->next, n, ++c, c0);
		}
	}
	else
	{
		*c0=-1;
		return -1;
	}
}

void sort(e **l, int c)
{
	e *t1=*l, *t2=NULL;

    for(int i=1; i<c; i++)
    {
      	t1=t1->next;
    }

	t2=t1->next;

	if(t2->c>t1->c)
	{
		if(t2->next!=NULL)
		{
			t2->next->prec=t1;
		}

		t1->next=t2->next;

		while(t1->c < t2->c)
		{
			t1=t1->prec;
		}

		t2->next=t1->next;
		t2->next->prec=t2;
		t2->prec=t1;
		t1->next=t2;
	}
}

void mtf(e **l, int c)
{
	if(c>0)
	{
		e *t1=*l, *t2=NULL;

		for(int i=1; i<c; i++)
		{
			t1=t1->next;
		}

		t2=t1->next;

		if(t2->next!=NULL)
		{
			t2->next->prec=t1;
		}

		t1->next=t2->next;
		t2->prec=NULL;
		t2->next=*l;
		t2->next->prec=t2;
		*l=t2;
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

		int c=0, c0, m=n, *s=(int*) malloc(sizeof(int)*n), max=0;;

		while(i!=-1)
		{
			scanf("%d", &x);

			i=search(l, x, 0, &c0);

			if(c0>max)
			{
				mtf(&l, i);
				max=c0;
			}
			else
			{
				sort(&l, i);
			}

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