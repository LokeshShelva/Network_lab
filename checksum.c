#include <stdio.h>
#include <string.h>

char a[20], b[20], sum[20], carry = '0', complement[20];
int n;

void add()
{
    int i;
    for (i = n - 1; i >= 0; i--)
    {
        if (a[i] == '0' && b[i] == '0' && carry == '0')
        {
            sum[i] = '0';
            carry = '0';
        }
        else if (a[i] == '0' && b[i] == '0' && carry == '1')
        {
            sum[i] = '1';
            carry = '0';
        }
        else if (a[i] == '0' && b[i] == '1' && carry == '0')
        {
            sum[i] = '1';
            carry = '0';
        }
        else if (a[i] == '0' && b[i] == '1' && carry == '1')
        {
            sum[i] = '0';
            carry = '1';
        }
        else if (a[i] == '1' && b[i] == '0' && carry == '0')
        {
            sum[i] = '1';
            carry = '0';
        }
        else if (a[i] == '1' && b[i] == '0' && carry == '1')
        {
            sum[i] = '0';
            carry = '1';
        }
        else if (a[i] == '1' && b[i] == '1' && carry == '0')
        {
            sum[i] = '0';
            carry = '1';
        }
        else if (a[i] == '1' && b[i] == '1' && carry == '1')
        {
            sum[i] = '1';
            carry = '1';
        }
    }
}

void main()
{
    int i, num, j, isAccepted = 1;

    printf("Enter number of frames: ");
    scanf("%d", &num);
    printf("Enter length of a frame: ");
    scanf("%d", &n);

    for (j = 0; j < n; j++)
    {
        a[j] = '0';
    }

    printf("\tSender\n");

    for (j = 0; j < num; j++)
    {
        printf("Enter the binary string: ");
        scanf("%s", &b);

        add();

        if (carry == '1')
        {
            for (i = 0; i < n; i++)
            {
                a[i] = sum[i];
                b[i] = '0';
            }
            b[n - 1] = '1';
            carry = '0';
            add();
            for (i = 0; i < n; i++)
            {
                a[i] = sum[i];
            }
        }
        else
        {
            for (i = 0; i < n; i++)
            {
                a[i] = sum[i];
            }
        }
        carry = '0';
    }

    for (i = 0; i < n; i++)
    {
        a[i] = '0';
        if (sum[i] == '0')
            complement[i] = '1';
        else
            complement[i] = '0';
    }

    printf("\nChecksum=%s\n", complement);

    printf("\t\nReciever\n");

    for (j = 0; j < num + 1; j++)
    {
        printf("Enter the binary string: ");
        scanf("%s", &b);

        add();

        if (carry == '1')
        {
            for (i = 0; i < n; i++)
            {
                a[i] = sum[i];
                b[i] = '0';
            }
            b[n - 1] = '1';
            carry = '0';
            add();
            for (i = 0; i < n; i++)
            {
                a[i] = sum[i];
            }
        }
        else
        {
            for (i = 0; i < n; i++)
            {
                a[i] = sum[i];
            }
        }
        carry = '0';
    }

    for (i = 0; i < n; i++)
    {
        if (sum[i] != '1')
            isAccepted = 0;
    }

    if (isAccepted)
        printf("\nThe message has no error.");
    else
        printf("The message has error.");
}