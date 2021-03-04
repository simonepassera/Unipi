#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <errno.h>
#include <unistd.h>

#define N 1000
#define K1 100
#define K2 200

int isNumber(const char *s, long *r)
{
   	char *e = NULL;

   	errno = 0;
   	long val = strtol(s, &e, 10);

	if(*e == '\0' && *s != '\0' && errno == 0)
	{
		if(r != NULL)
		{
			*r = val;
		}
		
		return 1;
	}
	
   	return 0;
}

int main(int argc, char *argv[])
{
	int opt, n = N, k1 = K1, k2 = K2;
	long t;
	
	while((opt=getopt(argc, argv, "n:a:b:")) != -1)
	{
		switch(opt)
		{
			case 'n':
				if(isNumber(optarg, &t))
				{
					n = t;	
				}
				break;
			case 'a':
				if(isNumber(optarg, &t))
				{
					k1 = t;
				}
				break;
			case 'b':
				if(isNumber(optarg, &t))
				{
					k2 = t;	
				}
				break;
			case '?':
				fprintf(stderr, "Usage: %s [-n <numeri random> -a <min> -b <max>\n", argv[0]);
				return EXIT_FAILURE;
		}
	}
	
	unsigned seed = time(NULL);
	int C[k2 - k1], num;

   	for(int i = 0; i < (k2 - k1); i++)
	{
		C[i] = 0;
   	}

	for(int i = 0; i < n; i++)
	{
		num = (rand_r(&seed) % (k2 - k1)) + k1;
		C[num % (k2 - k1)]++;
	}

   	printf("Occorrenze di:\n");

	for(int i = 0; i < (k2 - k1); i++)
	{
		printf("%d  : %5.2f%% \n", i + k1, C[i]/(float)n*100);
   	}

	printf("\n");

	return EXIT_SUCCESS;
}
