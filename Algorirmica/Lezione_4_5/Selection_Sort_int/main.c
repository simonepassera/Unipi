#include <stdlib.h>
#include <stdio.h>

void selection_sort(int *v, int l);

int main(int argc, char **argv)
{
	int n;
	scanf("%d", &n);

    int *v = (int*) malloc(sizeof(int)*n);
    
    for(int i=0;i<n;i++)
    {
        scanf("%d", v+i);
    }

	selection_sort(v, n);

    for(int i=0;i<n;i++)
    {
        printf("%d\n", v[i]);
    }
    
    free(v);

    return EXIT_SUCCESS;
}

void selection_sort(int *v, int l)
{
	int i,m,j,t;

	for(i=0; i<l; i++)
	{
		m=i;

		for(j=i+1; j<l; j++)
		{
			if(v[j]<v[m])
			{
				m=j;
			}
		}

		t=v[m];
		v[m]=v[i];
		v[i]=t;
	}
}
