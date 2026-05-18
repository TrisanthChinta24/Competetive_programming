import java.io.*;
import java.util.*;

public class Labyrinth {
    static int n, m;
    static char[][] grid;
    static boolean[][] visited;

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static char[] moveChar = {'D', 'U', 'R', 'L'};

    //this is finding a path from A to B given safe grids and obstacles.
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new char[n][m];
        visited = new boolean[n][m];

        int startX = 0, startY = 0;

        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            for (int j = 0; j < m; j++) {
                grid[i][j] = line.charAt(j);
                if (grid[i][j] == 'A') {
                    startX = i;
                    startY = j;
                }
            }
        }

        // Parent tracking
        int[][] parentX = new int[n][m];
        int[][] parentY = new int[n][m];
        char[][] direction = new char[n][m];

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;

        boolean found = false;
        int endX = -1, endY = -1;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0], y = curr[1];

            if (grid[x][y] == 'B') {
                found = true;
                endX = x;
                endY = y;
                break;
            }

            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx >= 0 && ny >= 0 && nx < n && ny < m) {
                    if (!visited[nx][ny] && grid[nx][ny] != '#') {
                        visited[nx][ny] = true;
                        queue.add(new int[]{nx, ny});

                        parentX[nx][ny] = x;
                        parentY[nx][ny] = y;
                        direction[nx][ny] = moveChar[d];
                    }
                }
            }
        }

        if (!found) {
            System.out.println("NO");
            return;
        }

        // Reconstruct path
        StringBuilder path = new StringBuilder();
        int x = endX, y = endY;

        while (grid[x][y] != 'A') {
            path.append(direction[x][y]);
            int px = parentX[x][y];
            int py = parentY[x][y];
            x = px;
            y = py;
        }

        path.reverse();

        System.out.println("YES");
        System.out.println(path.length());
        System.out.println(path.toString());
    }
}