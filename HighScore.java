import java.io.*;
import java.util.*;


//in this problem I need to find the highscore that I can obtain on the way from 1 to n.
//bellman ford is used to find the min distance from source to other nodes while detecting the negative cycles
//I am using this algorithm in the sense of negating the scores to obtain the min dist and then revesign the sign which results in max score obtained.
//if there is a -ve cycle (involving the end) which means scores keep on increasing infinitely then output is -1.
public class HighScore {

    static class Edge {

        int u, v;
        long w;

        Edge(int u, int v, long w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    static final long INF = (long) 4e18;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        ArrayList<Edge> edges = new ArrayList<>();

        ArrayList<Integer>[] graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {

            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long x = Long.parseLong(st.nextToken());

            edges.add(new Edge(a, b, -x));

            graph[a].add(b);
        }

        long[] dist = new long[n + 1];

        Arrays.fill(dist, INF);

        dist[1] = 0;

        // Bellman Ford
        for (int i = 1; i <= n - 1; i++) {

            for (Edge e : edges) {

                if (dist[e.u] == INF) continue;

                if (dist[e.u] + e.w < dist[e.v]) {

                    dist[e.v] = dist[e.u] + e.w;
                }
            }
        }

        // Detect nodes affected by negative cycle
        boolean[] affected = new boolean[n + 1];

        for (Edge e : edges) {

            if (dist[e.u] == INF) continue;

            if (dist[e.u] + e.w < dist[e.v]) {

                affected[e.v] = true;
            }
        }

        // BFS from affected nodes
        Queue<Integer> q = new LinkedList<>();

        boolean[] vis = new boolean[n + 1];

        for (int i = 1; i <= n; i++) {

            if (affected[i]) {

                q.offer(i);

                vis[i] = true;
            }
        }

        while (!q.isEmpty()) {

            int u = q.poll();

            for (int v : graph[u]) {

                if (!vis[v]) {

                    vis[v] = true;

                    q.offer(v);
                }
            }
        }

        // if node n reachable from negative cycle
        if (vis[n]) {

            System.out.println(-1);
        }
        else {

            System.out.println(-dist[n]);
        }
    }
}