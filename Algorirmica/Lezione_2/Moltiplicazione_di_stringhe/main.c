#include <stdlib.h>
#include <stdio.h>
#include <string.h>

char * product(char *str, int k);

int main(int argc, char **argv)
{

	char s1[1001];
	int k;

	scanf("%s", s1);
	scanf("%d", &k);

	char *s=product(s1, k);

	printf("%s", s);

	free(s);

        return EXIT_SUCCESS;
}

char * product(char *str, int k)
{
	char *s=(char*)malloc((sizeof(char)*strlen(str)*k)+1);
	int i,c,j=0;

	for(i=0;i<k;i++)
	{
		c=0;

		while(str[c]!='\0')
		{
			s[j]=str[c];
			c++;
			j++;
		}
	}

	s[j]='\0';

	return s;
}
