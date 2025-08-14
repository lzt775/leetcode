import java.util.*;

class Solution {
    public int maxCloseMatches(int N, int K, int[] R) {
        int totalPlayers = 1 << N;
        
        // 按评分降序排序，获取选手索引
        List<Integer> players = new ArrayList<>();
        for (int i = 0; i < totalPlayers; i++) {
            players.add(i);
        }
        players.sort((a, b) -> Integer.compare(R[b], R[a]));
        
        // 构建满足观赏性要求的比赛安排
        int[][] tournament = new int[N][totalPlayers];
        
        // 第一轮：按评分排序安排选手
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
            
            // 填充剩余位置（只填充未设置的位置）
            for (int i = topPlayers; i < totalPlayers; i++) {
                int targetPos = i;
                tournament[round][targetPos] = tournament[round - 1][i];
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
public class GoTournamentFinal {
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // 测试示例1
        System.out.println("示例1: " + solution.maxCloseMatches(2, 2, new int[]{9, 1, 6, 4})); // 期望: 1
        
        // 测试示例2
        System.out.println("示例2: " + solution.maxCloseMatches(2, 5, new int[]{9, 1, 6, 4})); // 期望: 3
        
        // 分析比赛安排
        System.out.println("\n分析比赛安排:");
        analyzeTournament(2, new int[]{9, 1, 6, 4});
    }
    
    private static void analyzeTournament(int N, int[] R) {
        int totalPlayers = 1 << N;
        
        // 按评分降序排序
        List<Integer> players = new ArrayList<>();
        for (int i = 0; i < totalPlayers; i++) {
            players.add(i);
        }
        players.sort((a, b) -> Integer.compare(R[b], R[a]));
        
        System.out.println("排序后选手索引: " + players);
        System.out.println("对应评分: " + Arrays.toString(R));
        
        // 构建比赛安排
        int[][] tournament = new int[N][totalPlayers];
        
        // 第一轮
        for (int i = 0; i < totalPlayers; i++) {
            tournament[0][i] = players.get(i);
        }
        
        // 后续轮次
        for (int round = 1; round < N; round++) {
            int topPlayers = 1 << round;
            
            System.out.println("第" + (round + 1) + "轮: topPlayers=" + topPlayers);
            
            for (int i = 0; i < topPlayers; i += 2) {
                int player1 = tournament[round - 1][i];
                int player2 = tournament[round - 1][i + 1];
                
                int matchIndex = i / 2;
                int halfSize = totalPlayers >> round;
                
                tournament[round][matchIndex] = player1;
                tournament[round][matchIndex + halfSize] = player2;
                
                System.out.println("  设置: tournament[" + round + "][" + matchIndex + "]=" + player1);
                System.out.println("  设置: tournament[" + round + "][" + (matchIndex + halfSize) + "]=" + player2);
            }
            
            // 填充剩余位置（只填充未设置的位置）
            for (int i = topPlayers; i < totalPlayers; i++) {
                int targetPos = i;
                tournament[round][targetPos] = tournament[round - 1][i];
                System.out.println("  填充: tournament[" + round + "][" + targetPos + "]=" + tournament[round - 1][i]);
            }
        }
        
        // 打印比赛安排
        for (int round = 0; round < N; round++) {
            System.out.println("第" + (round + 1) + "轮: " + Arrays.toString(tournament[round]));
        }
    }
}