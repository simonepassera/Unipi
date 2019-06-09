#include <stdlib.h>
#include <stdio.h>

void min_azzera(int *v,int *m);

int main(int argc, char *argv[])
{
        int v[10],i,m;

	for(i=0;i<10;i++)
	{
		scanf("%d", v+i);
	}

	min_azzera(v,&m);

	for(i=0;i<10;i++)
	{
		printf("%d\n", v[i]);
	}

	printf("%d",m);

        return EXIT_SUCCESS;
}

void min_azzera(int *v,int *m)
{
	int i;

	*m=v[0];

	for(i=0;i<10;i++)
	{
		if(v[i]<*m)
                {
			*m=v[i];
                }

		if(i%2 != 0)
		{
			v[i]=0;
		}
	}
}
