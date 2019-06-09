#include <stdlib.h>
#include <stdio.h>

void reset(int array[], int len);
void add(int array[], int len, int val);

int main(int argc, char **argv)
{
	int v[10],n=0;

	reset(v, 10);

	while(n!=-1)
	{
		scanf("%d", &n);
		add(v, 10, n);
	}

	for(int i=0;i<10;i++)
	{
		printf("%d\n", v[i]);
	}

    return EXIT_SUCCESS;
}

void reset(int array[], int len)
{
	for(int i=0;i<len;i++)
	{
		array[i]=0;
	}
}

void add(int array[], int len, int val)
{
	if(val>=0 && val<=(len-1))
	{
		array[val]++;
	}
}

