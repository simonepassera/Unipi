#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int compare(const void *x, const void *y);

int main(int argc, char **argv)
{
	int n;

	scanf("%d", &n);

	char **v = (char**) malloc(sizeof(char*)*n);

    for(int i=0; i<n; i++)
    {
		v[i] = (char*) malloc(sizeof(char)*101);
        scanf("%s", v[i]);
	}

  	qsort(v, n, sizeof(char*), compare);

    for(int i=0; i<n; i++)
    {
        printf("%s\n", v[i]);
    }

	for(int i=0; i<n; i++)
	{
		free(v[i]);
	}

	free(v);

    return EXIT_SUCCESS;
}

int compare(const void *x, const void *y)
{
	char **x1=(char**)x;
	char **y1=(char**)y;

	return strcmp(*x1, *y1)*(-1);
}