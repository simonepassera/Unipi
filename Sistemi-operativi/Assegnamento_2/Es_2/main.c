#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <unistd.h>

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

int arg_n(const char *arg)
{
	long int_arg;
	
	if(isNumber(arg, &int_arg))
	{
		printf("-n : %ld\n", int_arg);
		return 0;
	}
	else
	{		
		return -1;
	}
}

int arg_m(const char *arg)
{
	long int_arg;
	
	if(isNumber(arg, &int_arg))
	{
		printf("-m : %ld\n", int_arg);
		return 0;
	}
	else
	{		
		return -1;
	}
}

int arg_o(const char *arg)
{
	if(*arg != '\0')
	{
		printf("-o : %s\n", arg);
		return 0;
	}
	else
	{		
		return -1;
	}
}

int arg_h(const char *argv_0)
{
	fprintf(stderr, "Usage: %s -n <num. intero> -m <num. intero> -o <stringa> -h\n", argv_0);
	return 0;
}

typedef int (*F_arg)(const char*);

int main(int argc, char *argv[])
{
	F_arg V[4] = {arg_h, arg_m, arg_n, arg_o};
	int opt;

	while((opt=getopt(argc, argv, "n:m:o:h")) != -1)
	{
		switch(opt)
		{
			case '?':
				break;
			default:
				if(V[opt%4]((optarg == NULL ? argv[0] : optarg)) == -1)
				{
					printf("Invalid argument (%c)\n", opt);
				}
		}
   	}

   	return EXIT_SUCCESS;
}
