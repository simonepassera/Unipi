#include <stdlib.h>
#include <stdio.h>
#include <string.h>

char * my_strcat(char *s1, char *s2);

int main(int argc, char **argv)
{

	char s1[1001],s2[1001];

	scanf("%s", s1);
	scanf("%s", s2);

	printf("%s", my_strcat(s1, s2));

        return EXIT_SUCCESS;
}

char * my_strcat(char *s1, char *s2)
{
	if(strlen(s1)+strlen(s2)>1000)
	{
		return NULL;
	}

	int i=strlen(s1), c=0;

	while(s2[c]!='\0')
        {
                s1[i]=s2[c];
                c++;
		i++;
        }

	s1[i]='\0';

	return s1;
}
