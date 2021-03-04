#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[])
{
	if(argc != 3)
	{
		fprintf(stderr, "Usage: %s stringa1 stringa2\n", argv[0]);
		return EXIT_FAILURE;
	}

	char *save1, *save2, *c = malloc(sizeof(char) * (strlen(argv[2]) + 1));

	char *token1 = strtok_r(argv[1], " ", &save1);

    while(token1 != NULL)
	{
		printf("%s\n", token1);

		strcpy(c, argv[2]);

		char *token2 = strtok_r(c, " ", &save2);

		while(token2 != NULL)
		{
			printf("%s\n", token2);
	   		token2 = strtok_r(NULL, " ", &save2);
		}

		token1 = strtok_r(NULL, " ", &save1);
    }

	free(c);

	return EXIT_SUCCESS;
}
