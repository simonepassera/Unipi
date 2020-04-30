#include <stdlib.h>
#include <time.h>
#include <stdio.h>

#define N 10000
#define K 10

int main()
{
	unsigned int seed = time(NULL);
	int C[K], n;

    	for(int i=0; i<K; i++)
	{
		C[i]=0;
    	}

	for(int i=0; i<N; i++)
	{
		n = rand_r(&seed);
		C[n % K]++;
	}

    	printf("Occorrenze di:\n");

	for(int i=0; i<K; i++)
	{
		printf("%d  : %5.2f%% \n", i, (C[i]/(float)N)*100);
    	}

	printf("\n");

	return 0;
}
