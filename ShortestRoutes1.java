import java.io.*;
import java.util.*;

//single source shortest path - dijkstra algo
public class ShortestRoutes1 {

    static class Edge {
        int to;
        long wt;

        Edge(int to, long wt) {
            this.to = to;
            this.wt = wt;
        }
    }

    static class Pair implements Comparable<Pair> {
        int node;
        long dist;

        Pair(int node, long dist) {
            this.node = node;
            this.dist = dist;
        }

        public int compareTo(Pair other) {
            return Long.compare(this.dist, other.dist);
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        ArrayList<Edge>[] graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {

            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());

            graph[a].add(new Edge(b, c));
        }

        long[] dist = new long[n + 1];

        Arrays.fill(dist, Long.MAX_VALUE);

        PriorityQueue<Pair> pq = new PriorityQueue<>();

        dist[1] = 0;

        pq.offer(new Pair(1, 0));

        while (!pq.isEmpty()) {

            Pair cur = pq.poll();

            int u = cur.node;
            long d = cur.dist;

            // outdated entry
            if (d > dist[u]) continue;

            for (Edge e : graph[u]) {

                int v = e.to;
                long wt = e.wt;

                if (dist[u] + wt < dist[v]) {

                    dist[v] = dist[u] + wt;

                    pq.offer(new Pair(v, dist[v]));
                }
            }
        }

        StringBuilder ans = new StringBuilder();

        for (int i = 1; i <= n; i++) {
            ans.append(dist[i]).append(" ");
        }

        System.out.println(ans);
    }
}