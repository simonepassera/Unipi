#include <stdlib.h>
#include <stdio.h>

int compare(const void *x, const void *y);
void qsort_int(int *v, int n);

int main(int argc, char *argv)
{
	int n;
	scanf("%d", &n);

	int *v=(int*) malloc(sizeof(int)*n);

    for(int i=0; i<n; i++)
    {
        scanf("%d", v+i);
    }

	qsort(v, n, sizeof(int), compare);

  	for(int i=0; i<n; i++)
	{
		printf("%d\n", v[i]);
	}

	free(v);

	return EXIT_SUCCESS;
}

int compare(const void *x, const void *y)
{
	int *a=(int*)x, *b=(int*)y, ta=0, tb=0;
	
	if(*a%2==0)
	{
		ta=1;
	}
	
	if(*b%2==0)
	{
		tb=1;
	}
	
	if(ta && tb)
	{
		return *a-*b;
	}
	else
	{
		if(!ta && !tb)
		{
			return *b-*a;
		}
		else
		{
			if(ta)
			{
				return -1;
			}
			else
			{
				return 1;
			}
		}
	}
}