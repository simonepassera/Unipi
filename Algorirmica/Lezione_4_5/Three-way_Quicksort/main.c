#include <stdlib.h>
#include <stdio.h>
#include <time.h>

int distribuzione(int *v, int p, int x, int r, int *u);
void quicksort(int *v, int p, int r);
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

	return EXIT_SUCCESS;
}

int distribuzione(int *v, int p, int x, int r, int *u)
{
	int i=p-1, t=v[x];

	v[x]=v[r];
	v[r]=t;

	*u=i;

	for(int j=p; j<r; j++)
	{
		if(v[j]<v[r])
		{
			i++;
			(*u)++;
			t=v[i];
			v[i]=v[j];

			if(i==*u)
			{
				v[j]=t;
			}
			else
			{
				v[j]=v[*u];
				v[*u]=t;
			}
		}
		else
		{
			if(v[j]==v[r])
			{
				(*u)++;
				t=v[*u];
				v[*u]=v[j];
				v[j]=t;
			}
		}
	}

	(*u)++;

	t=v[*u];
	v[*u]=v[r];
	v[r]=t;

	return i+1;
}

void quicksort(int *v, int p, int r)
{
	int x,i,u;

	if(p<r)
	{

		x=(rand()%(r-p+1))+p;

    	i=distribuzione(v, p, x, r, &u);

		quicksort(v, p, i-1);
		quicksort(v, u+1, r);
	}
}

int legge(int **a, int *len)
{

	int i;
  	scanf("%d", len);

	if(*len<=0)
	{
		return 1;
	}

  	*a = (int*) malloc(*len*sizeof(int));

	if(*a==NULL)
	{
		return 1;
	}

  	for(i=0; i<*len; i++)
	{
		scanf("%d", (*a)+i);
	}

 	return 0;
}