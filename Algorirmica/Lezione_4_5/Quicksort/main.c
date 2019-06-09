#include <stdlib.h>
#include <stdio.h>
#include <time.h>

int distribuzione(int *v, int p, int x, int r);
void quicksort( int *v, int p, int r);
int legge(int **a, int *len);

int main(int argc, char **argv)
{
	int i, n, *v;

	if(legge(&v, &n))
 	{
		return EXIT_FAILURE;
	}

	srand(time(NULL));
  	quicksort(v, 0, n-1);

  	for(i=0; i<n; i++)
	{
		printf("%d ", v[i]);
	}

	free(v);
	
	return EXIT_SUCCESS;
}

int distribuzione(int *v, int p, int x, int r)
{
	int i=p-1, t=v[x];

	v[x]=v[r];
	v[r]=t;

	for(int j=p; j<r; j++)
	{
		if(v[j]<=v[r])
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

void quicksort( int *v, int p, int r)
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

int legge(int **a, int *len)
{
  	scanf("%d", len);

	if(*len<=0)
	{
		return 1;
	}

  	*a=(int*) malloc(*len*sizeof(int));

	if(*a==NULL)
	{
		return 1;
	}

  	for(int i=0; i<*len; i++)
	{
		scanf("%d", (*a)+i);
	}

 	return 0;
}