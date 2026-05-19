import java.io.*;
import java.util.*;

public class BuildingTeams {
    private static int[] visited;
    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner(System.in);

        int n = fs.nextInt();
        int m = fs.nextInt();

        List<Integer>[] list = new ArrayList[n + 1];
        visited = new int[n+1];
        for (int i = 1; i <= n; i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {

            int a = fs.nextInt();
            int b = fs.nextInt();

            list[a].add(b);
            list[b].add(a);
        }
        for(int i=1; i<=n; i++){
            if(visited[i]==0){
                if(!bfs(i, list)){
                    System.out.println("IMPOSSIBLE");
                    return;
                }
            }
        }
        for(int i=1; i<=n; i++){
            System.out.print(visited[i] + " ");
        }
    }

    public static boolean bfs(int i, List<Integer>[] adj){
        Queue<Integer> q = new ArrayDeque<>();
        visited[i]=1;
        q.add(i);
        while(!q.isEmpty()){
            int curr = q.poll();
            int group = (visited[curr]==1) ? 2 : 1;
            for(int frnd : adj[curr]){
                if(visited[frnd]==0){
                    visited[frnd] = group;
                    q.add(frnd);
                }else if(visited[frnd]==visited[curr]) return false;
            }
        }
        return true;
    }

    static class FastScanner {

            private final InputStream in;
            private final byte[] buffer = new byte[1 << 16];

            private int ptr = 0;
            private int len = 0;

            FastScanner(InputStream is) {
                in = is;
            }

            private int read() throws IOException {

                if (ptr >= len) {

                    len = in.read(buffer);
                    ptr = 0;

                    if (len <= 0) {
                        return -1;
                    }
                }

                return buffer[ptr++];
            }

            int nextInt() throws IOException {

                int c;

                while ((c = read()) <= ' ') ;

                int val = 0;

                while (c > ' ') {
                    val = val * 10 + (c - '0');
                    c = read();
                }

                return val;
            }
        }
}