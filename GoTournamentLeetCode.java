import java.util.*;

class Solution {
    public int maxCloseMatches(int N, int K, int[] R) {
        int totalPlayers = 1 << N; // 2^N
        
        // 按评分降序排序
        List<Integer> players = new ArrayList<>();
        for (int i = 0; i < totalPlayers; i++) {
            players.add(i);
        }
        players.sort((a, b) -> Integer.compare(R[b], R[a]));
        
        // 构建比赛安排
        int[][] tournament = new int[N][totalPlayers];
        
        // 第一轮：按评分排序安排
        for (int i = 0; i < totalPlayers; i++) {
            tournament[0][i] = players.get(i);
        }
        
        // 后续轮次：确保前2^i名选手在第i轮出现
        for (int round = 1; round < N; round++) {
            int topPlayers = 1 << round;
            
            // 前2^round名选手分成2^(round-1)对，分别放在不同半区
            for (int i = 0; i < topPlayers; i += 2) {
                int player1 = tournament[round - 1][i];
                int player2 = tournament[round - 1][i + 1];
                
                int matchIndex = i / 2;
                int halfSize = totalPlayers >> round;
                
                tournament[round][matchIndex] = player1;
                tournament[round][matchIndex + halfSize] = player2;
            }
            
            // 填充剩余位置
            for (int i = topPlayers; i < totalPlayers; i++) {
                tournament[round][i] = tournament[round - 1][i];
            }
        }
        
        // 统计势均力敌比赛
        int count = 0;
        for (int round = 0; round < N; round++) {
            int matches = totalPlayers >> (round + 1);
            if (N == 1) matches = 1;
            
            for (int match = 0; match < matches; match++) {
                int p1 = tournament[round][match * 2];
                int p2 = tournament[round][match * 2 + 1];
                
                if (Math.abs(R[p1] - R[p2]) <= K) {
                    count++;
                }
            }
        }
        
        return count;
    }
}

// 测试代码
public class GoTournamentLeetCode {
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // 测试示例1
        System.out.println(solution.maxCloseMatches(2, 2, new int[]{9, 1, 6, 4})); // 输出: 1
        
        // 测试示例2
        System.out.println(solution.maxCloseMatches(2, 5, new int[]{9, 1, 6, 4})); // 输出: 3
    }
}