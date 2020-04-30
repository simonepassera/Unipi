#include <stdio.h>
#include <stdlib.h>
#include "tokenizer.h"

int main(int argc, char *argv[])
{
	for(int i=1; i<argc; i++)
	{
		tokenizer_r(argv[i], stdout);
	}

	return EXIT_SUCCESS;
}

