#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

void strtoupper(const char *in, size_t len, char *out)
{
	int i = 0;
	
	while(in[i] != '\0' && i < len)
	{
		out[i] = toupper((unsigned char) in[i]);
		i++;
	}
	
	if(i == len)
	{
		out[len] = '\0';
	}
	else
	{
		out[i] = '\0';
	}
}

int main(int argc, char *argv[])
{
	if(argc == 1)
	{
		fprintf(stderr, "Usage: %s STRING [STRING]...\n", argv[0]);
		return EXIT_FAILURE;
    }

    char *s = NULL;
    char *out = NULL;
    int index = 1;

    while(index < argc)
    {
    	s = argv[index];

    	if((out = malloc(sizeof(char) * (strlen(s) + 1))) == NULL)
    	{
    		perror("malloc");
    		return EXIT_FAILURE;
    	}

    	strtoupper(s, strlen(s), out);
    	printf("%s --> %s\n", s, out); 
    	free(out);

    	index++; 	
    }

	return EXIT_SUCCESS;
}

