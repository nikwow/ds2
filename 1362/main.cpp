#include <iostream>
#include <map>
#include <queue>
#include <vector>

constexpr int INF = 1e9;

struct edge {
    int to;
    int capacity;
    int flow;
};

std::map<std::string, int> idx;
int nodes;
int source, sink;
std::vector<std::vector<int>> adj;
std::vector<edge> edges;

void add_edge(int from, int to, int capacity) {
    adj[from].push_back(edges.size());
    edges.push_back({to, capacity, 0});
    adj[to].push_back(edges.size());
    edges.push_back({from, 0, 0});
}

int find_path(std::vector<int>& parent_edge, std::vector<int>& parent_node) {
    std::fill(parent_edge.begin(), parent_edge.end(), -1);
    std::queue<std::pair<int, int>> q;
    q.emplace(source, INF);
    parent_node[source] = source;

    while (!q.empty()) {
        int u = q.front().first;
        int flow_value = q.front().second;
        q.pop();

        for (int index : adj[u]) {
            int v = edges[index].to;
            int residual = edges[index].capacity - edges[index].flow;
            if (parent_node[v] == -1 and residual > 0) {
                parent_node[v] = u;
                parent_edge[v] = index;
                int new_flow = std::min(flow_value, residual);
                if (v == sink) return new_flow;

                q.push({v, new_flow});
            }
        }
    }
    return 0;
}

int edmonds_karp() {
    int max_flow = 0;

    while (true) {
        std::vector<int> parent_edge(nodes, -1);
        std::vector<int> parent_node(nodes, -1);
        int pushed = find_path(parent_edge, parent_node);

        if (pushed == 0) break;

        max_flow += pushed;
        int current = sink;
        while (current != source) {
            int prev = parent_edge[current];
            edges[prev].flow += pushed;
            edges[prev ^ 1].flow -= pushed;
            current = parent_node[current];
        }
    }
    return max_flow;
}

int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);

    idx["XS"] = 0;
    idx["S"] = 1;
    idx["M"] = 2;
    idx["L"] = 3;
    idx["XL"] = 4;
    idx["XXL"] = 5;

    int tshirts, volunteers;

    std::cout<<"enter the number of t-shirts sizes (always 6) and number of volunteers: "<<std::endl;
    while (std::cin>>tshirts>>volunteers and (tshirts != 0 or volunteers != 0)) {
        std::vector<int> count(6);
        std::cout<<"enter the quantity of each t-shirt size (XS, S, M, L, XL, XXL): "<<std::endl;
        for (int i = 0; i < 6; i++) std::cin>>count[i];

        source = 0;
        sink = 1 + volunteers + 6;
        nodes = sink + 1;
        adj.assign(nodes, std::vector<int>());
        edges.clear();

        for (int i = 1; i <= volunteers; i++) add_edge(source, i, 1);

        std::cout<<"enter the 2 preferred t-shirts sizes for each volunteer: "<<std::endl;
        for (int i = 1; i <= volunteers; i++) {
            std::string s1, s2;
            std::cout<<"volunteer "<<i<<": "<<std::endl;
            std::cin>>s1>>s2;
            int idx1 = 1 + volunteers + idx[s1];
            int idx2 = 1 + volunteers + idx[s2];

            add_edge(i, idx1, 1);
            add_edge(i, idx2, 1);
        }

        for (int i = 0; i < 6; i++) {
            int tshirt_node = 1 + volunteers + i;
            add_edge(tshirt_node, sink, count[i] );
        }

        if (edmonds_karp() == volunteers) {
            std::cout<<"YES"<<std::endl;
        } else {
            std::cout<<"NO"<<std::endl;
        }
        std::cout<<"\nenter the number of t-shirts sizes and volunteers for a new test (0 0 to exit): "<<std::endl;
    }
    return EXIT_SUCCESS;
}