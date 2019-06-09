#include <stdlib.h>
#include <stdio.h>

void primoultimopari(int arr[], int dim, int *primaocc,int *ultimaocc);

int main(int argc, char *argv[])
{
        int v[7],i,primaocc=-1,ultimaocc=-1;

	for(i=0;i<7;i++)
	{
		scanf("%d", v+i);
	}

	primoultimopari(v, 7, &primaocc, &ultimaocc);

	printf("%d %d", primaocc, ultimaocc);

        return EXIT_SUCCESS;
}

void primoultimopari(int arr[], int dim, int *primaocc,int *ultimaocc)
{
	int i,b=1;

	for(i=0;i<dim;i++)
	{
		if(arr[i]%2==0)
		{
			if(b)
			{
				*primaocc=i;
				*ultimaocc=i;
				b=0;
			}
			else
			{
				*ultimaocc=i;
			}
		}
	}
}
