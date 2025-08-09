public class BinaryTreeMaxPathSum {
    // 二叉树节点定义
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // 解决方案核心逻辑
    static class Solution {
        private int maxSum = Integer.MIN_VALUE; // 记录全局最大路径和

        public int maxPathSum(TreeNode root) {
            dfs(root);
            return maxSum;
        }

        // 递归计算「当前节点对父节点的最大贡献」，同时更新全局最大路径和
        private int dfs(TreeNode node) {
            if (node == null) return 0; // 空节点贡献为 0

            // 左、右子树的最大贡献（负数则舍弃，因为会拉低总和）
            int leftGain = Math.max(dfs(node.left), 0);
            int rightGain = Math.max(dfs(node.right), 0);

            // 当前节点作为「路径最高点」的总和（左+右+自身）
            int currentMax = node.val + leftGain + rightGain;
            maxSum = Math.max(maxSum, currentMax); // 更新全局最大值

            // 返回「当前节点对父节点的最大贡献」（只能选左或右中较大的，保证路径不分叉）
            return node.val + Math.max(leftGain, rightGain);
        }
    }

    // 测试用例（直接运行 main 方法即可）
    public static void main(String[] args) {
        // 示例 1：输入 [1,2,3] → 输出 6
        TreeNode root1 = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        Solution sol1 = new Solution();
        System.out.println("示例1: " + sol1.maxPathSum(root1)); // 预期 6

        // 示例 2：输入 [-10,9,20,null,null,15,7] → 输出 42（15+20+7）
        TreeNode root2 = new TreeNode(-10, 
            new TreeNode(9), 
            new TreeNode(20, new TreeNode(15), new TreeNode(7))
        );
        Solution sol2 = new Solution();
        System.out.println("示例2: " + sol2.maxPathSum(root2)); // 预期 42

        // 测试用例 3：单节点 [-3] → 输出 -3
        TreeNode root3 = new TreeNode(-3);
        Solution sol3 = new Solution();
        System.out.println("单节点: " + sol3.maxPathSum(root3)); // 预期 -3

        // 测试用例 4：左子树为负数 [2,-1] → 输出 2（路径只选根节点）
        TreeNode root4 = new TreeNode(2, new TreeNode(-1), null);
        Solution sol4 = new Solution();
        System.out.println("左子树负数: " + sol4.maxPathSum(root4)); // 预期 2

        // 测试用例 5：全负数 [-2,-1] → 输出 -1（选值较大的叶子节点）
        TreeNode root5 = new TreeNode(-2, new TreeNode(-1), null);
        Solution sol5 = new Solution();
        System.out.println("全负数: " + sol5.maxPathSum(root5)); // 预期 -1

        // 测试用例 6：混合正负 [1,-2,3] → 输出 4（3→1）
        TreeNode root6 = new TreeNode(1, new TreeNode(-2), new TreeNode(3));
        Solution sol6 = new Solution();
        System.out.println("混合正负: " + sol6.maxPathSum(root6)); // 预期 4
    }
}