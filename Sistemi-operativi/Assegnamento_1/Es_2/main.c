#include <stdio.h>
#include <stdlib.h>
#include <string.h>

long isNumber(const char *s)
{
   	char *e = NULL;
   	long val = strtol(s, &e, 0);

	if(e != NULL && *e == '\0')
	{
		return val;
	}

   	return -1;
}

void print_usage(const char *programname)
{
    printf("usage: %s -n <num> -m <num> -s <string> -h\n", programname);
}

int main(int argc, char **argv)
{
	char *programname = argv[0];

    	if(argc == 1)
	{
		printf("No args!\n");
		return EXIT_SUCCESS;
    	}

    	int foundn = 0, foundm = 0, founds = 0;
    	long narg = -1, marg = -1;
    	char *sarg = NULL, c;

	while(--argc > 0)
	{
		if((*++argv)[0] == '-')
		{
			while((c = *++argv[0]) == '-');

			switch(c)
			{
	    			case 'n':
				{
					foundn = 1;

					if(argv[0][1] == '\0')
					{
						++argv, --argc;

						if(argv[0] == NULL || (narg = isNumber(argv[0]))==-1)
						{
							foundn = 0;
							printf("Invalid argument (n)\n");
		    				}
					}
					else
					{
						if((narg = isNumber(&argv[0][1]))==-1)
						{
							foundn = 0;
							printf("Invalid argument (n)\n");
		    				}
					}
				}

				break;

				case 'm':
				{
					foundm = 1;

					if(argv[0][1] == '\0')
					{
						++argv, --argc;

						if(argv[0] == NULL || (marg = isNumber(argv[0]))==-1)
						{
							foundm = 0;
							printf("argomento m non valido\n");
		    				}
					}
					else
					{
						if((marg = isNumber(&argv[0][1]))==-1)
						{
							foundm = 0;
							printf("argomento m non valido\n");
		    				}
					}
				}

				break;

				case 's':
				{
					founds = 1;

					if(argv[0][1] == '\0')
					{
						++argv, --argc;
		    				sarg = strdup(argv[0]);
					}
					else
					{
						sarg = strdup(&argv[0][1]);
					}

					if(sarg == NULL)
					{
						founds = 0;
		    				printf("Invalid argument s\n");
					}
				}

				break;

		    		case 'h':
				{
					print_usage(programname);

					free(sarg);

					return EXIT_SUCCESS;
	    			}

				break;

	    			default: printf("Invalid argument (%c)\n", c);
			}
		}
	}

	if(foundn)
	{
		printf("-n: %ld\n", narg);
    	}

	if(foundm)
	{
		printf("-m: %ld\n", marg);
    	}

	if(founds)
	{
		printf("-s: %s\n", sarg);
	}

	free(sarg);

	return EXIT_SUCCESS;
}
