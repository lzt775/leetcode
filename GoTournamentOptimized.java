import java.util.*;

public class GoTournamentOptimized {
    
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
        int[][] tournament = buildOptimalTournament(N, players);
        
        // 计算最多能安排的"势均力敌"比赛场数
        return countCloseMatches(tournament, R, K);
    }
    
    /**
     * 构建满足观赏性要求的比赛安排，尽可能多地安排"势均力敌"的比赛
     */
    private int[][] buildOptimalTournament(int N, List<Player> players) {
        int totalPlayers = 1 << N;
        int[][] tournament = new int[N][totalPlayers];
        
        // 第一轮：按评分排序安排选手
        for (int i = 0; i < totalPlayers; i++) {
            tournament[0][i] = players.get(i).index;
        }
        
        // 后续轮次：在满足观赏性要求的前提下，尽可能安排"势均力敌"的比赛
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
            
            // 对于剩余选手，尝试安排"势均力敌"的比赛
            List<Integer> remainingPlayers = new ArrayList<>();
            for (int i = topPlayers; i < totalPlayers; i++) {
                remainingPlayers.add(tournament[round - 1][i]);
            }
            
            // 按评分排序剩余选手（使用players列表中的评分）
            remainingPlayers.sort((a, b) -> {
                int ratingA = players.stream().filter(p -> p.index == a).findFirst().get().rating;
                int ratingB = players.stream().filter(p -> p.index == b).findFirst().get().rating;
                return Integer.compare(ratingB, ratingA);
            });
            
            // 填充剩余位置，尝试让评分相近的选手对战
            int remainingPositions = totalPlayers - topPlayers;
            for (int i = 0; i < remainingPlayers.size(); i++) {
                int targetPos = topPlayers + (i % remainingPositions);
                tournament[round][targetPos] = remainingPlayers.get(i);
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
        GoTournamentOptimized solution = new GoTournamentOptimized();
        
        // 测试示例1
        int result1 = solution.maxCloseMatches(2, 2, new int[]{9, 1, 6, 4});
        System.out.println("示例1结果: " + result1); // 期望输出: 1
        
        // 测试示例2
        int result2 = solution.maxCloseMatches(2, 5, new int[]{9, 1, 6, 4});
        System.out.println("示例2结果: " + result2); // 期望输出: 3
        
        // 额外测试用例
        int result3 = solution.maxCloseMatches(3, 3, new int[]{10, 9, 8, 7, 6, 5, 4, 3});
        System.out.println("额外测试结果: " + result3);
        
        // 测试更大的用例
        int result4 = solution.maxCloseMatches(4, 5, new int[]{20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5});
        System.out.println("更大测试结果: " + result4);
    }
}