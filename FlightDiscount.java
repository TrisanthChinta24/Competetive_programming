import java.io.*;
import java.util.*;

public class FlightDiscount {

    static class Edge {

        int to;
        long wt;

        Edge(int to, long wt) {
            this.to = to;
            this.wt = wt;
        }
    }

    static class State implements Comparable<State> {

        int node;
        int used;
        long cost;

        State(int node, int used, long cost) {
            this.node = node;
            this.used = used;
            this.cost = cost;
        }

        public int compareTo(State other) {
            return Long.compare(this.cost, other.cost);
        }
    }

    static final long INF = (long) 4e18;

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

        long[][] dist = new long[n + 1][2];

        for (int i = 1; i <= n; i++) {
            Arrays.fill(dist[i], INF);
        }

        PriorityQueue<State> pq = new PriorityQueue<>();

        dist[1][0] = 0;

        pq.offer(new State(1, 0, 0));

        while (!pq.isEmpty()) {

            State cur = pq.poll();

            int u = cur.node;
            int used = cur.used;
            long d = cur.cost;

            if (d > dist[u][used]) continue;

            for (Edge e : graph[u]) {

                int v = e.to;
                long w = e.wt;

                // move normally
                if (d + w < dist[v][used]) {

                    dist[v][used] = d + w;

                    pq.offer(new State(v, used, dist[v][used]));
                }

                // use coupon
                if (used == 0) {

                    long nd = d + (w / 2);

                    if (nd < dist[v][1]) {

                        dist[v][1] = nd;

                        pq.offer(new State(v, 1, nd));
                    }
                }
            }
        }

        System.out.println(Math.min(dist[n][0], dist[n][1]));
    }
}
