#include <stdlib.h>
#include <stdio.h>

struct gatto
{
	int cod;
	int eta;
	float peso;
	enum cibo
	{
		crocchette,
		scatolette,
		tonno
	}cibo;
};

int main(int argc, char *argv[])
{
	int i;
	float m=0;
	char *s=NULL;
	struct gatto *g=(struct gatto*) malloc(sizeof(struct gatto)*4);

	for(i=0;i<4;i++)
	{
		scanf("%d", &(g+i)->cod);
		scanf("%d", &(g+i)->eta);
		scanf("%f", &(g+i)->peso);
                scanf("%d", &(g+i)->cibo);
	}

	for(i=0;i<4;i++)
	{
		m+=(g+i)->peso;
	}

	m/=4;

	for(i=0;i<4;i++)
	{
		switch((g+i)->cibo)
		{
			case 0:
				s="crocchette";
				break;
			case 1:
				s="scatolette";
				break;
			case 2:
				s="tonno";
		}

		if((g+i)->eta<4 && (g+i)->peso>m)
		{
			printf("%d %s\n", (g+i)->cod, s);
		}
	}

	free(g);

	return EXIT_SUCCESS;
}
