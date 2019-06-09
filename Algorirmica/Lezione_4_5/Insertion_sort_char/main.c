#include <stdlib.h>
#include <stdio.h>
#include <string.h>

void insertion_sort_char(char **v, int l);

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

        insertion_sort_char(v, n);

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

void insertion_sort_char(char **v, int l)
{
	int i, j;
	char *k;

	for(i=1; i<l; i++)
	{
		k=v[i];
		j=i-1;

		while((j>=0) && (strcmp(v[j],k)>0))
		{
			v[j+1]=v[j];
			j--;
		}

		v[j+1]=k;
	}
}
