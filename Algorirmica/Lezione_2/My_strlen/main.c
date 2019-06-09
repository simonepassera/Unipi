#include <stdlib.h>
#include <stdio.h>

int my_strlen(char *s);

int main(int argc, char **argv)
{

	char s[1001];

	scanf("%s", s);

	printf("%d", my_strlen(s));

    return EXIT_SUCCESS;
}

int my_strlen(char *s)
{
	int i=0;

	while(s[i]!='\0')
	{
		i++;
	}

	return i;
}
