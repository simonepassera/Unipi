#include <stdlib.h>
#include <stdio.h>

void minmax(int v[], int l, int *min, int *max);

int main(int argc, char **argv)
{

	int v[10],i=0,min,max;

	while(i<10)
	{
		scanf("%d", v+i);
		i++;
	}

	minmax(v,10,&min,&max);

	printf("%d\n%d\n%d\n%d", min,v[min],max,v[max]);

    	return EXIT_SUCCESS;
}

void minmax(int v[], int l, int *min, int *max)
{
	*min=0;
	*max=0;

	for(int i=1;i<l;i++)
	{
		if(v[i]>v[*max])
		{
			*max=i;
		}
		else
		{
			if(v[i]<v[*min])
			{
				*min=i;
			}
		}
	}
}
