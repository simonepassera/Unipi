#include <stdlib.h>
#include <stdio.h>

int * Fusione(int *v1, int l1, int *v2, int l2);

int main(int argc, char **argv)
{
    int l1, l2;

    scanf("%d", &l1);
    int *a1=(int*) malloc(sizeof(int)*l1);

    for(int i=0;i<l1;i++)
    {
        scanf("%d", a1+i);
    }

    scanf("%d", &l2);
    int *a2=(int*) malloc(sizeof(int)*l1);

    for(int i=0;i<l2;i++)
    {
        scanf("%d", a2+i);
    }

    int *p=Fusione(a1, l1, a2, l2);

    for(int i=0; i<(l1+l2); i++)
    {
        printf("%d\n", *(p+i));
    }

    free(a1);
    free(a2);
    free(p);

    return EXIT_SUCCESS;
}

int * Fusione(int *v1, int l1, int *v2, int l2)
{
    int *p=(int *) malloc(sizeof(int)*(l1+l2)), i=0, j=0, k=0;

    while(i<l1 && j<l2)
    {
        if(v1[i]<v2[j])
        {
            p[k]=v1[i];
            i++;
        }
        else
        {
            p[k]=v2[j];
            j++;
        }

        k++;
    }

    while(i<l1)
    {
        p[k]=v1[i];
        i++;
        k++;
    }

    while(j<l2)
    {
        p[k]=v2[j];
        j++;
        k++;
    }

    return p;
}