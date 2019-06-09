#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>

int distribuzione(char **v, int p, int x, int r);
void quicksort(char **v, int p, int r);

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

	srand(time(NULL));
  	quicksort(v, 0, n-1);

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

int distribuzione(char **v, int p, int x, int r)
{
	int i=p-1;
	char *t=v[x];

	v[x]=v[r];
	v[r]=t;

	for(int j=p; j<r; j++)
	{
		if(strcmp(v[j], v[r])<=0)
		{
			i++;
			t=v[i];
			v[i]=v[j];
			v[j]=t;
		}
	}

	t=v[i+1];
	v[i+1]=v[r];
	v[r]=t;

	return i+1;
}

void quicksort(char **v, int p, int r)
{
	int x,i;

	if(p<r)
	{

		x=(rand()%(r-p+1))+p;

    	i=distribuzione(v, p, x, r);

		quicksort(v, p, i-1);
		quicksort(v, i+1, r);
	}
}