#include <omp.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

int is_prime(int n);
int count_prime(int n);

int main(int argc, char* argv[]) {
    int prime_count;
    int threads_count = atoi(argv[2]) > 0 ? atoi(argv[2]) : 1;
    omp_set_dynamic(0);

    if (strcmp(argv[1], "1") == 0) {
#pragma omp parallel for reduction(+ \
                                   : prime_count) schedule(static) num_threads(threads_count)
        for (int i = 1; i <= 200000; i++) {
            if (is_prime(i))
                prime_count++;
        }
    } else if (strcmp(argv[1], "2") == 0) {
#pragma omp parallel for reduction(+ \
                                   : prime_count) schedule(dynamic, 4) num_threads(threads_count)
        for (int i = 1; i <= 200000; i++) {
            if (is_prime(i))
                prime_count++;
        }
    } else if (strcmp(argv[1], "3") == 0) {
#pragma omp parallel for reduction(+ \
                                   : prime_count) schedule(guided, 4) num_threads(threads_count)
        for (int i = 1; i <= 200000; i++) {
            if (is_prime(i))
                prime_count++;
        }
    } else {
        printf("Invalid arguments.\n");
        return -1;
    }

    //printf("Execution time: %f s\n", (double)(end - start) / CLOCKS_PER_SEC);
    printf("Prime number count: %d\n", prime_count);
    return 0;
}

int is_prime(int n) {
    if (n <= 1)
        return 0;
    else if (n == 2)
        return 1;
    else if ((n & 1) == 0) {
        return 0;
    }
    for (int i = 3; i < n; i += 2) {
        if (n % i == 0)
            return 0;
    }
    return 1;
}

int count_prime(int n) {
}
