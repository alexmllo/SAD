#include <stdio.h>
int main() {
	int i;
	while (scanf("&d", &i) != EOF) {
		fwrite(&i, sizeof(int), 1, stdout);
	}
} 
