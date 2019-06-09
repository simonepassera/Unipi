#include <stdlib.h>
#include <stdio.h>
#include <string.h>

typedef struct s_g
{
	char *s;
	char *a;
}g;

int compare(const void *x, const void *y)
{
	char *x1=(char*)x;
	char *y1=(char*)y;

	return *x1 - *y1;
}

int compare2(const void *x, const void *y)
{
        g *x1=(g*)x;
        g *y1=(g*)y;

        return strcmp(x1->a, y1->a);
}

int compare3(const void *x, const void *y)
{
        g *x1=(g*)x;
        g *y1=(g*)y;

        return strcmp(x1->s, y1->s);
}

char * anagramma(char *t)
{
	char *a=(char*) malloc(sizeof(char)*20);

	strcpy(a, t);

	qsort(a, strlen(a), sizeof(char), compare);

	return a;
}

int main(int argc, char **argv)
{
	int n;

	scanf("%d", &n);

	g *v = (g*) malloc(sizeof(g)*n);

    for(int i=0; i<n; i++)
    {
		(v+i)->s=(char*) malloc(sizeof(char)*20);
        scanf("%s", (v+i)->s);

		(v+i)->a=anagramma((v+i)->s);
	}

	qsort(v, n, sizeof(g), compare2);

	int i=0, j=0;

	while(j<n)
	{
		while(j<(n-1) && strcmp((v+j)->a, (v+j+1)->a)==0)
		{
			j++;
		}

		qsort(v+i, j+1-i, sizeof(g), compare3);

		for(int k=i; k<=j; k++)
		{
			printf("%s ", (v+k)->s);
		}

		printf("\n");

		j++;
		i=j;
	}

	for(int i=0; i<n; i++)
	{
		free((v+i)->s);
		free((v+i)->a);
	}
	
	free(v);

	return EXIT_SUCCESS;
}