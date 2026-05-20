import java.io.*;
import java.util.*;

public class ShortestRoutes2 {

    static final long INF = (long) 4e18;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        long[][] dist = new long[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {

            Arrays.fill(dist[i], INF);

            dist[i][i] = 0;
        }

        // input roads
        for (int i = 0; i < m; i++) {

            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());

            if (c < dist[a][b]) {

                dist[a][b] = c;
                dist[b][a] = c;
            }
        }

        // Floyd Warshall
        for (int k = 1; k <= n; k++) {

            long[] dk = dist[k];

            for (int i = 1; i <= n; i++) {

                long[] di = dist[i];

                long dik = di[k];

                if (dik == INF) continue;

                for (int j = 1; j <= n; j++) {

                    long nd = dik + dk[j];

                    if (nd < di[j]) {

                        di[j] = nd;
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        // queries
        while (q-- > 0) {

            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (dist[a][b] == INF)
                sb.append(-1).append('\n');
            else
                sb.append(dist[a][b]).append('\n');
        }

        System.out.print(sb);
    }
}