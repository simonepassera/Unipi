#include <stdlib.h>
#include <stdio.h>

struct elemento
{
	int info;
	struct elemento *next;
};

typedef struct elemento e;
typedef e *pl;

void inserisci(pl *l, int v);
int pari(pl l);
int dispari(pl l);

int  main(int argc, char *argv[])
{
	pl l=NULL;
	int v;

	do
	{
		scanf("%d", &v);

		if(v>=0)
		{
			inserisci(&l,v);
		}
	}while(v>=0);

	printf("%d\n%d", dispari(l), pari(l));

	return EXIT_SUCCESS;
}

int dispari(pl l)
{
        if(l!=NULL)
        {
                if(l->info%2 != 0)
                {
                        return l->info;
                }
                else
                {
                        return dispari(l->next);
                }
        }
        else
        {
                return -1;
        }
}

int pari(pl l)
{
	if(l!=NULL)
	{
		if(l->info%2 == 0)
		{
			return l->info;
		}
		else
		{
			return pari(l->next);
		}
	}
	else
	{
		return -1;
	}
}

void inserisci(pl *l, int v)
{
	pl n=(pl)malloc(sizeof(e)),t=*l;
	n->info=v;
	n->next=NULL;

	if(*l==NULL)
	{
		*l=n;
	}
	else
	{
		while(t->next!=NULL)
		{
			t=t->next;
		}

		t->next=n;
	}
}
