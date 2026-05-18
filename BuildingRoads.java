import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
class BuildingRoads {

    static boolean[] visited;
    static List<Integer> repre;
    static ArrayList<Integer>[] graph;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] line = br.readLine().split(" ");

        int n = Integer.parseInt(line[0]);
        int m = Integer.parseInt(line[1]);

        graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        visited = new boolean[n + 1];
        repre = new ArrayList<>();

        for (int i = 0; i < m; i++) {

            String[] road = br.readLine().split(" ");

            int a = Integer.parseInt(road[0]);
            int b = Integer.parseInt(road[1]);

            graph[a].add(b);
            graph[b].add(a);
        }

        // Find connected components
        for (int i = 1; i <= n; i++) {

            if (!visited[i]) {
                repre.add(i);
                bfs(i);
            }
        }

        // Minimum roads needed
        System.out.println(repre.size() - 1);

        // Connect representatives
        for (int i = 1; i < repre.size(); i++) {
            System.out.println(repre.get(i - 1) + " " + repre.get(i));
        }
    }

    public static void bfs(int start) {

        Queue<Integer> q = new LinkedList<>();

        q.offer(start);
        visited[start] = true;

        while (!q.isEmpty()) {

            int node = q.poll();

            for (int next : graph[node]) {

                if (!visited[next]) {

                    visited[next] = true;
                    q.offer(next);
                }
            }
        }
    }
}