// Zapis
// https://open.kattis.com/problems/zapis


#include <iostream>
#include <string.h>
#include <fstream>

using namespace std;
#define INITIALIZE(a) memset(a, -1, sizeof(a))
#define MOD 100000
bool is_modulus_used;
long data[200 + 10][200 + 10];
string str;
long processInput(int left_index, int right_index) {
    int j, correct;
    if (left_index > right_index) return 1;
    if (data[left_index][right_index] != -1) return data[left_index][right_index];
    long temp_res = 0;
    for (j = left_index + 1; j <= right_index; j += 2) {
        if (str[left_index] == '(' && str[j] == ')') correct = 1;
        else if (str[left_index] == '{' && str[j] == '}') correct = 1;
        else if (str[left_index] == '[' && str[j] == ']') correct = 1;
        else if (str[left_index] == '?' && str[j] == ')') correct = 1;
        else if (str[left_index] == '?' && str[j] == '}') correct = 1;
        else if (str[left_index] == '?' && str[j] == ']') correct = 1;
        else if (str[left_index] == '(' && str[j] == '?') correct = 1;
        else if (str[left_index] == '{' && str[j] == '?') correct = 1;
        else if (str[left_index] == '[' && str[j] == '?') correct = 1;
        else if (str[left_index] == '?' && str[j] == '?') correct = 3;
        else correct = 0;
        temp_res += correct * processInput(left_index + 1, j - 1) * processInput(j + 1, right_index);
        if (temp_res > MOD) {
            is_modulus_used = true;
            temp_res %= MOD;
        }
    }
    return data[left_index][right_index] = temp_res;
}

int main() {
    long res, len;
    cout << "";
    while (cin >> len >> str) {
        INITIALIZE(data);
        res = processInput(0, len - 1);
        if (!is_modulus_used)
            cout << res << endl;
        else
            printf("%05lld\n", res);
        break;
    }
    return 0;
}