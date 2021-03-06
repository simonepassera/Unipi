#define _XOPEN_SOURCE // getopt()

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <unistd.h>

#define DEFAULT_MAX_LINE 2048

int wcount(const char *p)
{
   	int count = 0, flag = 0;
   	
	while(*p != '\0' && (iscntrl(*p) || isspace(*p))) p++;

   	while(*p != '\0')
	{
		if(isspace(*p))
		{
			if(!isspace(*(p-1)) && !iscntrl(*(p-1)))
			{
				count++;
				flag = 0;
    		}
		}
		else
		{
			flag = 1;
		}

		p++;
   	}

	return flag ? count+1 : count;
}

int main(int argc, char *argv[])
{
	if(argc == 1)
	{
		fprintf(stderr, "Usage: %s [-l -w -m <num> ] <filename> [<filename> ...]\n", argv[0]);
		fprintf(stderr, " -l conta il numero di linee\n");
		fprintf(stderr, " -w conta il numero di parole\n");
		fprintf(stderr, " -m <num> setta la lunghezza massima di una linea a <num>\n");

		return EXIT_FAILURE;
   	}

	int countline = 0, countword = 0;
   	int opt;
   	long max_line = DEFAULT_MAX_LINE;
	char *endptr;

	while((opt = getopt(argc, argv, "lwm:")) != -1)
	{
		switch(opt)
		{
			case 'l':
				countline = 1;
				break;
			case 'w':
				countword = 1;
				break;
			case 'm':
				max_line = strtol(optarg, &endptr, 10);

				if(*endptr != '\0' || *optarg == '\0')
				{
					fprintf(stderr, "%s: option requires an argument -- 'm'\n", argv[0]);
					fprintf(stderr, "Usage: %s [-l -w -m <num> ] <filename> [<filename> ...]\n", argv[0]);
					return EXIT_FAILURE;
				}

				break;
			default:
				fprintf(stderr, "Usage: %s [-l -w -m <num> ] <filename> [<filename> ...]\n", argv[0]);
				return EXIT_FAILURE;
		}
   	}

	if(optind == argc)
	{
		fprintf(stderr, "Usage: %s [-l -w -m <num> ] <filename> [<filename> ...]\n", argv[0]);
        return EXIT_FAILURE;
	}

	if(countword+countline == 0)
	{
		countline = 1;
		countword = 1;
	}

    char *line = malloc(max_line*sizeof(char));

	if(line == NULL)
	{
		perror("malloc");
		return EXIT_FAILURE;
    }

	while(argv[optind] != NULL)
	{
		FILE *f;

		long numline, numword;
		char *filename = argv[optind];

		if ((f=fopen(filename, "r")) == NULL)
		{
			perror(filename);
	   		return EXIT_FAILURE;
		}

		numline=0;
		numword=0;

		while(fgets(line, max_line, f) != NULL)
		{
			if(countline && line[strlen(line)-1] == '\n')
			{
				numline++;
	   		}

			if(countword)
			{
				numword += wcount(line);
			}
		}

		fclose(f);

		if(countline && !countword)
		{
			printf("%4ld %s\n", numline, argv[optind]);
		}

		if(!countline && countword)
		{
			printf("%4ld %s\n", numword, argv[optind]);
		}

		if(countline && countword)
		{
			printf("%4ld %4ld %s\n", numline, numword, argv[optind]);
   		}

		optind++;
	}

   	free(line);

   	return EXIT_SUCCESS;
}
