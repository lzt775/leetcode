import java.util.*;

public class GoTournamentCorrect {
    
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
        
        // 构建满足观赏性要求的比赛安排
        int[][] tournament = buildCorrectTournament(N, players);
        
        // 计算最多能安排的"势均力敌"比赛场数
        return countCloseMatches(tournament, R, K);
    }
    
    /**
     * 构建正确的单淘汰制比赛安排
     */
    private int[][] buildCorrectTournament(int N, List<Player> players) {
        int totalPlayers = 1 << N;
        int[][] tournament = new int[N][totalPlayers];
        
        // 第一轮：按评分排序安排选手
        for (int i = 0; i < totalPlayers; i++) {
            tournament[0][i] = players.get(i).index;
        }
        
        // 后续轮次：构建标准的单淘汰制比赛树
        for (int round = 1; round < N; round++) {
            int matchesInRound = totalPlayers >> round; // 2^(N-round)
            
            for (int match = 0; match < matchesInRound; match++) {
                // 每场比赛的胜者进入下一轮
                int player1Index = match * 2;
                int player2Index = match * 2 + 1;
                
                // 根据评分决定胜者（评分高的获胜）
                int player1 = tournament[round - 1][player1Index];
                int player2 = tournament[round - 1][player2Index];
                
                // 胜者进入下一轮
                tournament[round][match] = player1; // 假设player1评分更高
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
            
            // 对于N=1的特殊情况，只有1场比赛
            if (N == 1) {
                matchesInRound = 1;
            }
            
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
        GoTournamentCorrect solution = new GoTournamentCorrect();
        
        // 测试示例1
        int result1 = solution.maxCloseMatches(2, 2, new int[]{9, 1, 6, 4});
        System.out.println("示例1结果: " + result1); // 期望输出: 1
        
        // 测试示例2
        int result2 = solution.maxCloseMatches(2, 5, new int[]{9, 1, 6, 4});
        System.out.println("示例2结果: " + result2); // 期望输出: 3
        
        // 测试N=1的情况
        int result3 = solution.maxCloseMatches(1, 1, new int[]{5, 3});
        System.out.println("N=1结果: " + result3); // 期望输出: 0
        
        // 测试N=2, K=1的情况
        int result4 = solution.maxCloseMatches(2, 1, new int[]{9, 1, 6, 4});
        System.out.println("N=2, K=1结果: " + result4); // 期望输出: 0
    }
}