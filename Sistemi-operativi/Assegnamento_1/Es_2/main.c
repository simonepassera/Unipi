#include <stdarg.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define REALLOC_INC 16
#define RIALLOCA(buf, newsize)       				\
		char *oldbuf = buf;							\
        buf = realloc(buf, newsize * sizeof(char)); \
	    if(buf == NULL)                				\
	    {                            				\
	       perror("realloc"); 		 				\
	       free(oldbuf);							\
		   exit(EXIT_FAILURE); 	     				\
	    }

char *mystrcat(char *buffer, size_t buffer_size, char *first, ...)
{
	va_list ap;
    va_start(ap, first);

    if(buffer_size < strlen(first) + 1)
	{
		RIALLOCA(buffer, buffer_size + strlen(first) + 1 + REALLOC_INC);
		buffer_size += strlen(first) + 1 + REALLOC_INC;
    }

   	strncat(buffer, first, strlen(first));
   	char *s = NULL;

   	while((s = va_arg(ap, char*)) != NULL)
	{
		if(buffer_size < strlen(buffer) + strlen(s) + 1)
		{
			RIALLOCA(buffer, strlen(buffer) + strlen(s) + 1 + REALLOC_INC);
    		buffer_size = strlen(buffer) + strlen(s) + 1 + REALLOC_INC;
		}

		strncat(buffer, s, strlen(s));
   	}

	va_end(ap);

	return buffer;
}

int main(int argc, char *argv[])
{
	if(argc != 7)
	{
		printf("Requires 6 args!\n");
		return EXIT_FAILURE;
    }

	char *buffer = NULL;
    RIALLOCA(buffer, REALLOC_INC);

	buffer[0] = '\0';
    buffer = mystrcat(buffer, REALLOC_INC, argv[1], argv[2], argv[3], argv[4], argv[5], argv[6], NULL);

	printf("%s\n", buffer);

	free(buffer);

	return EXIT_SUCCESS;
}

