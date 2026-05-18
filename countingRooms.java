import java.io.*;
import java.util.*;

class countingRooms {
    private static char[][] grid;
    private static boolean[][] visited;
    private static int rows[];
    private static int cols[];

    //this problem will count the number of connected componenets. here dots are considered as rooms.
    public static void bfs(int i, int j){
        Deque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{i, j});
        while(!queue.isEmpty()){
            int[] cell = queue.poll();
            for(int k=0; k<4; k++){
                int newRow = cell[0] + rows[k];
                int newCol = cell[1] + cols[k];
                if(newRow>=0 && newRow<grid.length && 
                    newCol>=0 && newCol<grid[0].length && 
                    !visited[newRow][newCol] && 
                    grid[newRow][newCol]=='.'){
                queue.offer(new int[]{newRow, newCol});
                visited[newRow][newCol] = true;
                }
            }
        }
        return;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] first = br.readLine().split(" ");
        int n = Integer.parseInt(first[0]);
        int m = Integer.parseInt(first[1]);
        rows = new int[]{-1, 0, 1, 0};
        cols = new int[]{0, 1, 0, -1};
        grid = new char[n][m];

        for (int i = 0; i < n; i++) {
            grid[i] = br.readLine().toCharArray();
        }
        visited = new boolean[n][m];
        int rooms = 0;
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(!visited[i][j] && grid[i][j]=='.'){
                    visited[i][j] = true;
                    bfs(i, j);
                    rooms++;
                }
            }
        }

        System.out.println(rooms);
    }
}