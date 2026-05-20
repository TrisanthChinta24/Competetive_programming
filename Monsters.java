import java.io.*;
import java.util.*;


public class Monsters{

    static int n, m;
    static char[][] grid;

    static int[][] monsterDist;
    static boolean[][] visited;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    static char[] dir = {'U', 'D', 'L', 'R'};

    static class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new char[n][m];
        monsterDist = new int[n][m];

        for (int[] row : monsterDist)
            Arrays.fill(row, Integer.MAX_VALUE);

        Queue<Pair> monsterQueue = new LinkedList<>();

        int startX = 0, startY = 0;

        for (int i = 0; i < n; i++) {

            String s = br.readLine();

            for (int j = 0; j < m; j++) {

                grid[i][j] = s.charAt(j);

                if (grid[i][j] == 'M') {
                    monsterQueue.offer(new Pair(i, j));
                    monsterDist[i][j] = 0;
                }

                if (grid[i][j] == 'A') {
                    startX = i;
                    startY = j;
                }
            }
        }

        // BFS for monsters
        bfsMonsters(monsterQueue);

        // BFS for player
        bfsPlayer(startX, startY);
    }

    static void bfsMonsters(Queue<Pair> queue) {

        while (!queue.isEmpty()) {

            Pair cur = queue.poll();

            for (int k = 0; k < 4; k++) {

                int nx = cur.x + dx[k];
                int ny = cur.y + dy[k];

                if (isValid(nx, ny)
                        && grid[nx][ny] != '#'
                        && monsterDist[nx][ny] == Integer.MAX_VALUE) {

                    monsterDist[nx][ny] = monsterDist[cur.x][cur.y] + 1;

                    queue.offer(new Pair(nx, ny));
                }
            }
        }
    }

    static void bfsPlayer(int sx, int sy) {

        visited = new boolean[n][m];

        int[][] dist = new int[n][m];

        Pair[][] parent = new Pair[n][m];
        char[][] move = new char[n][m];

        Queue<Pair> queue = new LinkedList<>();

        queue.offer(new Pair(sx, sy));

        visited[sx][sy] = true;

        while (!queue.isEmpty()) {

            Pair cur = queue.poll();

            int x = cur.x;
            int y = cur.y;

            // boundary reached
            if (isBoundary(x, y)) {

                System.out.println("YES");

                StringBuilder path = new StringBuilder();

                while (!(x == sx && y == sy)) {

                    path.append(move[x][y]);

                    Pair p = parent[x][y];

                    x = p.x;
                    y = p.y;
                }

                path.reverse();

                System.out.println(path.length());
                System.out.println(path);

                return;
            }

            for (int k = 0; k < 4; k++) {

                int nx = x + dx[k];
                int ny = y + dy[k];

                if (isValid(nx, ny)
                        && !visited[nx][ny]
                        && grid[nx][ny] != '#') {

                    int nextDist = dist[x][y] + 1;

                    // safe before monster arrives
                    if (nextDist < monsterDist[nx][ny]) {

                        visited[nx][ny] = true;

                        dist[nx][ny] = nextDist;

                        parent[nx][ny] = cur;

                        move[nx][ny] = dir[k];

                        queue.offer(new Pair(nx, ny));
                    }
                }
            }
        }

        System.out.println("NO");
    }

    static boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < n && y < m;
    }

    static boolean isBoundary(int x, int y) {
        return x == 0 || y == 0 || x == n - 1 || y == m - 1;
    }
}


//Description
// You and some monsters are in a labyrinth. When taking a step to some direction in the labyrinth, each monster may simultaneously take one as well. Your goal is to reach one of the boundary squares without ever sharing a square with a monster.
// Your task is to find out if your goal is possible, and if it is, print a path that you can follow. Your plan has to work in any situation; even if the monsters know your path beforehand.
// Input
// The first input line has two integers n and m: the height and width of the map.
// After this there are n lines of m characters describing the map. Each character is . (floor), # (wall), A (start), or M (monster). There is exactly one A in the input.
// Output
// First print "YES" if your goal is possible, and "NO" otherwise.
// If your goal is possible, also print an example of a valid path (the length of the path and its description using characters D, U, L, and R). You can print any path, as long as its length is at most n \cdot m steps.
// Constraints

// 1 \le n,m \le 1000

// Example
// Input:
// 5 8
// ########
// #M..A..#
// #.#.M#.#
// #M#..#..
// #.######

// Output:
// YES
// 5
// RRDDR

// Since monsters and the player move simultaneously, we must ensure that the player never enters a cell where a monster can already be present.
// So:
// First compute the minimum time required for any monster to reach every cell using multi-source BFS.
// All monster positions are inserted into the queue initially.
// This gives:
// monsterTime[i][j] = earliest time a monster can reach that cell.
// Then run BFS from the player A.
// We move only to cells where: playerTime<monsterTime
// Meaning the player reaches strictly earlier than monsters.
// If during BFS we reach any boundary cell safely, escape is possible.