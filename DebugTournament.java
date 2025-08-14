import java.util.*;

public class DebugTournament {
    
    public static void main(String[] args) {
        // 测试N=1的情况
        System.out.println("=== 调试 N=1 的情况 ===");
        debugTournament(1, 1, new int[]{5, 3});
        
        System.out.println("\n=== 调试 N=2 的情况 ===");
        debugTournament(2, 1, new int[]{9, 1, 6, 4});
    }
    
    public static void debugTournament(int N, int K, int[] R) {
        int totalPlayers = 1 << N;
        
        System.out.println("N=" + N + ", K=" + K + ", R=" + Arrays.toString(R));
        System.out.println("总选手数: " + totalPlayers);
        
        // 创建选手列表，包含评分和索引
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < totalPlayers; i++) {
            players.add(new Player(R[i], i));
        }
        
        // 按评分降序排序
        Collections.sort(players, (a, b) -> Integer.compare(b.rating, a.rating));
        System.out.println("排序后选手: " + players);
        
        // 构建比赛安排
        int[][] tournament = buildTournament(N, players);
        
        // 打印比赛安排
        printTournament(tournament, R);
        
        // 计算势均力敌比赛
        int closeMatches = countCloseMatches(tournament, R, K);
        System.out.println("势均力敌比赛数: " + closeMatches);
    }
    
    private static int[][] buildTournament(int N, List<Player> players) {
        int totalPlayers = 1 << N;
        int[][] tournament = new int[N][totalPlayers];
        
        System.out.println("\n构建比赛安排:");
        
        // 第一轮：按评分排序安排选手
        for (int i = 0; i < totalPlayers; i++) {
            tournament[0][i] = players.get(i).index;
        }
        System.out.println("第1轮: " + Arrays.toString(tournament[0]));
        
        // 后续轮次：确保前2^i名选手在第i轮出现
        for (int round = 1; round < N; round++) {
            int matchesInRound = totalPlayers >> round; // 2^(N-round)
            int topPlayers = 1 << round; // 2^round
            
            System.out.println("第" + (round + 1) + "轮: topPlayers=" + topPlayers + ", matchesInRound=" + matchesInRound);
            
            // 为前2^round名选手安排位置，确保他们在第round轮相遇
            for (int i = 0; i < topPlayers; i += 2) {
                int player1 = tournament[round - 1][i];
                int player2 = tournament[round - 1][i + 1];
                
                // 确保这两个选手在第round轮相遇
                int matchIndex = i / 2;
                tournament[round][matchIndex] = player1;
                tournament[round][matchIndex + matchesInRound] = player2;
                
                System.out.println("  安排: player" + player1 + " vs player" + player2 + " 在第" + (round + 1) + "轮");
            }
            
            // 填充剩余位置
            for (int i = topPlayers; i < totalPlayers; i++) {
                int targetPos = topPlayers + (i - topPlayers);
                tournament[round][targetPos] = tournament[round - 1][i];
            }
            
            System.out.println("第" + (round + 1) + "轮完整: " + Arrays.toString(tournament[round]));
        }
        
        return tournament;
    }
    
    private static int countCloseMatches(int[][] tournament, int[] ratings, int K) {
        int totalMatches = 0;
        int N = tournament.length;
        int totalPlayers = tournament[0].length;
        
        System.out.println("\n统计势均力敌比赛:");
        
        // 统计每轮比赛中的"势均力敌"场数
        for (int round = 0; round < N; round++) {
            int matchesInRound = totalPlayers >> (round + 1);
            
            // 对于N=1的特殊情况，只有1场比赛
            if (N == 1) {
                matchesInRound = 1;
            }
            
            System.out.println("第" + (round + 1) + "轮: " + matchesInRound + "场比赛");
            
            for (int match = 0; match < matchesInRound; match++) {
                int player1Index = tournament[round][match * 2];
                int player2Index = tournament[round][match * 2 + 1];
                
                int rating1 = ratings[player1Index];
                int rating2 = ratings[player2Index];
                
                int diff = Math.abs(rating1 - rating2);
                boolean isClose = diff <= K;
                
                System.out.println("  比赛" + (match + 1) + ": player" + player1Index + "(" + rating1 + ") vs player" + player2Index + "(" + rating2 + ") 评分差=" + diff + " " + (isClose ? "✓势均力敌" : "✗不势均力敌"));
                
                if (isClose) {
                    totalMatches++;
                }
            }
        }
        
        return totalMatches;
    }
    
    private static void printTournament(int[][] tournament, int[] ratings) {
        System.out.println("\n完整比赛安排:");
        for (int round = 0; round < tournament.length; round++) {
            System.out.print("第" + (round + 1) + "轮: ");
            for (int i = 0; i < tournament[round].length; i++) {
                int playerIndex = tournament[round][i];
                System.out.print("[" + playerIndex + ":" + ratings[playerIndex] + "] ");
            }
            System.out.println();
        }
    }
    
    private static class Player {
        int rating;
        int index;
        
        Player(int rating, int index) {
            this.rating = rating;
            this.index = index;
        }
        
        @Override
        public String toString() {
            return "[" + index + ":" + rating + "]";
        }
    }
}