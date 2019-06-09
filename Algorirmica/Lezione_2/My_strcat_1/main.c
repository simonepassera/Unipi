#include <stdlib.h>
#include <stdio.h>
#include <string.h>

char * my_strcat1(char *s1, char *s2);

int main(int argc, char **argv)
{

	char s1[1001],s2[1001];

	scanf("%s", s1);
	scanf("%s", s2);

	printf("%s", my_strcat1(s1, s2));

    	return EXIT_SUCCESS;
}

char * my_strcat1(char *s1, char *s2)
{
	int i=0,c=0;
	char *s=(char *)malloc(sizeof(char)*(strlen(s1)+strlen(s2)+1));

	while(s1[i]!='\0')
	{
		s[i]=s1[i];
		i++;
	}

	while(s2[c]!='\0')
    	{
        	s[i]=s2[c];
        	c++;
		i++;
    	}

	s[i]='\0';

	return s;
}
