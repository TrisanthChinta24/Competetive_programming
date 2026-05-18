import java.io.*;
import java.util.*;

//keep track of parent element useful for path finding in the backtracking
public class MessageRoute {

    static ArrayList<Integer>[] graph;
    static boolean[] visited;
    static int[] parent;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] first = br.readLine().split(" ");

        int n = Integer.parseInt(first[0]);
        int m = Integer.parseInt(first[1]);

        graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {

            String[] edge = br.readLine().split(" ");

            int a = Integer.parseInt(edge[0]);
            int b = Integer.parseInt(edge[1]);

            graph[a].add(b);
            graph[b].add(a);
        }

        visited = new boolean[n + 1];
        parent = new int[n + 1];

        bfs(1);

        // No path exists
        if (!visited[n]) {
            System.out.println("IMPOSSIBLE");
            return;
        }

        // Reconstruct path
        ArrayList<Integer> path = new ArrayList<>();

        int curr = n;

        while (curr != 0) {
            path.add(curr);
            curr = parent[curr];
        }

        Collections.reverse(path);

        System.out.println(path.size());

        for (int node : path) {
            System.out.print(node + " ");
        }
    }

    static void bfs(int start) {

        Queue<Integer> q = new LinkedList<>();

        q.offer(start);
        visited[start] = true;

        while (!q.isEmpty()) {

            int node = q.poll();

            for (int next : graph[node]) {

                if (!visited[next]) {

                    visited[next] = true;

                    parent[next] = node;

                    q.offer(next);
                }
            }
        }
    }
}