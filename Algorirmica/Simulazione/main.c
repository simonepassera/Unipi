#include <stdlib.h>
#include <stdio.h>
#include <string.h>

typedef struct el
{
	char *n;
	struct el *next;
}e;

int compare(const void *x, const void *y)
{
	char **x1=(char**)x;
	char **y1=(char**)y;

	return strcmp(*x1, *y1);
}

void delete_head(e **c, e *end)
{
	if(*c!=NULL)
	{
        	e *t=(*c)->next;
        	free(*c);
		*c=t;

		if(c==NULL)
		{
			end=NULL;
		}
       	}
}

e * insert(e *end, char *x)
{
	end->next=(e*) malloc(sizeof(e));
	end->next->n=(char*) malloc(sizeof(char)*101);
        strcpy(end->next->n, x);
	end->next->next=NULL;

	return end->next;
}

e * create(char *x)
{
	e *t=(e*)malloc(sizeof(e));
	t->n=(char*) malloc(sizeof(char)*101);
        strcpy(t->n, x);
	t->next=NULL;

	return t;
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

int main()
{
	int x, i=0;
	e *c=NULL, *end=NULL;
	char *s=(char*) malloc(sizeof(char)*101);

	do
	{
		scanf("%d", &x);

		switch(x)
		{
			case 1:
				scanf("%s", s);

				if(c==NULL)
				{
					c=create(s);
					end=c;
				}
				else
				{
					end=insert(end, s);
				}

				i++;

				break;
			case 2:
				delete_head(&c, end);
				i--;
		};
	}while(x!=0);

	free(s);

	if(i==0)
	{
		printf("$\n");
		return EXIT_SUCCESS;
	}

	end=c;
	char **n=(char**) malloc(sizeof(char*)*i);

	for(int j=0;j<i;j++)
	{
		n[j]=end->n;
		end=end->next;
	}

	qsort(n, i, sizeof(char*), compare);

	for(int j=0;j<i;j++)
        {
                printf("%s\n", n[j]);
        }

	printf("$\n");

	for(int j=0;j<i;j++)
	{
		free(n[j]);
	}

	free(n);
	free_list(c);

	return EXIT_SUCCESS;
}

