#include <stdio.h>
#include <string.h>
#include <tokenizer.h>

void tokenizer(char *str, FILE *out)
{
	char *token = strtok(str, " ");

	while(token != NULL)
	{
		fprintf(out, "%s\n", token);
		token = strtok(NULL, " ");
    }
}

void tokenizer_r(char *str, FILE *out)
{
	char *tmpstr;
	char *token = strtok_r(str, " ", &tmpstr);

	while(token != NULL)
	{
		fprintf(out, "%s\n", token);
		token = strtok_r(NULL, " ", &tmpstr);
    }
}
