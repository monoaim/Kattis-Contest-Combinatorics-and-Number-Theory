// Rhombinoes
// https://open.kattis.com/problems/rhombinoes


#include <iostream>
#include <ctime>
#include <fstream>
#include <cmath>
#include <cstring>
#include <cassert>
#include <cstdio>
#include <algorithm>
#include <vector>
#include <utility>

using namespace std;

#define MAXN 1005
#define MAXW 105
#define MAXH 105

typedef pair < int, int > pii;

#define FOR(i, n) for (int i = 0; i < (n); ++i)

    vector < int > E[MAXN];
int match[MAXN];
bool vis[MAXN];

bool dfs(int i) {
    if (vis[i]) return false;
    vis[i] = true;

    int numE = E[i].size();
    for (int x = 0; x < numE; ++x) {
        int j = E[i][x];
        if (match[j] < 0 || dfs(match[j]))
            return (match[i] = j, match[j] = i, true);
    }

    return false;
}

int bip_match(int N) {
    memset(match, -1, sizeof(match));
    int ans = 0;
    for (int i = 0; i < N; ++i)
        if (match[i] < 0) {
            memset(vis, 0, sizeof(vis));
            ans += dfs(i);
        }
    return ans;
}

void add_edge(int i, int j) {
    E[i].push_back(j);
    E[j].push_back(i);
}

bool is_dead[MAXW][MAXH];

// triangle (x,y) is "pointing up" iff (x+y) is even
#define parity(x, y)(((x) + (y)) % 2)
#define PARITY_POINTING_UP 0
#define PARITY_POINTING_DOWN 1

#define has_left_neighbour(x, y)((x) > 0)
#define has_right_neighbour(x, y)((x) < (W - 1))
#define has_up_neighbour(x, y)((parity(x, y) == PARITY_POINTING_DOWN) && ((y) < (H - 1)))
#define has_down_neighbour(x, y)((parity(x, y) == PARITY_POINTING_UP) && ((y) > 0))

// map all nodes (x,y) to an integer in [0..W*H)
#define make_node(x, y)((y) * W + (x))

int main() {
    int W, H, K, x, y;
    if (scanf("%d%d%d", & W, & H, & K) != 3) {
        fprintf(stderr, "Expected at least three numbers as input\n");
        exit(1);
    }

    assert(1 <= W && W <= 100);
    assert(1 <= H && H <= 100);
    assert(1 <= K && K <= (W * H) && (W * H) <= 1000);

    memset(is_dead, false, sizeof(is_dead));

    // input (the dead triangles)
    FOR(q, K) {
        if (scanf("%d%d", & x, & y) != 2) {
            fprintf(stderr, "Expected at least two numbers as input\n");
            exit(1);
        }
        assert(0 <= x && x < W);
        assert(0 <= y && y < H);
        assert(!is_dead[x][y]);
        is_dead[x][y] = true;
    }

    // construct the (implicitly bipartite) graph
    int x2, y2;
    FOR(x, W) FOR(y, H) {
        if (is_dead[x][y]) continue;

        // check neighbours
        // only do up and right; down and left is implied (add_edge is symmetric)
        vector < pii > neighbours;
        if (has_up_neighbour(x, y)) {
            x2 = x;
            y2 = y + 1;
            if (!is_dead[x2][y2]) add_edge(make_node(x, y), make_node(x2, y2));
        }

        if (has_right_neighbour(x, y)) {
            x2 = x + 1;
            y2 = y;
            if (!is_dead[x2][y2]) add_edge(make_node(x, y), make_node(x2, y2));
        }
    }

    int ans = bip_match(W * H);
    printf("%d\n", ans);
}