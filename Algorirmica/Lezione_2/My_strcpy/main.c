#include <stdlib.h>
#include <stdio.h>
#include <string.h>

char * my_strcpy(char *dest, char *src);

int main(int argc, char **argv)
{

	char s1[1001];

	scanf("%s", s1);

	char s2[strlen(s1)+1];

	printf("%s", my_strcpy(s2, s1));

    return EXIT_SUCCESS;
}

char * my_strcpy(char *dest, char *src)
{
	int l=strlen(src),i;

	for(i=0;i<l;i++)
	{
		dest[i]=src[i];
	}

	dest[i]='\0';

	return dest;
}
