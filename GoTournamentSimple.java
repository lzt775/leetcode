import java.util.*;

class Solution {
    public int maxCloseMatches(int N, int K, int[] R) {
        int totalPlayers = 1 << N;
        
        // 直接计算所有可能的势均力敌比赛
        int totalCloseMatches = 0;
        
        // 遍历所有选手对
        for (int i = 0; i < totalPlayers; i++) {
            for (int j = i + 1; j < totalPlayers; j++) {
                if (Math.abs(R[i] - R[j]) <= K) {
                    totalCloseMatches++;
                }
            }
        }
        
        // 但是观赏性要求限制了某些比赛不能安排
        // 前2^i名选手必须在第i轮出现，这意味着他们不能过早相遇
        
        // 计算被约束限制的比赛数量
        int constrainedMatches = 0;
        
        // 对于每轮比赛，检查哪些势均力敌的比赛被约束阻止了
        for (int round = 1; round < N; round++) {
            int topPlayers = 1 << round;
            
            // 前topPlayers名选手中，评分差≤K的对数
            for (int i = 0; i < topPlayers; i++) {
                for (int j = i + 1; j < topPlayers; j++) {
                    if (Math.abs(R[i] - R[j]) <= K) {
                        // 这些选手必须在第round轮才能相遇，不能在第1轮相遇
                        // 如果他们在第1轮相遇，就违反了观赏性要求
                        constrainedMatches++;
                    }
                }
            }
        }
        
        // 实际可安排的势均力敌比赛 = 总的势均力敌比赛 - 被约束阻止的比赛
        return totalCloseMatches - constrainedMatches;
    }
}

// 测试代码
public class GoTournamentSimple {
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // 测试示例1
        System.out.println("示例1: " + solution.maxCloseMatches(2, 2, new int[]{9, 1, 6, 4})); // 期望: 1
        
        // 测试示例2
        System.out.println("示例2: " + solution.maxCloseMatches(2, 5, new int[]{9, 1, 6, 4})); // 期望: 3
        
        // 分析一下
        System.out.println("\n分析示例1:");
        int[] R1 = {9, 1, 6, 4};
        System.out.println("选手评分: " + Arrays.toString(R1));
        System.out.println("评分差≤2的对:");
        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                int diff = Math.abs(R1[i] - R1[j]);
                System.out.println("  [" + i + "," + j + "] 评分差=" + diff + (diff <= 2 ? " ✓" : " ✗"));
            }
        }
    }
}