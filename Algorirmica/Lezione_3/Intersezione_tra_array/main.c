#include <stdlib.h>
#include <stdio.h>

int intersezione(int *a1, int l1, int *a2, int l2);

int main(int argc, char **argv)
{
    int l1, l2;

    scanf("%d", &l1);
    int *a1 = (int *) malloc(sizeof(int)*l1);

    for(int i=0;i<l1;i++)
    {
        scanf("%d", a1+i);
    }

    scanf("%d", &l2);
    int *a2 = (int*) malloc(sizeof(int)*l2);

    for(int i=0;i<l2;i++)
    {
        scanf("%d", a2+i);
    }

    printf("%d", intersezione(a1, l1, a2, l2));

    free(a1);
    free(a2);

    return EXIT_SUCCESS;
}

int intersezione(int *a1, int l1, int *a2, int l2)
{
    int i, c, r = 0;

    for (i = 0; i < l1; i++) {
        for (c = 0; c < l2; c++) {
            if (a1[i] == a2[c]) {
                r++;
            }
        }
    }

    return r;
}