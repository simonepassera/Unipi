#include <stdio.h>
#include <stdlib.h>

#ifndef INIT_VALUE
#error Please compile with the flag -DINIT_VALUE=n
#endif

int somma(int x)
{
    static int s = INIT_VALUE;
    s += x;

    return s;
}

int somma_r(int x, int *s)
{
    *s += x;

    return *s;
}

int main(int argc, char *argv[])
{
	if(argc != 2)
	{
		fprintf(stderr, "usa: %s N\n", argv[0]);
		return EXIT_FAILURE;
    }

    int N = strtol(argv[1], NULL, 10);

	if(N<=0 || N>10)
	{
		fprintf(stderr, "N deve essere maggiore di 0 e minore o uguale a 10\n");
		return EXIT_FAILURE;
    }

    int s = INIT_VALUE;

	for(int i=0; i<N; i++)
	{
		int val;
		fprintf(stdout, "Inserisci un numero (%d rimasti): ", N-i);

		if(fscanf(stdin, "%d", &val) != 1)
		{
	   	 	perror("fscanf");
	    	return EXIT_FAILURE;
		}

		fprintf(stdout, "somma   %d\n", somma(val));
		fprintf(stdout, "somma_r %d\n", somma_r(val, &s));
    }

    return EXIT_SUCCESS;
}
