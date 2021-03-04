#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <unistd.h>

int isNumber(const char *s)
{
   	char *e = NULL;

   	errno = 0;
   	long val = strtol(s, &e, 10);

	if(*e == '\0' && *s != '\0' && errno == 0)
	{
		return 1;
	}

   	return 0;
}

int main(int argc, char *argv[])
{
	int opt;

	while((opt=getopt(argc, argv, "n:m:o:h")) != -1)
	{
		switch(opt)
		{
			case 'n':
				if(isNumber(optarg))
				{
					printf("-n : %s\n", optarg);
				}
				else
				{
					printf("Invalid argument (n)\n");
				}

				break;
			case 'm':
				if(isNumber(optarg))
				{
					printf("-m : %s\n", optarg);
				}
				else
				{
					printf("Invalid argument (m)\n");
				}
				
				break;
			case 'o':
				printf("-o : %s\n", optarg);
				break;
			case 'h':
				fprintf(stderr, "Usage: %s -n <num. intero> -m <num. intero> -o <stringa> -h\n", argv[0]);
				exit(EXIT_FAILURE);
		}
   	}

   	return EXIT_SUCCESS;
}
