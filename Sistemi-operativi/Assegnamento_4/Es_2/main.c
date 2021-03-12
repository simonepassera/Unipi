#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define DIM_LIMIT 512

#define CHECK_EQ(X, val, str)		\
									\
	if((X) == val)					\
	{								\
		perror(str);				\
      	exit(EXIT_FAILURE);			\
    }								\

#define CHECK_NEQ(X, val, str)		\
									\
    	if((X) != val)				\
		{							\
      		perror(str);			\
      		exit(EXIT_FAILURE);		\
    	}							\

typedef int (*F_t)(const void*, const void*, size_t);

int confronta(F_t cmp, const void *s1, const void *s2, size_t n)
{
	return cmp(s1, s2, n);
}

int main(int argc, char *argv[])
{
	if(argc != 3)
	{
		fprintf(stderr, "Use: %s <dim> create|check\n", argv[0]);
		return EXIT_FAILURE;
    }

    long dim = strtol(argv[1], NULL, 10);

	if(dim > DIM_LIMIT)
	{
		fprintf(stderr,"dim (%ld) troppo grande, deve essere <= %d\n", dim, DIM_LIMIT);
		return EXIT_FAILURE;
    }

    int create = (strncmp("create", argv[2], strlen("create")) == 0);

    if(create)
	{
		float *mat = NULL;

		CHECK_EQ(mat = malloc(dim * dim * sizeof(float)), NULL, "malloc");

		for(long i = 0; i < dim; i++)
	   	{
			for(long j = 0; j < dim; j++)
			{
				mat[i*dim + j] = (i+j)/2.0;
			}
		}

		FILE *fp1;
		CHECK_EQ(fp1 = fopen("mat_dump.dat", "w"), NULL, "fopen mat_dump.dat");

		CHECK_NEQ(fwrite(mat, sizeof(float), dim * dim, fp1), (dim * dim), "fwrite");

		fclose(fp1);

		CHECK_EQ(fp1 = fopen("mat_dump.txt", "w"), NULL, "fopen mat_dump.txt");

		for(long i = 0; i < dim; i++)
		{
			for(long j = 0; j < dim; j++)
			{
				if(fprintf(fp1, "%f\n", mat[i*dim + j]) < 0)
				{
					perror("fprintf");
					return EXIT_FAILURE;
				}
	    	}
		}

		fclose(fp1);

		free(mat);
	}
	else
	{
		float *mat = NULL;
		CHECK_EQ(mat = malloc(dim * dim * sizeof(float)), NULL, "malloc");

		float *mat2=NULL;
		CHECK_EQ(mat2 = malloc(dim * dim * sizeof(float)), NULL, "malloc");

		FILE *fp1;
		CHECK_EQ(fp1 = fopen("mat_dump.dat", "r"), NULL, "fopen mat_dump.dat");

		FILE *fp2;
		CHECK_EQ(fp2 = fopen("mat_dump.txt", "r"), NULL, "fopen max_dump.txt");

		char buf[128];

		for(long i = 0; i < dim; i++)
    	{
			for(long j = 0; j < dim; j++)
			{
				CHECK_EQ(fgets(buf, 128, fp2), NULL, "fgets");

				buf[strlen(buf) - 1] = '\0';
				mat[i*dim + j] = strtof(buf, NULL);
			}
		}

		fclose(fp2);

		CHECK_NEQ(fread(mat2, sizeof(float), dim * dim, fp1), (dim * dim), "fread");

		fclose(fp1);

		if(confronta(memcmp, mat, mat2, dim * dim * sizeof(float)) != 0)
		{
			free(mat);
			free(mat2);

			fprintf(stderr, "Le due matrici non corrispondono\n");

			return EXIT_FAILURE;
		}

		fprintf(stdout, "Le due matrici sono uguali\n");

		free(mat);
		free(mat2);
	}

	return EXIT_SUCCESS;
}
