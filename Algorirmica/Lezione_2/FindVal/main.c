#include <stdlib.h>
#include <stdio.h>

int * FindVal(int a[], int len, int val);

int main(int argc, char **argv)
{

	int v[10],i=0,*b,n;

	while(i<10)
	{
		scanf("%d", v+i);
		i++;
	}

	scanf("%d", &n);

	b=FindVal(v, 10, n);

	if(b!=NULL)
	{
		printf("trovato");
	}
	else
	{
		printf("non trovato");
	}

        return EXIT_SUCCESS;
}

int * FindVal(int a[], int len, int val)
{
	for(int i=0;i<len;i++)
	{
		if(a[i]==val)
		{
			return a+i;
		}
	}

	return NULL;
}
