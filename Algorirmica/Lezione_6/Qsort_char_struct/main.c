#include <stdlib.h>
#include <stdio.h>
#include <string.h>

typedef struct s_stringa
{
	char *s;
	int l;
}stringa;

int compare(const void *p1, const void *p2);

int main(int argc, char **argv)
{
	int n;

	scanf("%d", &n);

	stringa *v = (stringa*) malloc(sizeof(stringa)*n);

    for(int i=0; i<n; i++)
    {
		(v+i)->s = (char*) malloc(sizeof(char)*101);
        scanf("%s", (v+i)->s);

		(v+i)->l=strlen((v+i)->s);
	}

  	qsort(v, n, sizeof(stringa), compare);

    for(int i=0; i<n; i++)
    {
    	printf("%s\n", (v+i)->s);
    }

	for(int i=0; i<n; i++)
	{
		free((v+i)->s);
	}

	free(v);

	return EXIT_SUCCESS;
}

int compare(const void *p1, const void *p2)
{
	stringa *a=(stringa*)p1;
	stringa *b=(stringa*)p2;

	if(a->l == b->l)
	{
		return strcmp(a->s, b->s);
	}
	else
	{
		return a->l - b->l;
	}
}