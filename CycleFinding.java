import java.io.*;
import java.util.*;

public class CycleFinding {

    static class Edge {

        int u, v;
        long w;

        Edge(int u, int v, long w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        ArrayList<Edge> edges = new ArrayList<>();

        for (int i = 0; i < m; i++) {

            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());

            edges.add(new Edge(a, b, c));
        }

        long[] dist = new long[n + 1];

        int[] parent = new int[n + 1];

        Arrays.fill(dist, 0);

        int x = -1;

        // Bellman Ford
        for (int i = 1; i <= n; i++) {

            x = -1;

            for (Edge e : edges) {

                if (dist[e.u] + e.w < dist[e.v]) {

                    dist[e.v] = dist[e.u] + e.w;

                    parent[e.v] = e.u;

                    x = e.v;
                }
            }
        }

        // no negative cycle
        if (x == -1) {

            System.out.println("NO");

            return;
        }

        // move inside cycle
        for (int i = 1; i <= n; i++) {
            x = parent[x];
        }

        ArrayList<Integer> cycle = new ArrayList<>();

        int cur = x;

        do {
            cycle.add(cur);
            cur = parent[cur];
        }
        while (cur != x);

        cycle.add(x);

        Collections.reverse(cycle);

        System.out.println("YES");

        for (int node : cycle) {
            System.out.print(node + " ");
        }
    }
}