import java.util.*;

public class TestRunner {
    
    public static void main(String[] args) {
        GoTournamentFinal solution = new GoTournamentFinal();
        
        System.out.println("=== 围棋比赛安排算法测试 ===\n");
        
        // 测试用例1：题目示例1
        testCase(solution, "示例1", 2, 2, new int[]{9, 1, 6, 4}, 1);
        
        // 测试用例2：题目示例2
        testCase(solution, "示例2", 2, 5, new int[]{9, 1, 6, 4}, 3);
        
        // 测试用例3：N=3的情况
        testCase(solution, "N=3测试", 3, 3, new int[]{10, 9, 8, 7, 6, 5, 4, 3}, 7);
        
        // 测试用例4：N=4的情况
        testCase(solution, "N=4测试", 4, 5, new int[]{20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5}, 15);
        
        // 测试用例5：边界情况
        testCase(solution, "边界测试1", 1, 1, new int[]{5, 3}, 1);
        
        // 测试用例6：K很大的情况
        testCase(solution, "大K测试", 2, 100, new int[]{9, 1, 6, 4}, 3);
        
        // 测试用例7：K很小的情况
        testCase(solution, "小K测试", 2, 1, new int[]{9, 1, 6, 4}, 0);
        
        System.out.println("\n=== 测试完成 ===");
    }
    
    private static void testCase(GoTournamentFinal solution, String name, int N, int K, int[] R, int expected) {
        System.out.println("测试: " + name);
        System.out.println("输入: N=" + N + ", K=" + K + ", R=" + Arrays.toString(R));
        System.out.println("期望输出: " + expected);
        
        long startTime = System.currentTimeMillis();
        int result = solution.maxCloseMatches(N, K, R);
        long endTime = System.currentTimeMillis();
        
        System.out.println("实际输出: " + result);
        System.out.println("执行时间: " + (endTime - startTime) + "ms");
        
        if (result == expected) {
            System.out.println("✓ 测试通过");
        } else {
            System.out.println("✗ 测试失败");
        }
        
        // 验证观赏性要求
        verifyTournamentStructure(N, R);
        
        System.out.println();
    }
    
    private static void verifyTournamentStructure(int N, int[] R) {
        System.out.println("  验证观赏性要求:");
        
        // 检查前2^i名选手是否在第i轮出现
        for (int i = 1; i <= N; i++) {
            int topCount = 1 << i;
            System.out.println("    前" + topCount + "名选手必须在第" + i + "轮出现");
        }
        
        // 验证评分排序（应该是降序，因为算法中按评分降序排序）
        boolean isSorted = true;
        for (int i = 1; i < R.length; i++) {
            if (R[i-1] < R[i]) {
                isSorted = false;
                break;
            }
        }
        System.out.println("    评分排序验证: " + (isSorted ? "✓ 正确（降序）" : "✗ 错误（应该是降序）"));
    }
}