import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


//we need to find the cycle in the graph, where nodes are cities and start and end cities are same.
//i am using dfs traversal with the parent tracking for the reconstruction of the route.
//also included the raw bytes taking as input using the defined fastscanner class.
public class RoundTrip {
        private static List<Integer>[] adj;
        private static boolean[] visited;
        private static int[] parent;

        static int start = -1;
        static int end = -1;
        public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner(System.in);
        int n = fs.nextInt();
        int m = fs.nextInt();

        adj = new ArrayList[n+1];
        visited = new boolean[n+1];
        parent = new int[n+1];

        for(int i=1; i<=n; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < m; i++) {

            int a = fs.nextInt();
            int b = fs.nextInt();

            adj[a].add(b);
            adj[b].add(a);
        }

        for (int i = 1; i <= n; i++) {

            if (!visited[i]) {

                if (dfs(i, -1)) {
                    break;
                }
            }
        }

        if (start == -1) {
            System.out.println("IMPOSSIBLE");
            return;
        }

        List<Integer> cycle = new ArrayList<>();

        cycle.add(start);

        int node = end;

        while (node != start) {
            cycle.add(node);
            node = parent[node];
        }

        cycle.add(start);

        Collections.reverse(cycle);

        System.out.println(cycle.size());

        StringBuilder sb = new StringBuilder();

        for (int city : cycle) {
            sb.append(city).append(" ");
        }

        System.out.println(sb);
    }

    static boolean dfs(int node, int par) {

        visited[node] = true;

        for (int next : adj[node]) {

            if (next == par) {
                continue;
            }

            if (visited[next]) {

                start = next;
                end = node;

                return true;
            }

            parent[next] = node;

            if (dfs(next, node)) {
                return true;
            }
        }

        return false;
    }

    static class FastScanner{
        private final InputStream in;
        private final byte[] buffer = new byte[1<<16];
        private int ptr = 0;
        private int len = 0;

        public FastScanner(InputStream is){
            this.in = is;
        }

        public int read() throws IOException{
            if(ptr>=len){
                len = in.read(buffer);
                ptr = 0;
                if(len<=0) return -1;
            }
            return buffer[ptr++];
        }

        public int nextInt() throws IOException{
            int c;
            while((c = read()) <= ' ');
            int val = 0;
            while(c > ' '){
                val = val*10 + (c-'0');
                c = read();
            }
            return val;
        }

    }
}


