#include <stdlib.h>
#include <stdio.h>

void insertion_sort(int *v, int l);

int main(int argc, char **argv)
{
        int n;
        scanf("%d", &n);

        int *v = (int*) malloc(sizeof(int)*n);

        for(int i=0;i<n;i++)
        {
            scanf("%d", v+i);
        }

        insertion_sort(v, n);

        for(int i=0;i<n;i++)
        {
            printf("%d\n", v[i]);
        }

		free(v);
		
        return EXIT_SUCCESS;
}

void insertion_sort(int *v, int l)
{
	int i,k,j;

	for(i=1; i<l; i++)
	{
		k=v[i];
		j=i-1;

		while(j>=0 && v[j]>k)
		{
			v[j+1]=v[j];
			j--;
		}

		v[j+1]=k;
	}
}

