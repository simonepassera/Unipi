#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char *argv[])
{
	pid_t pid = fork();
	
	if(pid == -1)
	{
		perror("Error");
	}

	if(pid == 0)
	{
		printf("Child: %d\n", getpid());
		sleep(180);
	}
	else
	{
		printf("Parent: %d\n", getpid());
		sleep(180);
	}
	
	return EXIT_SUCCESS;
}
