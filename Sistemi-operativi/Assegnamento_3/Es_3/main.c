#include <stdlib.h>
#include <stdio.h>

#define dimN 16
#define dimM 8

#define CHECK_PTR(p, str)		\
								\
	if(p == NULL)				\
	{							\
		perror(str);			\
		exit(EXIT_FAILURE);		\
	}							\

#define ELEM(p, i, j) p[i*dimM+j]

#define PRINTMAT(mat)								\
													\
	printf("\nStampo %s la matrice %s:\n\n", #mat);	\
													\
	for(size_t i=0; i<dimN; i++)					\
	{										 		\
	 	for(size_t j=0; j<dimM; j++)				\
	    {											\
			printf("%4ld ", ELEM(mat, i, j));		\
     	}											\
													\
		printf("\n");								\
	}												\

int main()
{
	long *M = malloc(dimN*dimM*sizeof(long));

	CHECK_PTR(M, "malloc");

	for(size_t i=0; i<dimN; i++)
	{
		for(size_t j=0; j<dimM; j++)
		{
			ELEM(M, i, j) = i+j;
		}
	}

	PRINTMAT(M);

	free(M);

	return EXIT_SUCCESS;
}
