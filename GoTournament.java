import java.util.*;

public class GoTournament {
    
    /**
     * 计算最多能安排的"势均力敌"比赛场数
     * @param N 比赛轮数
     * @param K "势均力敌"的评分差阈值
     * @param R 选手评分数组
     * @return 最多能安排的"势均力敌"比赛场数
     */
    public int maxCloseMatches(int N, int K, int[] R) {
        int totalPlayers = 1 << N; // 2^N
        
        // 创建选手列表，包含评分和索引
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < totalPlayers; i++) {
            players.add(new Player(R[i], i));
        }
        
        // 按评分降序排序
        Collections.sort(players, (a, b) -> Integer.compare(b.rating, a.rating));
        
        // 构建比赛树，满足观赏性要求
        int[][] tournament = buildTournament(N, players);
        
        // 计算最多能安排的"势均力敌"比赛场数
        return countCloseMatches(tournament, R, K);
    }
    
    /**
     * 构建满足观赏性要求的比赛树
     */
    private int[][] buildTournament(int N, List<Player> players) {
        int totalPlayers = 1 << N;
        int[][] tournament = new int[N][totalPlayers];
        
        // 第一轮：按评分排序安排选手
        for (int i = 0; i < totalPlayers; i++) {
            tournament[0][i] = players.get(i).index;
        }
        
        // 后续轮次：确保前2^i名选手在第i轮出现
        for (int round = 1; round < N; round++) {
            int matchesInRound = totalPlayers >> round; // 2^(N-round)
            int topPlayers = 1 << round; // 2^round
            
            // 为前2^round名选手安排位置，确保他们在第round轮相遇
            for (int i = 0; i < topPlayers; i += 2) {
                int player1 = tournament[round - 1][i];
                int player2 = tournament[round - 1][i + 1];
                
                // 确保这两个选手在第round轮相遇
                int matchIndex = i / 2;
                tournament[round][matchIndex] = player1;
                tournament[round][matchIndex + matchesInRound] = player2;
            }
            
            // 填充剩余位置
            int remainingPositions = totalPlayers - topPlayers;
            int remainingPlayers = totalPlayers - topPlayers;
            
            for (int i = 0; i < remainingPlayers; i++) {
                int sourcePos = topPlayers + i;
                int targetPos = topPlayers + (i % remainingPositions);
                tournament[round][targetPos] = tournament[round - 1][sourcePos];
            }
        }
        
        return tournament;
    }
    
    /**
     * 计算最多能安排的"势均力敌"比赛场数
     */
    private int countCloseMatches(int[][] tournament, int[] ratings, int K) {
        int totalMatches = 0;
        int N = tournament.length;
        int totalPlayers = tournament[0].length;
        
        // 统计每轮比赛中的"势均力敌"场数
        for (int round = 0; round < N; round++) {
            int matchesInRound = totalPlayers >> (round + 1);
            
            for (int match = 0; match < matchesInRound; match++) {
                int player1Index = tournament[round][match * 2];
                int player2Index = tournament[round][match * 2 + 1];
                
                int rating1 = ratings[player1Index];
                int rating2 = ratings[player2Index];
                
                if (Math.abs(rating1 - rating2) <= K) {
                    totalMatches++;
                }
            }
        }
        
        return totalMatches;
    }
    
    /**
     * 选手类
     */
    private static class Player {
        int rating;
        int index;
        
        Player(int rating, int index) {
            this.rating = rating;
            this.index = index;
        }
    }
    
    // 测试方法
    public static void main(String[] args) {
        GoTournament solution = new GoTournament();
        
        // 测试示例1
        int result1 = solution.maxCloseMatches(2, 2, new int[]{9, 1, 6, 4});
        System.out.println("示例1结果: " + result1); // 期望输出: 1
        
        // 测试示例2
        int result2 = solution.maxCloseMatches(2, 5, new int[]{9, 1, 6, 4});
        System.out.println("示例2结果: " + result2); // 期望输出: 3
        
        // 额外测试用例
        int result3 = solution.maxCloseMatches(3, 3, new int[]{10, 9, 8, 7, 6, 5, 4, 3});
        System.out.println("额外测试结果: " + result3);
    }
}