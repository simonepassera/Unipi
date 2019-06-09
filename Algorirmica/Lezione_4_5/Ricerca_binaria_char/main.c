#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int ricerca_binaria_char(char **v, int i, int f, char *s);

int main(int argc, char **argv)
{
    int n;
    scanf("%d", &n);

    char **v = (char**) malloc(sizeof(char*)*n), *s = (char*) malloc(sizeof(char)*101);

    for(int i=0; i<n; i++)
    {
		v[i] = (char*) malloc(sizeof(char)*101);
        scanf("%s", v[i]);
    }

	int r, j=0, f=n, *a=(int*) malloc(sizeof(int)*n);

	do
	{
		scanf("%d", &r);

		if(r)
		{
			scanf("%s", s);

			if(j==f)
			{
				f+=10;
				a=(int *) realloc(a, f);
			}

			a[j]=ricerca_binaria_char(v, 0, n-1, s);
			j++;
		}
	}while(r);

    for(int i=0; i<j; i++)
    {
        printf("%d\n", a[i]);
    }

	for(int i=0; i<n; i++)
	{
		free(v[i]);
	}

	free(v);
	free(a);
	free(s);

    return EXIT_SUCCESS;
}

int ricerca_binaria_char(char **v, int i, int f, char *s)
{
	int m;

	if(i>f)
	{
		return -1;
	}
	else
	{
		m=(i+f)/2;

		if(strcmp(s,v[m])==0)
		{
			return m;
		}
		else
		{
			if(strcmp(s,v[m])>0)
			{
				return ricerca_binaria_char(v, m+1, f, s);
			}
			else
			{
				return ricerca_binaria_char(v, i, m-1, s);
			}
		}
	}
}
