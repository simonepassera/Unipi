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
pl fusion(pl l1, pl l2);
void stampa(pl l);

int  main(int argc, char *argv[])
{
	pl l1=NULL, l2=NULL, l3=NULL;
	int v;

	do
	{
		scanf("%d", &v);

		if(v>=0)
		{
			inserisci(&l1,v);
		}
	}while(v >= 0);

	do
        {
                scanf("%d", &v);

                if(v>=0)
                {
                        inserisci(&l2,v);
                }
        }while(v >= 0);

	l3=fusion(l1,l2);

	stampa(l3);

	return EXIT_SUCCESS;
}

void stampa(pl l)
{
	if(l != NULL)
	{
		printf("%d --> ", l->info);

		stampa(l->next);
	}
	else
	{
		printf("NULL");
	}
}

pl fusion(pl l1, pl l2)
{
	pl n=NULL;

	while(l1!=NULL)
	{
		inserisci(&n, l1->info);
		l1=l1->next;
	}

	while(l2!=NULL)
        {
                inserisci(&n, l2->info);
                l2=l2->next;
        }

	return n;
}

void inserisci(pl *l, int v)
{
        pl n=(pl)malloc(sizeof(e));
        n->info=v;

        if(*l==NULL)
        {
                n->next=NULL;
                *l=n;
        }
        else
        {
                if((*l)->info>=v)
                {
                        n->next=*l;
                        *l=n;
                }
                else
                {
                        pl t=*l;
                        int b=1;

                        while(t->next!=NULL && b)
                        {
                                if((t->next)->info>=v)
                                {
                                        n->next=t->next;
                                        t->next=n;
                                        b=0;
                                }
                                else
                                {
                                        t=t->next;
                                }
                        }

                        if(b)
                        {
                                n->next=NULL;
                                t->next=n;
                        }
                }
        }
}
