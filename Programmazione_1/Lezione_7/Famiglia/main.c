#include <stdlib.h>
#include <stdio.h>

struct persona
{
	char nome[10];
	struct persona * mamma;
	struct persona * babbo;
};

typedef struct persona Persona;

void famiglia(Persona *p);

int main(int argc, char *argv[])
{
	Persona *p=(Persona*) malloc(sizeof(Persona)*3);
	char s[30];
	int i,c;

	scanf("%s", s);

	for(i=0;i<10;i++)
	{
		p->nome[i]=s[i];
	}

	for(i=10,c=0;i<20;i++,c++)
	{
		(p+1)->nome[c]=s[i];
	}

	for(i=20,c=0;i<30;i++,c++)
        {
                (p+2)->nome[c]=s[i];
        }

	for(i=1;i<3;i++)
        {
                (p+i)->mamma=NULL;
		(p+i)->babbo=NULL;
        }

	p->mamma=(p+1);
	p->babbo=(p+2);

	famiglia(p);
	famiglia(p+1);
	famiglia(p+2);

	free(p);

	return EXIT_SUCCESS;
}

void famiglia(Persona *p)
{
	printf("%s\n", p->nome);

	if(p->mamma!=NULL)
	{
		printf("%s", p->mamma->nome);
	}
	else
	{
		printf("Sconosciuto");
	}

	if(p->babbo!=NULL)
        {
                printf("%s\n", p->babbo->nome);
        }
        else
        {
                printf("Sconosciuto\n");
        }
}
