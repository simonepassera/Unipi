#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define PASSWD_FILE "/etc/passwd"
#define MAX_LEN 1024

int main(int argc, char *argv[])
{
	if(argc != 2)
	{
		fprintf(stderr,	"Too few arguements!\nUse: %s filename\n", argv[0]);
		return -1;
    	}

	FILE *fd=NULL;
	FILE *fout=NULL;

	char *buffer= NULL;

    	if((fd=fopen(PASSWD_FILE, "r")) == NULL)
	{
		perror("opening password file");
		goto error;
    	}

    	if((fout=fopen(argv[1], "w")) == NULL)
	{
		perror("opening output file");
		goto error;
    	}

 	if((buffer=(char*)malloc(MAX_LEN*sizeof(char))) == NULL)
	{
		perror("malloc buffer");
		goto error;
	}

	char *c;

    	while(fgets(buffer, MAX_LEN, fd) != NULL)
	{
		if((c=strchr(buffer, '\n')) == NULL)
		{
			fprintf(stderr, "something went wrong, please consider to increase MAX_LEN (%d)\n", MAX_LEN);
	    		goto error;
		}

		if((c=strchr(buffer, ':')) == NULL)
		{
			fprintf(stderr, "wrong file format\n");
	    		goto error;
		}

		*c='\0';

		fprintf(fout, "%s\n", buffer);
    	}

 	fclose(fd);
    	fclose(fout);
    	free(buffer);

    	return 0;

error:
	if(fd != NULL) fclose(fd);
    	if(fout != NULL) fclose(fout);
    	if(buffer != NULL) free(buffer);

	exit(EXIT_FAILURE);
}
