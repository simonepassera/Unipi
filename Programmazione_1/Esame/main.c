#include <stdio.h>
#include <stdlib.h>

//List structure:
struct El {
	int  info;
	struct  El *next;
};

typedef struct El  ElementoLista;
typedef ElementoLista *ListaDiElementi;


// Functions/Procedure  to be implemented:

void readList(ListaDiElementi *lista)
{
	int v,b=1;

	do
	{
		scanf("%d", &v);

		if(*lista != NULL)
		{
			ListaDiElementi t=*lista;

			while(t->next != NULL)
			{
				t=t->next;
			}

			if(t->info <= v)
			{
				ListaDiElementi n=(ListaDiElementi) malloc(sizeof(ElementoLista));

				n->next=NULL;
				n->info=v;

				t->next=n;
			}
			else
			{
				b=0;
			}
		}
		else
		{
			ListaDiElementi n=(ListaDiElementi) malloc(sizeof(ElementoLista));

			n->next=NULL;
			n->info=v;

			*lista=n;
		}
	}while(b);
}

void cancellaDuplicates(ListaDiElementi *lista)
{
	if(*lista != NULL)
	{
		if((*lista)->next != NULL)
		{
			if((*lista)->info == (*lista)->next->info)
			{
				ListaDiElementi t=(*lista)->next;

				(*lista)->next=t->next;

				free(t);

				cancellaDuplicates(&(*lista));
			}
			else
			{
				cancellaDuplicates(&((*lista)->next));
			}
		}
	}
}

int find(ListaDiElementi l, int n)
{
	if(l!=NULL)
	{
		if(l->info==n)
		{
			return 1;
		}
		else
		{
			find(l->next, n);
		}
	}
	else
	{
		return 0;
	}
}

void filterLists(ListaDiElementi *lista1, ListaDiElementi lista2)
{
	if(*lista1 != NULL)
	{
		if(find(lista2, (*lista1)->info))
		{
			ListaDiElementi t=(*lista1)->next;

			free(*lista1);

			*lista1=t;

			filterLists(&(*lista1), lista2);
		}
		else
		{
			filterLists(&((*lista1)->next), lista2);
		}
	}
}

//Function to print all the elements of the list:
void printList(ListaDiElementi list) {
	printf("(");
	while (list != NULL) {
		printf("%d ", list->info);
		list = list->next;
	}
	printf(")\n");
}

int main() {

	ListaDiElementi first_list = NULL, second_list=NULL;

	//Read and  print the first list:
    	readList(&first_list); // add call to procedure/function readList()
	printf("Prima lista\n");
	printList(first_list);

    	//Eliminates  Duplicates from the first list:
    	cancellaDuplicates(&first_list);
    	printf("Prima lista senza duplicati\n");
    	printList(first_list);

    	//Read and  print the second list:
     	readList(&second_list);
    	printf("Seconda lista\n");
    	printList(second_list);

    	//Eliminates  Duplicates from the second list:
    	cancellaDuplicates(&second_list);
    	printf("Seconda lista senza duplicati\n");
    	printList(second_list);

    	//Filter the first list using the elements of the second list:
    	filterLists(&first_list, second_list);

    	//Print the filtered list:
    	printf("Lista filtrata\n");
    	printList(first_list);

    	return 0;
}
